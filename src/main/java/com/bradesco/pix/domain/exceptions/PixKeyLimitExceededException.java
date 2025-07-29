package com.bradesco.pix.domain.exceptions;

public class PixKeyLimitExceededException extends BusinessRuleException {
    private final int currentCount;
    private final int maxAllowed;

    public PixKeyLimitExceededException(int currentCount, int maxAllowed) {
        super(String.format("PIX key limit exceeded. Current: %d, Max allowed: %d",
                currentCount, maxAllowed));
        this.currentCount = currentCount;
        this.maxAllowed = maxAllowed;
    }

    public int getCurrentCount() { return currentCount; }
    public int getMaxAllowed() { return maxAllowed; }
}
