package com.bradesco.pix.infrastructure.adapters.rest.mappers;

import com.bradesco.pix.domain.entities.PixTransaction;
import com.bradesco.pix.infrastructure.persistence.entities.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public PixTransaction toDomain(TransactionEntity entity) {
        if (entity == null) {
            return null;
        }

        return PixTransaction.rehydrate(
                entity.getTransactionId(),
                entity.getSourceAccountNumber(),
                entity.getSourceAgency(),
                entity.getDestinationPixKey(),
                entity.getDestinationAccountNumber(),
                entity.getDestinationAgency(),
                entity.getAmount(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getErrorMessage(),
                entity.getCreatedAt()
        );
    }

    public TransactionEntity toEntity(PixTransaction domain) {
        if (domain == null) {
            return null;
        }

        TransactionEntity entity = new TransactionEntity();
        entity.setTransactionId(domain.getTransactionId());
        entity.setSourceAccountNumber(domain.getSourceAccountNumber());
        entity.setSourceAgency(domain.getSourceAgency());
        entity.setDestinationPixKey(domain.getDestinationPixKey());
        entity.setDestinationAccountNumber(domain.getDestinationAccountNumber());
        entity.setDestinationAgency(domain.getDestinationAgency());
        entity.setAmount(domain.getAmount());
        entity.setDescription(domain.getDescription());
        entity.setStatus(domain.getStatus());
        entity.setErrorMessage(domain.getErrorMessage());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }

    public void updateEntityFromDomain(PixTransaction domain, TransactionEntity entity) {
        entity.setStatus(domain.getStatus());
        entity.setErrorMessage(domain.getErrorMessage());
    }
}