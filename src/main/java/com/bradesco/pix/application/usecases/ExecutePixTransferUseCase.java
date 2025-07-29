package com.bradesco.pix.application.usecases;

import com.bradesco.pix.domain.entities.Account;
import com.bradesco.pix.domain.entities.PixKey;
import com.bradesco.pix.domain.entities.PixTransaction;
import com.bradesco.pix.domain.exceptions.BusinessRuleException;
import com.bradesco.pix.domain.exceptions.EntityNotFoundException;
import com.bradesco.pix.domain.exceptions.TransactionProcessingException;
import com.bradesco.pix.domain.ports.*;
import java.math.BigDecimal;

public class ExecutePixTransferUseCase {
    private final AccountRepository accountRepository;
    private final PixKeyRepository pixKeyRepository;
    private final TransactionRepository transactionRepository;
    private final BacenService bacenService;
    private final TransactionManager transactionManager;

    public ExecutePixTransferUseCase(AccountRepository accountRepository,
                                     PixKeyRepository pixKeyRepository,
                                     TransactionRepository transactionRepository,
                                     BacenService bacenService,
                                     TransactionManager transactionManager) {
        this.accountRepository = accountRepository;
        this.pixKeyRepository = pixKeyRepository;
        this.transactionRepository = transactionRepository;
        this.bacenService = bacenService;
        this.transactionManager = transactionManager;
    }

    public PixTransaction execute(String sourceAccountNumber, String sourceAgency,
                                  String destinationPixKey, BigDecimal amount, String description) {

        return transactionManager.executeInTransaction(() -> {
            PixTransaction savedTransaction = null;

            try {
                Account sourceAccount = accountRepository.findByAccountNumberAndAgency(
                                sourceAccountNumber, sourceAgency)
                        .orElseThrow(() -> new EntityNotFoundException("Source Account",
                                sourceAccountNumber + "-" + sourceAgency));

                PixKey pixKey = pixKeyRepository.findByKey(destinationPixKey)
                        .orElseThrow(() -> new EntityNotFoundException("PIX Key", destinationPixKey));

                if (!pixKey.isActive()) {
                    throw new BusinessRuleException("PIX key is inactive: " + destinationPixKey);
                }

                Account destinationAccount = accountRepository.findByAccountNumberAndAgency(
                                pixKey.getAccountNumber(), pixKey.getAgency())
                        .orElseThrow(() -> new EntityNotFoundException("Destination Account",
                                pixKey.getAccountNumber() + "-" + pixKey.getAgency()));

                PixTransaction transaction = new PixTransaction(
                        sourceAccountNumber, sourceAgency,
                        destinationPixKey, pixKey.getAccountNumber(), pixKey.getAgency(),
                        amount, description
                );

                savedTransaction = transactionRepository.save(transaction);

                sourceAccount.debit(amount);
                destinationAccount.credit(amount);

                accountRepository.update(sourceAccount);
                accountRepository.update(destinationAccount);

                savedTransaction.markAsCompleted();
                transactionRepository.update(savedTransaction);

                bacenService.notifyTransaction(savedTransaction);

                return savedTransaction;

            } catch (Exception e) {
                if (savedTransaction != null) {
                    try {
                        savedTransaction.markAsFailed(e.getMessage());
                        transactionRepository.update(savedTransaction);
                    } catch (Exception updateException) {
                        // Log but don't throw to preserve original exception
                        System.err.println("Failed to mark transaction as failed: " + updateException.getMessage());
                    }
                }

                if (e instanceof TransactionProcessingException) {
                    throw e;
                } else {
                    throw new TransactionProcessingException(
                            "Failed to process PIX transfer: " + e.getMessage(),
                            savedTransaction != null ? savedTransaction.getTransactionId() : null,
                            e
                    );
                }
            }
        });
    }
}