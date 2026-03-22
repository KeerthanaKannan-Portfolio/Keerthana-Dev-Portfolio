package com.banking.model;

/**
 * Savings Account — extends Account with minimum balance rule.
 *
 * Business Rule:
 *  - Minimum balance of 1000.00 must be maintained at all times
 *  - Withdrawal is blocked if it causes balance to drop below minimum
 */
public class SavingsAccount extends Account {

    private static final double MINIMUM_BALANCE = 1000.00;

    // Constructor — calls parent constructor using super()
    public SavingsAccount(int accountId, String accountHolderName, double initialBalance) {
        super(accountId, accountHolderName, initialBalance, "SAVINGS");
    }

    /**
     * Overrides parent withdraw() with savings specific rule.
     * Balance cannot drop below MINIMUM_BALANCE.
     */
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Withdrawal amount must be greater than zero.");
            return;
        }
        if ((this.balance - amount) < MINIMUM_BALANCE) {
            System.out.printf("[ERROR] Insufficient funds. Minimum balance of %.2f must be maintained.%n",
                    MINIMUM_BALANCE);
            return;
        }
        this.balance -= amount;
        System.out.printf("[SUCCESS] Withdrawn %.2f | New Balance: %.2f%n", amount, balance);
    }
}