package com.bradesco.pix.domain.entities;

import com.bradesco.pix.domain.exceptions.InactiveAccountException;
import com.bradesco.pix.domain.exceptions.InsufficientBalanceException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Account {
    private final String accountNumber;
    private final String agency;
    private final String ownerDocument;
    private final String ownerName;
    private BigDecimal balance;
    private final LocalDateTime createdAt;
    private boolean active;

    public Account(String accountNumber, String agency, String ownerDocument,
                   String ownerName, BigDecimal initialBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        if (agency == null || agency.trim().isEmpty()) {
            throw new IllegalArgumentException("Agency cannot be null or empty");
        }
        if (ownerDocument == null || ownerDocument.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner document cannot be null or empty");
        }
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        this.accountNumber = accountNumber;
        this.agency = agency;
        this.ownerDocument = ownerDocument;
        this.ownerName = ownerName;
        this.balance = initialBalance;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public void debit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (!active) {
            throw new InactiveAccountException(accountNumber, agency);
        }
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException(balance, amount);
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (!active) {
            throw new InactiveAccountException(accountNumber, agency);
        }
        this.balance = this.balance.add(amount);
    }

    public String getAccountNumber() { return accountNumber; }
    public String getAgency() { return agency; }
    public String getOwnerDocument() { return ownerDocument; }
    public String getOwnerName() { return ownerName; }
    public BigDecimal getBalance() { return balance; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isActive() { return active; }
    public void deactivate() { this.active = false; }
    public void activate() { this.active = true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(agency, account.agency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, agency);
    }
}

