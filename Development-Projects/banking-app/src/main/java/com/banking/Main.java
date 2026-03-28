package com.banking;

import com.banking.exceptions.AccountNotFoundException;
import com.banking.exceptions.InsufficientFundsException;
import com.banking.exceptions.InvalidAmountException;
import com.banking.exceptions.MinimumBalanceException;
import com.banking.model.Account;
import com.banking.model.CheckingAccount;
import com.banking.model.FixedDepositAccount;
import com.banking.model.SavingsAccount;
import com.banking.repository.AccountRepository;
import com.banking.repository.InMemoryAccountRepository;
import com.banking.service.AccountService;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Wire up service and repository
       AccountRepository repository = new InMemoryAccountRepository();
       AccountService service = new AccountService(repository);
  
        // Open accounts
        System.out.println("===== Opening Accounts =====");
        service.openAccount(new SavingsAccount(1001, "Keerthana", 5000.00));
        service.openAccount(new CheckingAccount(1002, "Monish", 2000.00));
        service.openAccount(new FixedDepositAccount(
                1003, "Priya", 50000.00,
                LocalDate.now().plusYears(1), 6.5));

        // Deposit
        System.out.println("\n===== Deposits =====");
        try {
            service.deposit(1001, 1500);
            service.deposit(1002, 1000);
        } catch (InvalidAmountException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        // Withdraw
        System.out.println("\n===== Withdrawals =====");
        try {
            service.withdraw(1001, 2000);
            service.withdraw(1002, 500);
        } catch (MinimumBalanceException | InsufficientFundsException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        // Transfer
        System.out.println("\n===== Transfer =====");
        try {
            service.transfer(1001, 1002, 1000);
        } catch (InsufficientFundsException | MinimumBalanceException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        // Account not found
        System.out.println("\n===== Account Not Found =====");
        try {
            service.deposit(9999, 500);
        } catch (AccountNotFoundException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        // Print all statements
        System.out.println("\n===== All Account Statements =====");
        List<Account> accounts = service.getAllAccounts();
        for (Account account : accounts) {
            account.printStatement();
        }

        // Total accounts
        System.out.println("Total Accounts: " + repository.count());
    }
}