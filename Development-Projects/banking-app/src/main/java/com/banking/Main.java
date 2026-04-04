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
import com.banking.thread.SafeWithdrawalTask;
import com.banking.thread.WithdrawalTask;
import com.banking.util.TransactionLogger;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        // Wire up service and repository
        AccountRepository repository = new InMemoryAccountRepository();
       AccountService service = new AccountService(repository);
  
        // // Open accounts
        // System.out.println("===== Opening Accounts =====");
        // service.openAccount(new SavingsAccount(1001, "Keerthana", 5000.00));
        // service.openAccount(new CheckingAccount(1002, "Monish", 2000.00));
        // service.openAccount(new FixedDepositAccount(
        //         1003, "Priya", 50000.00,
        //         LocalDate.now().plusYears(1), 6.5));

        // // Deposit
        // System.out.println("\n===== Deposits =====");
        // try {
        //     service.deposit(1001, 1500);
        //     service.deposit(1002, 1000);
        // } catch (InvalidAmountException e) {
        //     System.out.println("[ERROR] " + e.getMessage());
        // }

        // // Withdraw
        // System.out.println("\n===== Withdrawals =====");
        // try {
        //     service.withdraw(1001, 2000);
        //     service.withdraw(1002, 500);
        // } catch (MinimumBalanceException | InsufficientFundsException e) {
        //     System.out.println("[ERROR] " + e.getMessage());
        // }

        // // Transfer
        // System.out.println("\n===== Transfer =====");
        // try {
        //     service.transfer(1001, 1002, 1000);
        // } catch (InsufficientFundsException | MinimumBalanceException e) {
        //     System.out.println("[ERROR] " + e.getMessage());
        // }

        // // Account not found
        // System.out.println("\n===== Account Not Found =====");
        // try {
        //     service.deposit(9999, 500);
        // } catch (AccountNotFoundException e) {
        //     System.out.println("[ERROR] " + e.getMessage());
        // }

        // // Print all statements
        // System.out.println("\n===== All Account Statements =====");
        // List<Account> accounts = service.getAllAccounts();
        // for (Account account : accounts) {
        //     account.printStatement();
        // }

        // // Total accounts
        // System.out.println("Total Accounts: " + repository.count());

        //  service.openAccount(new SavingsAccount(1001, "Keerthana", 5000.00));
        // service.openAccount(new CheckingAccount(1002, "Monish", 2000.00));

//         // ===== Phase 4 — Race Condition Demo =====
//         System.out.println("===== UNSAFE — Race Condition Demo =====");
//         Account sharedAccount = new SavingsAccount(2001, "Shared", 5000.00);

//         Thread thread1 = new Thread(
//                 new WithdrawalTask(sharedAccount, 3000), "ATM-Thread");
//         Thread thread2 = new Thread(
//                 new WithdrawalTask(sharedAccount, 3000), "UPI-Thread");

//         thread1.start();
//         thread2.start();
//        try {
//     thread1.join();
//     thread2.join();
// } catch (InterruptedException e) {
//     Thread.currentThread().interrupt();
// }

//         System.out.println("UNSAFE Final Balance: " + sharedAccount.getBalance());

//         // ===== Safe Demo =====
//         System.out.println("\n===== SAFE — Synchronized Demo =====");
//         Account safeAccount = new SavingsAccount(2002, "Safe", 5000.00);

//         Thread safeThread1 = new Thread(
//                 new SafeWithdrawalTask(safeAccount, 3000), "ATM-Thread");
//         Thread safeThread2 = new Thread(
//                 new SafeWithdrawalTask(safeAccount, 3000), "UPI-Thread");

//         safeThread1.start();
//         safeThread2.start();
//        try {
//     safeThread1.join();
//     safeThread2.join();
// } catch (InterruptedException e) {
//     Thread.currentThread().interrupt();
// }

//         System.out.println("SAFE Final Balance: " + safeAccount.getBalance());
// System.out.println("\n===== Thread Pool Demo =====");
// Account poolAccount = new SavingsAccount(3001, "ThreadPool-Test", 50000.00);

// // Create thread pool with 5 threads
// ExecutorService threadPool = Executors.newFixedThreadPool(5);

// // Submit 10 withdrawal tasks
// for (int i = 1; i <= 10; i++) {
//     threadPool.submit(new SafeWithdrawalTask(poolAccount, 1000));
// }

// // Shutdown and wait
// threadPool.shutdown();
// try {
//     if (!threadPool.awaitTermination(1, TimeUnit.MINUTES)) {
//         threadPool.shutdownNow();
//     }
// } catch (InterruptedException e) {
//     threadPool.shutdownNow();
//     Thread.currentThread().interrupt();
// }

// System.out.println("Final Balance after 10 withdrawals: "
//         + poolAccount.getBalance());
//     }
// Setup logger
        TransactionLogger logger =
                new TransactionLogger("transactions.log");

        Account keerthana = new SavingsAccount(1001, "Keerthana", 5000.00);
        Account monish    = new CheckingAccount(1002, "Monish", 2000.00);

        // Inject logger into accounts
        keerthana.setLogger(logger);
        monish.setLogger(logger);

        service.openAccount(keerthana);
        service.openAccount(monish);

        // Perform transactions
        System.out.println("===== Transactions =====");
        service.deposit(1001, 1500);
        service.withdraw(1001, 2000);
        service.deposit(1002, 1000);
        service.transfer(1001, 1002, 500);

        // Print in-memory history
        System.out.println();
        keerthana.printTransactionHistory();
        System.out.println();
        monish.printTransactionHistory();

        // Read from log file
        System.out.println();
        logger.printAll();

        // Read specific account logs
        System.out.println("\n===== Keerthana's File Logs =====");
        logger.readByAccountId(1001)
              .forEach(System.out::println);
}
}