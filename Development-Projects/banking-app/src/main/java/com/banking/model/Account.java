package com.banking.model;

import com.banking.exceptions.InvalidAmountException;
import com.banking.exceptions.InsufficientFundsException;

public class Account {

    private final int accountId;
    private final String accountHolderName;
    protected double balance;
    private final String accountType;

    public Account(int accountId, String accountHolderName,
                   double initialBalance, String accountType) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.accountType = accountType;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        this.balance += amount;
        System.out.printf("[SUCCESS] Deposited %.2f | New Balance: %.2f%n",
                amount, balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (amount > this.balance) {
            throw new InsufficientFundsException(amount - this.balance);
        }
        this.balance -= amount;
        System.out.printf("[SUCCESS] Withdrawn %.2f | New Balance: %.2f%n",
                amount, balance);
    }

    public void transferTo(Account target, double amount) {
        if (target == null) {
            throw new IllegalArgumentException("Target account cannot be null.");
        }
        System.out.printf("[INFO] Transferring %.2f from Account-%d to Account-%d%n",
                amount, this.accountId, target.accountId);
        this.withdraw(amount);
        target.deposit(amount);
    }

    public void printStatement() {
        System.out.println("-----------------------------------");
        System.out.printf(" Statement for %s [A/C: %d]%n",
                accountHolderName, accountId);
        System.out.println("-----------------------------------");
        System.out.printf(" Account Type  : %s%n", accountType);
        System.out.printf(" Balance       : %.2f%n", balance);
        System.out.println("-----------------------------------");
    }

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