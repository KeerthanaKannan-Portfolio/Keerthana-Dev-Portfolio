package com.banking.exceptions;

/**
 * Thrown when a transaction amount is zero or negative.
 */
public class InvalidAmountException extends RuntimeException {

    private final double amount;

    public InvalidAmountException(double amount) {
        super(String.format(
            "Invalid amount: %.2f. Amount must be greater than zero.", amount));
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}