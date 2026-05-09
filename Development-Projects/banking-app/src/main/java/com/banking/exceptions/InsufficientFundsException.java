package com.banking.exceptions;


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