package com.banking.repository;

import com.banking.model.Account;
import java.util.List;

/**
 * Contract for all account storage implementations.
 * Currently: InMemoryAccountRepository
 * Phase 8:   MySQLAccountRepository
 */
public interface AccountRepository {
    void save(Account account);
    Account findById(int accountId);
    List<Account> findAll();
    void delete(int accountId);
    int count();
}