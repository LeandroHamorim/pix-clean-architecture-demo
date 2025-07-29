package com.bradesco.pix.infrastructure.adapters.rest.mappers;

import com.bradesco.pix.domain.entities.PixKey;
import com.bradesco.pix.infrastructure.persistence.entities.PixKeyEntity;
import org.springframework.stereotype.Component;

@Component
public class PixKeyMapper {

    public PixKey toDomain(PixKeyEntity entity) {
        if (entity == null) {
            return null;
        }

        PixKey pixKey = new PixKey(
                entity.getKeyValue(),
                entity.getKeyType(),
                entity.getAccountNumber(),
                entity.getAgency(),
                entity.getOwnerName()
        );

        if (!entity.isActive()) {
            pixKey.deactivate();
        }
        return pixKey;
    }

    public PixKeyEntity toEntity(PixKey domain) {
        if (domain == null) {
            return null;
        }

        PixKeyEntity entity = new PixKeyEntity();
        entity.setKeyValue(domain.getKey());
        entity.setKeyType(domain.getType());
        entity.setAccountNumber(domain.getAccountNumber());
        entity.setAgency(domain.getAgency());
        entity.setOwnerName(domain.getOwnerName());
        entity.setActive(domain.isActive());

        return entity;
    }
}