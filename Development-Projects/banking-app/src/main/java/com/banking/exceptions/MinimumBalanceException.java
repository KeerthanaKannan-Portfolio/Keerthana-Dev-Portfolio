package com.banking.exceptions;


public class MinimumBalanceException extends RuntimeException {

    private final double minimumBalance;
    private final double currentBalance;

    public MinimumBalanceException(double minimumBalance, double currentBalance) {
        super(String.format(
            "Minimum balance of %.2f must be maintained. Current balance: %.2f",
            minimumBalance, currentBalance));
        this.minimumBalance = minimumBalance;
        this.currentBalance = currentBalance;
    }

    public double getMinimumBalance() { return minimumBalance; }
    public double getCurrentBalance() { return currentBalance; }
}