package com.banking.model;

/**
 * Checking Account — extends Account with overdraft facility.
 *
 * Business Rule:
 *  - Overdraft limit of 5000.00 allowed
 *  - Balance can go negative up to overdraft limit
 */
public class CheckingAccount extends Account {

    private static final double OVERDRAFT_LIMIT = 5000.00;

    public CheckingAccount(int accountId, String accountHolderName, double initialBalance) {
        super(accountId, accountHolderName, initialBalance, "CHECKING");
    }

    /**
     * Overrides parent withdraw() with overdraft rule.
     * Allows withdrawal up to overdraft limit beyond current balance.
     */
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Withdrawal amount must be greater than zero.");
            return;
        }
        if ((this.balance - amount) < -OVERDRAFT_LIMIT) {
            System.out.printf("[ERROR] Overdraft limit of %.2f exceeded.%n", OVERDRAFT_LIMIT);
            return;
        }
        this.balance -= amount;
        System.out.printf("[SUCCESS] Withdrawn %.2f | New Balance: %.2f%n", amount, balance);
    }
}