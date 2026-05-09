package com.banking.model;

import com.banking.exceptions.InvalidAmountException;
import com.banking.exceptions.MinimumBalanceException;

public class SavingsAccount extends Account {

    private static final double MINIMUM_BALANCE = 1000.00;

    public SavingsAccount(int accountId, String accountHolderName,
                          double initialBalance) {
        super(accountId, accountHolderName, initialBalance, "SAVINGS");
    }

    @Override
    public synchronized  void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if ((this.balance - amount) < MINIMUM_BALANCE) {
            throw new MinimumBalanceException(MINIMUM_BALANCE, this.balance);
        }
        this.balance -= amount;
        logTransaction("WITHDRAWAL", amount);
        System.out.printf("[SUCCESS] Withdrawn %.2f | New Balance: %.2f%n",
                amount, balance);
    }
}