package com.bradesco.pix.domain.ports;

public interface TransactionManager {
    <T> T executeInTransaction(TransactionCallback<T> callback);

    @FunctionalInterface
    interface TransactionCallback<T> {
        T doInTransaction() throws Exception;
    }
}
