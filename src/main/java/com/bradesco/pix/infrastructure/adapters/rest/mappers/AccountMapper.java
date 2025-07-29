package com.bradesco.pix.infrastructure.adapters.rest.mappers;

import com.bradesco.pix.domain.entities.Account;
import com.bradesco.pix.infrastructure.persistence.entities.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toDomain(AccountEntity entity) {
        if (entity == null) {
            return null;
        }

        Account account = new Account(
                entity.getAccountNumber(),
                entity.getAgency(),
                entity.getOwnerDocument(),
                entity.getOwnerName(),
                entity.getBalance()
        );

        if (!entity.isActive()) {
            account.deactivate();
        }
        return account;
    }

    public AccountEntity toEntity(Account domain) {
        if (domain == null) {
            return null;
        }

        AccountEntity entity = new AccountEntity();
        entity.setAccountNumber(domain.getAccountNumber());
        entity.setAgency(domain.getAgency());
        entity.setOwnerDocument(domain.getOwnerDocument());
        entity.setOwnerName(domain.getOwnerName());
        entity.setBalance(domain.getBalance());
        entity.setActive(domain.isActive());
        return entity;
    }

    public void updateEntityFromDomain(Account domain, AccountEntity entity) {
        entity.setBalance(domain.getBalance());
        entity.setActive(domain.isActive());
    }
}