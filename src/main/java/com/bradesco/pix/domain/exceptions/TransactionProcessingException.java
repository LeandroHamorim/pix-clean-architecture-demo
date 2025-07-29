package com.bradesco.pix.domain.exceptions;

public class TransactionProcessingException extends RuntimeException {
    private final String transactionId;

    public TransactionProcessingException(String message, String transactionId) {
        super(message);
        this.transactionId = transactionId;
    }

    public TransactionProcessingException(String message, String transactionId, Throwable cause) {
        super(message, cause);
        this.transactionId = transactionId;
    }

    public String getTransactionId() { return transactionId; }
}
