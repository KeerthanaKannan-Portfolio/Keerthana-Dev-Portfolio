package com.banking;

import com.banking.model.Account;
import com.banking.model.AccountFactory;
import com.banking.repository.AccountRepository;
import com.banking.repository.MySQLAccountRepository;
import com.banking.repository.MySQLTransactionRepository;
import com.banking.service.AccountService;
import com.banking.util.BankConfig;
import com.banking.util.DatabaseConnection;
import com.banking.util.TransactionLogger;

public class Main {

    public static void main(String[] args) throws InterruptedException {

      
        BankConfig config = BankConfig.getInstance();
        System.out.println("===== " + config.getBankName() + " =====");

    
        TransactionLogger logger =
                new TransactionLogger(config.getLogFilePath());

       
        MySQLTransactionRepository transactionRepository =
                new MySQLTransactionRepository();

        
        AccountRepository repository = new MySQLAccountRepository();
        AccountService    service    = new AccountService(repository);

        service.setLogger(logger);
        service.setTransactionRepository(transactionRepository);

       
        Account keerthana = AccountFactory.createAccount(
                "SAVINGS", 1001, "Keerthana", 5000.00);
        Account monish    = AccountFactory.createAccount(
                "CHECKING", 1002, "Monish", 2000.00);

        
        System.out.println("\n===== Opening Accounts =====");
        service.openAccount(keerthana);
        service.openAccount(monish);

       
        System.out.println("\n===== Transactions =====");
        try {
            service.deposit(1001, 1500);
            service.withdraw(1001, 2000);
            service.deposit(1002, 1000);
            service.transfer(1001, 1002, 500);
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

     
        System.out.println("\n===== All Accounts from Database =====");
        service.getAllAccounts().forEach(Account::printStatement);

       
        System.out.println("\n===== Keerthana's Transactions from DB =====");
        transactionRepository.findByAccountId(1001)
                .forEach(System.out::println);

        System.out.println("\n===== All Transactions from DB =====");
        transactionRepository.findAll()
                .forEach(System.out::println);

       
        System.out.println("\nTotal Accounts in DB: "
                + ((MySQLAccountRepository) repository).count());

       
        DatabaseConnection.closeConnection();
    }
}