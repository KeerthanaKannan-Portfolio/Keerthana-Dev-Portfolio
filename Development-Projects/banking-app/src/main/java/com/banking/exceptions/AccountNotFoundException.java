package com.banking.exceptions;

/**
 * Thrown when an account is not found in the repository.
 */
public class AccountNotFoundException extends RuntimeException {

    private final int accountId;

    public AccountNotFoundException(int accountId) {
        super("Account not found with ID: " + accountId);
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }
}