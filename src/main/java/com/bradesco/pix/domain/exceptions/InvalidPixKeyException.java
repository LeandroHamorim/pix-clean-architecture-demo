package com.bradesco.pix.domain.exceptions;

import com.bradesco.pix.domain.entities.enums.PixKeyType;

public class InvalidPixKeyException extends BusinessRuleException {
    private final PixKeyType type;
    private final String key;

    public InvalidPixKeyException(PixKeyType type, String value) {
        super(String.format("Invalid Pix key. Type: %s, Value: %s", type, value));
        this.type = type;
        this.key = value;
    }
    public InvalidPixKeyException(String message) {
        super(message);
        this.type = null;
        this.key = null;
    }

    public PixKeyType getType() {
        return type;
    }

    public String getKey() {
        return key;
    }


}
