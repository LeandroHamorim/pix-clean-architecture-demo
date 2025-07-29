package com.bradesco.pix.domain.ports;

import com.bradesco.pix.domain.entities.Account;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findByAccountNumberAndAgency(String accountNumber, String agency);
    Account save(Account account);
    void update(Account account);
}
