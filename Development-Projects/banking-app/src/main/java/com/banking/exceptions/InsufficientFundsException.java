package com.banking.exceptions;

/**
 * Thrown when a withdrawal or transfer exceeds available balance.
 * Includes the shortfall amount for reporting purposes.
 */
public class InsufficientFundsException extends RuntimeException {

    private final double shortfall;

    public InsufficientFundsException(double shortfall) {
        super(String.format(
            "Insufficient funds. Shortfall amount: %.2f", shortfall));
        this.shortfall = shortfall;
    }

    public double getShortfall() {
        return shortfall;
    }
}