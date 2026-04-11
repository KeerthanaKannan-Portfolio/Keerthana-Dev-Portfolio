package com.banking.repository;

import com.banking.exceptions.AccountNotFoundException;
import com.banking.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAccountRepository implements AccountRepository {

    private final Map<Integer, Account> accountStore = new HashMap<>();

    @Override
    public void save(Account account) {
        if (accountStore.containsKey(account.getAccountId())) {
            throw new IllegalArgumentException(
                "Account with ID " + account.getAccountId() + " already exists.");
        }
        accountStore.put(account.getAccountId(), account);
        System.out.println("[INFO] Account created: " + account.getAccountHolderName()
                + " [ID: " + account.getAccountId() + "]");
    }

    @Override
    public Account findById(int accountId) {
        Account account = accountStore.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException(accountId);
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accountStore.values());
    }

    @Override
    public void delete(int accountId) {
        if (!accountStore.containsKey(accountId)) {
            throw new AccountNotFoundException(accountId);
        }
        accountStore.remove(accountId);
        System.out.println("[INFO] Account deleted: [ID: " + accountId + "]");
    }

    @Override
    public int count() {
        return accountStore.size();
    }
}