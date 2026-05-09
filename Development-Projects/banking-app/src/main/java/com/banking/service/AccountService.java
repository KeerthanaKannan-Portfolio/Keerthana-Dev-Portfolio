package com.banking.service;

import com.banking.model.Account;
import com.banking.repository.AccountRepository;
import com.banking.repository.MySQLAccountRepository;
import com.banking.repository.MySQLTransactionRepository;
import com.banking.util.TransactionLogger;

import java.util.List;

public class AccountService {

    private final AccountRepository accountRepository;
    private MySQLTransactionRepository transactionRepository;
    private TransactionLogger logger;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

   
    public void setTransactionRepository(
            MySQLTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    
    public void setLogger(TransactionLogger logger) {
        this.logger = logger;
    }

   
    private void injectDependencies(Account account) {
        if (logger != null) {
            account.setLogger(logger);
        }
        if (transactionRepository != null) {
            account.setTransactionRepository(transactionRepository);
        }
    }

    public void openAccount(Account account) {
        accountRepository.save(account);
    }

    public void deposit(int accountId, double amount) {
        Account account = accountRepository.findById(accountId);
        injectDependencies(account); 
        account.deposit(amount);
        syncBalance(accountId, account.getBalance());
    }

    public void withdraw(int accountId, double amount) {
        Account account = accountRepository.findById(accountId);
        injectDependencies(account); 
        account.withdraw(amount);
        syncBalance(accountId, account.getBalance());
    }

    public void transfer(int fromAccountId, int toAccountId, double amount) {
        Account fromAccount = accountRepository.findById(fromAccountId);
        Account toAccount   = accountRepository.findById(toAccountId);
        injectDependencies(fromAccount); 
        injectDependencies(toAccount);  
        fromAccount.transferTo(toAccount, amount);
        syncBalance(fromAccountId, fromAccount.getBalance());
        syncBalance(toAccountId, toAccount.getBalance());
    }

    public void printStatement(int accountId) {
        accountRepository.findById(accountId).printStatement();
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void closeAccount(int accountId) {
        accountRepository.delete(accountId);
    }

    private void syncBalance(int accountId, double newBalance) {
        if (accountRepository instanceof MySQLAccountRepository) {
            ((MySQLAccountRepository) accountRepository)
                    .updateBalance(accountId, newBalance);
        }
    }
}