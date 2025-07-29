package com.bradesco.pix.infrastructure.adapters.rest.controllers.requests;

import com.bradesco.pix.domain.entities.enums.PixKeyType;

public class CreatePixKeyRequest {
    private String key;
    private PixKeyType type;
    private String accountNumber;
    private String agency;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PixKeyType getType() {
        return type;
    }

    public void setType(PixKeyType type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }
}
