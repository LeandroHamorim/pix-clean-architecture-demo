package com.bradesco.pix.infrastructure.persistence.entities;

import com.bradesco.pix.domain.entities.enums.TransactionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "source_account_number", nullable = false)
    private String sourceAccountNumber;

    @Column(name = "source_agency", nullable = false)
    private String sourceAgency;

    @Column(name = "destination_pix_key", nullable = false)
    private String destinationPixKey;

    @Column(name = "destination_account_number", nullable = false)
    private String destinationAccountNumber;

    @Column(name = "destination_agency", nullable = false)
    private String destinationAgency;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public TransactionEntity() {
        this.createdAt = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public String getSourceAgency() {
        return sourceAgency;
    }

    public void setSourceAgency(String sourceAgency) {
        this.sourceAgency = sourceAgency;
    }

    public String getDestinationPixKey() {
        return destinationPixKey;
    }

    public void setDestinationPixKey(String destinationPixKey) {
        this.destinationPixKey = destinationPixKey;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public String getDestinationAgency() {
        return destinationAgency;
    }

    public void setDestinationAgency(String destinationAgency) {
        this.destinationAgency = destinationAgency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
