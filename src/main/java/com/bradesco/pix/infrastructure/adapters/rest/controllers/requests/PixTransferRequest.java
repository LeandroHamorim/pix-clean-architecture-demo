package com.bradesco.pix.infrastructure.adapters.rest.controllers.requests;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class PixTransferRequest {
    @NotBlank(message = "Source account number cannot be blank")
    private String sourceAccountNumber;

    @NotBlank(message = "Source agency cannot be blank")
    private String sourceAgency;

    @NotBlank(message = "Destination PIX key cannot be blank")
    private String destinationPixKey;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0.01")
    @DecimalMax(value = "500000.00", message = "Amount cannot exceed 500,000.00")
    private BigDecimal amount;

    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;

    public String getSourceAccountNumber() { return sourceAccountNumber; }
    public void setSourceAccountNumber(String sourceAccountNumber) { this.sourceAccountNumber = sourceAccountNumber; }
    public String getSourceAgency() { return sourceAgency; }
    public void setSourceAgency(String sourceAgency) { this.sourceAgency = sourceAgency; }
    public String getDestinationPixKey() { return destinationPixKey; }
    public void setDestinationPixKey(String destinationPixKey) { this.destinationPixKey = destinationPixKey; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
