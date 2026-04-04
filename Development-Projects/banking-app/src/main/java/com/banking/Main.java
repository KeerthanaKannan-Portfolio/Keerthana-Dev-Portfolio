package com.banking;
import com.banking.model.Account;
import com.banking.model.AccountFactory;
import com.banking.repository.AccountRepository;
import com.banking.repository.InMemoryAccountRepository;
import com.banking.service.AccountService;
import com.banking.util.BankConfig;
import com.banking.util.TransactionLogger;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // Singleton — one config for entire app
        BankConfig config = BankConfig.getInstance();
        System.out.println("===== " + config.getBankName() + " =====");

        // Prove Singleton — same instance
        BankConfig config2 = BankConfig.getInstance();
        System.out.println("Same instance: " + (config == config2)); 

        // Singleton logger
        TransactionLogger logger =
                new TransactionLogger(config.getLogFilePath());

        // Setup
        AccountRepository repository = new InMemoryAccountRepository();
        AccountService    service    = new AccountService(repository);

        // Factory — create accounts without knowing internals
        Account keerthana = AccountFactory.createAccount(
                "SAVINGS", 1001, "Keerthana", 5000.00);
        Account monish    = AccountFactory.createAccount(
                "CHECKING", 1002, "Monish", 2000.00);
        Account priya     = AccountFactory.createAccount(
                "FIXED_DEPOSIT", 1003, "Priya", 50000.00);

        // Inject logger
        keerthana.setLogger(logger);
        monish.setLogger(logger);
        priya.setLogger(logger);

        service.openAccount(keerthana);
        service.openAccount(monish);
        service.openAccount(priya);

        // Transactions — Builder creates Transaction internally
        System.out.println("\n===== Transactions =====");
        service.deposit(1001, 1500);
        service.withdraw(1001, 2000);
        service.transfer(1001, 1002, 500);

        // Print history
        System.out.println();
        keerthana.printTransactionHistory();

        // Read logs
        System.out.println();
        logger.printAll();
    }
}
