package com.bradesco.pix.infrastructure.adapters.rest.controllers.requests;

import com.bradesco.pix.domain.entities.enums.PixKeyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatePixKeyRequest {
    @NotBlank(message = "Key cannot be blank")
    private String key;

    @NotNull(message = "Type cannot be null")
    private PixKeyType type;

    @NotBlank(message = "Account number cannot be blank")
    private String accountNumber;

    @NotBlank(message = "Agency cannot be blank")
    private String agency;

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public PixKeyType getType() { return type; }
    public void setType(PixKeyType type) { this.type = type; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getAgency() { return agency; }
    public void setAgency(String agency) { this.agency = agency; }
}
