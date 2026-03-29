package com.banking.model;

import com.banking.exceptions.InvalidAmountException;
import com.banking.exceptions.InsufficientFundsException;

public class CheckingAccount extends Account {

    private static final double OVERDRAFT_LIMIT = 5000.00;

    public CheckingAccount(int accountId, String accountHolderName,
                           double initialBalance) {
        super(accountId, accountHolderName, initialBalance, "CHECKING");
    }

    @Override
    public synchronized  void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if ((this.balance - amount) < -OVERDRAFT_LIMIT) {
            throw new InsufficientFundsException(
                    amount - (this.balance + OVERDRAFT_LIMIT));
            // shortfall = how much they exceeded overdraft limit
        }
        this.balance -= amount;
        System.out.printf("[SUCCESS] Withdrawn %.2f | New Balance: %.2f%n",
                amount, balance);
    }
}