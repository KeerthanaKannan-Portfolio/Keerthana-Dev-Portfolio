package com.banking.service;

import com.banking.exceptions.AccountNotFoundException;
import com.banking.model.Account;
import com.banking.repository.AccountRepository;

import java.util.List;

/**
 * Handles all account related business operations.
 *
 * Acts as bridge between Main and AccountRepository.
 * Main never talks to Repository directly — always through Service.
 */
public class AccountService {

    // Change this line
private final AccountRepository accountRepository;

public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
}
    /**
     * Opens a new account.
     */
    public void openAccount(Account account) {
        accountRepository.save(account);
    }

    /**
     * Deposits amount into account by ID.
     */
    public void deposit(int accountId, double amount) {
        Account account = accountRepository.findById(accountId);
        account.deposit(amount);
    }

    /**
     * Withdraws amount from account by ID.
     */
    public void withdraw(int accountId, double amount) {
        Account account = accountRepository.findById(accountId);
        account.withdraw(amount);
    }

    /**
     * Transfers amount between two accounts by ID.
     */
    public void transfer(int fromAccountId, int toAccountId, double amount) {
        Account fromAccount = accountRepository.findById(fromAccountId);
        Account toAccount   = accountRepository.findById(toAccountId);
        fromAccount.transferTo(toAccount, amount);
    }

    /**
     * Prints statement for account by ID.
     */
    public void printStatement(int accountId) {
        Account account = accountRepository.findById(accountId);
        account.printStatement();
    }

    /**
     * Returns all accounts.
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Closes an account by ID.
     */
    public void closeAccount(int accountId) {
        accountRepository.delete(accountId);
    }
}