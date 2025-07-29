package com.bradesco.pix.domain.ports;

import com.bradesco.pix.domain.entities.PixTransaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    PixTransaction save(PixTransaction transaction);
    Optional<PixTransaction> findById(String transactionId);
    List<PixTransaction> findBySourceAccount(String accountNumber, String agency);
    void update(PixTransaction transaction);
}
