package com.bradesco.pix.application.usecases;

import com.bradesco.pix.domain.entities.Account;
import com.bradesco.pix.domain.entities.PixKey;
import com.bradesco.pix.domain.entities.enums.PixKeyType;
import com.bradesco.pix.domain.ports.AccountRepository;
import com.bradesco.pix.domain.ports.PixKeyRepository;

public class CreatePixKeyUseCase {
    private final PixKeyRepository pixKeyRepository;
    private final AccountRepository accountRepository;

    public CreatePixKeyUseCase(PixKeyRepository pixKeyRepository,
                               AccountRepository accountRepository) {
        this.pixKeyRepository = pixKeyRepository;
        this.accountRepository = accountRepository;
    }

    public PixKey execute(String key, PixKeyType type, String accountNumber, String agency) {

        Account account = accountRepository.findByAccountNumberAndAgency(accountNumber, agency)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        if (pixKeyRepository.findByKey(key).isPresent()) {
            throw new IllegalArgumentException("PIX key already exists");
        }

        var existingKeys = pixKeyRepository.findByAccountNumberAndAgency(accountNumber, agency);

        if (existingKeys.size() >= 5) {
            throw new IllegalArgumentException("Maximum number of PIX keys reached for this account");
        }

        PixKey pixKey = new PixKey(key, type, accountNumber, agency, account.getOwnerName());
        return pixKeyRepository.save(pixKey);
    }
}
