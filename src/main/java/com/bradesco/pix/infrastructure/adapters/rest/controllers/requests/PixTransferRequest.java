package com.bradesco.pix.infrastructure.adapters.rest.controllers.requests;

import java.math.BigDecimal;

public class PixTransferRequest {
    private String sourceAccountNumber;
    private String sourceAgency;
    private String destinationPixKey;
    private BigDecimal amount;
    private String description;

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
}
