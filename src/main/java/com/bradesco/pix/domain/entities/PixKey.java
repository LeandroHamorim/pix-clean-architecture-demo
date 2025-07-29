package com.bradesco.pix.domain.entities;

import com.bradesco.pix.domain.entities.enums.PixKeyType;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Pattern;

public class PixKey {
    private final String key;
    private final PixKeyType type;
    private final String accountNumber;
    private final String agency;
    private final String ownerName;
    private final LocalDateTime createdAt;
    private boolean active;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+55\\d{10,11}$");
    private static final Pattern CPF_PATTERN =
            Pattern.compile("^\\d{11}$");

    public PixKey(String key, PixKeyType type, String accountNumber,
                  String agency, String ownerName) {
        if (!isValidKey(key, type)) {
            throw new IllegalArgumentException("Invalid PIX key format for type: " + type);
        }

        this.key = key;
        this.type = type;
        this.accountNumber = accountNumber;
        this.agency = agency;
        this.ownerName = ownerName;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    private boolean isValidKey(String key, PixKeyType type) {
        if (key == null || key.trim().isEmpty()) return false;

        return switch (type) {
            case EMAIL -> EMAIL_PATTERN.matcher(key).matches();
            case PHONE -> PHONE_PATTERN.matcher(key).matches();
            case CPF -> CPF_PATTERN.matcher(key).matches();
            case RANDOM -> key.length() == 32; // UUID sem hífens
        };
    }

    public String getKey() {
        return key;
    }

    public PixKeyType getType() {
        return type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAgency() {
        return agency;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixKey pixKey = (PixKey) o;
        return Objects.equals(key, pixKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
