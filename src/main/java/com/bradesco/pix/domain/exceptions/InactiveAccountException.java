package com.bradesco.pix.domain.exceptions;

public class InactiveAccountException extends BusinessRuleException {
    private final String accountNumber;
    private final String agency;

    public InactiveAccountException(String accountNumber, String agency) {
        super(String.format("Account is inactive: %s-%s", agency, accountNumber));
        this.accountNumber = accountNumber;
        this.agency = agency;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getAgency() { return agency; }
}
