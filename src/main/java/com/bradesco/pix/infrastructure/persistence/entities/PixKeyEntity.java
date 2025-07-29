package com.bradesco.pix.infrastructure.persistence.entities;

import com.bradesco.pix.domain.entities.enums.PixKeyType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pix_keys")
public class PixKeyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_value", nullable = false, unique = true)
    private String keyValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "key_type", nullable = false)
    private PixKeyType keyType;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "agency", nullable = false)
    private String agency;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private boolean active;

    public PixKeyEntity() {
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public PixKeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(PixKeyType keyType) {
        this.keyType = keyType;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
