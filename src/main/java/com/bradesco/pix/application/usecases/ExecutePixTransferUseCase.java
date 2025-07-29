package com.bradesco.pix.application.usecases;

import com.bradesco.pix.domain.entities.Account;
import com.bradesco.pix.domain.entities.PixKey;
import com.bradesco.pix.domain.entities.PixTransaction;
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

            Account sourceAccount = accountRepository.findByAccountNumberAndAgency(
                            sourceAccountNumber, sourceAgency)
                    .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

            PixKey pixKey = pixKeyRepository.findByKey(destinationPixKey)
                    .orElseThrow(() -> new IllegalArgumentException("PIX key not found"));

            if (!pixKey.isActive()) {
                throw new IllegalArgumentException("PIX key is inactive");
            }

            Account destinationAccount = accountRepository.findByAccountNumberAndAgency(
                            pixKey.getAccountNumber(), pixKey.getAgency())
                    .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

            PixTransaction transaction = new PixTransaction(
                    sourceAccountNumber, sourceAgency,
                    destinationPixKey, pixKey.getAccountNumber(), pixKey.getAgency(),
                    amount, description
            );

            PixTransaction savedTransaction = transactionRepository.save(transaction);

            sourceAccount.debit(amount);
            destinationAccount.credit(amount);

            accountRepository.update(sourceAccount);
            accountRepository.update(destinationAccount);

            savedTransaction.markAsCompleted();
            transactionRepository.update(savedTransaction);

            bacenService.notifyTransaction(savedTransaction);

            return savedTransaction;
        });
    }
}