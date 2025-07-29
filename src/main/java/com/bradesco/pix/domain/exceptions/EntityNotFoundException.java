package com.bradesco.pix.domain.exceptions;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String entityType, String identifier) {
        super(String.format("%s not found with identifier: %s", entityType, identifier));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}

