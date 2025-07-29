package com.bradesco.pix.domain.entities;

import com.bradesco.pix.domain.entities.enums.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class PixTransaction {
    private final String transactionId;
    private final String sourceAccountNumber;
    private final String sourceAgency;
    private final String destinationPixKey;
    private final String destinationAccountNumber;
    private final String destinationAgency;
    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime createdAt;
    private TransactionStatus status;
    private String errorMessage;

    public PixTransaction(String sourceAccountNumber, String sourceAgency,
                          String destinationPixKey, String destinationAccountNumber,
                          String destinationAgency, BigDecimal amount, String description) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount.compareTo(new BigDecimal("500000")) > 0) {
            throw new IllegalArgumentException("Amount exceeds maximum limit");
        }

        this.transactionId = UUID.randomUUID().toString();
        this.sourceAccountNumber = sourceAccountNumber;
        this.sourceAgency = sourceAgency;
        this.destinationPixKey = destinationPixKey;
        this.destinationAccountNumber = destinationAccountNumber;
        this.destinationAgency = destinationAgency;
        this.amount = amount;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }

    private PixTransaction(String transactionId, String sourceAccountNumber, String sourceAgency,
                           String destinationPixKey, String destinationAccountNumber, String destinationAgency,
                           BigDecimal amount, String description, TransactionStatus status,
                           String errorMessage, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.sourceAccountNumber = sourceAccountNumber;
        this.sourceAgency = sourceAgency;
        this.destinationPixKey = destinationPixKey;
        this.destinationAccountNumber = destinationAccountNumber;
        this.destinationAgency = destinationAgency;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
    }

    public static PixTransaction rehydrate(
            String transactionId,
            String sourceAccountNumber,
            String sourceAgency,
            String destinationPixKey,
            String destinationAccountNumber,
            String destinationAgency,
            BigDecimal amount,
            String description,
            TransactionStatus status,
            String errorMessage,
            LocalDateTime createdAt)
    {
        return new PixTransaction(transactionId, sourceAccountNumber, sourceAgency, destinationPixKey,
                destinationAccountNumber, destinationAgency, amount, description, status, errorMessage, createdAt);
    }

    public void markAsCompleted() {
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException("Only pending transactions can be completed");
        }
        this.status = TransactionStatus.COMPLETED;
        this.errorMessage = null;
    }

    public void markAsFailed(String errorMessage) {
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException("Only pending transactions can be failed");
        }
        this.status = TransactionStatus.FAILED;
        this.errorMessage = errorMessage;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String getSourceAgency() {
        return sourceAgency;
    }

    public String getDestinationPixKey() {
        return destinationPixKey;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public String getDestinationAgency() {
        return destinationAgency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixTransaction that = (PixTransaction) o;
        return Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }
}