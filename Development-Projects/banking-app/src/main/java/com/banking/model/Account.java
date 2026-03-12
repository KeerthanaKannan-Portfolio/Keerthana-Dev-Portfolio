package com.banking.model;

/**
 * Represents a Bank Account.
 *
 * Responsibilities:
 *  - Hold account data
 *  - Enforce deposit/withdrawal business rules
 *
 * Design Decision:
 *  - Fields are private = Encapsulation
 *  - balance has no setter = only deposit/withdraw can modify it
 *  - accountId, accountHolderName are final = immutable after creation
 */
public class Account {

    private final int accountId;
    private final String accountHolderName;
    private double balance;
    private final String accountType;

    // Constructor — the only way to create a valid Account
    public Account(int accountId, String accountHolderName,
                   double initialBalance, String accountType) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.accountType = accountType;
    }

    /**
     * Deposits amount into the account.
     * @param amount must be greater than zero
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Deposit amount must be greater than zero.");
            return;
        }
        this.balance += amount;
        System.out.printf("[SUCCESS] Deposited %.2f | New Balance: %.2f%n", amount, balance);
    }

    /**
     * Withdraws amount from the account.
     * @param amount must be positive and not exceed current balance
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("[ERROR] Withdrawal amount must be greater than zero.");
            return;
        }
        if (amount > this.balance) {
            System.out.println("[ERROR] Insufficient funds.");
            return;
        }
        this.balance -= amount;
        System.out.printf("[SUCCESS] Withdrawn %.2f | New Balance: %.2f%n", amount, balance);
    }

    /**
     * Transfers amount from this account to the target account.
     * @param target  receiving account
     * @param amount  amount to transfer
     */
    public void transferTo(Account target, double amount) {
        if (target == null) {
            System.out.println("[ERROR] Target account cannot be null.");
            return;
        }
        System.out.printf("[INFO] Transferring %.2f from Account-%d to Account-%d%n",
                amount, this.accountId, target.accountId);
        this.withdraw(amount);
        target.deposit(amount);
    }

    /**
     * Prints a mini account statement.
     */
    public void printStatement() {
        System.out.println("-----------------------------------");
        System.out.printf(" Statement for %s [A/C: %d]%n", accountHolderName, accountId);
        System.out.println("-----------------------------------");
        System.out.printf(" Account Type  : %s%n", accountType);
        System.out.printf(" Balance       : %.2f%n", balance);
        System.out.println("-----------------------------------");
    }

    // Getters — no setters for sensitive fields
    public int getAccountId()            { return accountId; }
    public String getAccountHolderName() { return accountHolderName; }
    public double getBalance()           { return balance; }
    public String getAccountType()       { return accountType; }

    @Override
    public String toString() {
        return String.format("Account{id=%d, holder='%s', balance=%.2f, type='%s'}",
                accountId, accountHolderName, balance, accountType);
    }
}