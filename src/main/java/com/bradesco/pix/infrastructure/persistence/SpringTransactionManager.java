package com.bradesco.pix.infrastructure.persistence;

import com.bradesco.pix.domain.ports.TransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SpringTransactionManager implements TransactionManager {

    @Override
    @Transactional
    public <T> T executeInTransaction(TransactionCallback<T> callback) {
        try {
            return callback.doInTransaction();
        } catch (Exception e) {
            throw new RuntimeException("Transaction failed", e);
        }
    }
}
