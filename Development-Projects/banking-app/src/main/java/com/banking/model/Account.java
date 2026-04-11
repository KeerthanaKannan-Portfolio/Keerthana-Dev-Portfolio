package com.banking.model;
import com.banking.model.Transaction;
import com.banking.repository.MySQLTransactionRepository;
import com.banking.util.TransactionLogger;
import com.banking.exceptions.InsufficientFundsException;
import com.banking.exceptions.InvalidAmountException;
import java.util.ArrayList;
import java.util.List;


public abstract class Account {

    private final int accountId;
    private final String accountHolderName;
    protected double balance;
    private final String accountType;

private MySQLTransactionRepository transactionRepository;
public void setTransactionRepository(
        MySQLTransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
}
    
    private final List<Transaction> transactionHistory = new ArrayList<>();

  
    private TransactionLogger logger;

    public void setLogger(TransactionLogger logger) {
        this.logger = logger;
    }
protected void logTransaction(String type, double amount) {
    Transaction transaction = new Transaction.Builder(
            accountId, type, amount, balance)
            .referenceNumber("REF-" + System.currentTimeMillis())
            .remarks("Processed by " + Thread.currentThread().getName())
            .build();

   
    transactionHistory.add(transaction);

    
    if (logger != null) {
        logger.log(transaction);
    }

    
    if (transactionRepository != null) {
        transactionRepository.save(transaction);
    }
}
   
    public Account(int accountId, String accountHolderName,
                   double initialBalance, String accountType) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.accountType = accountType;
    }

   
   
    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        this.balance += amount;
         logTransaction("DEPOSIT", amount);
        System.out.printf("[SUCCESS] Deposited %.2f | New Balance: %.2f%n",
                amount, balance);
    }

   
    public abstract void withdraw(double amount);


    public void transferTo(Account target, double amount) {
        if (target == null) {
            throw new IllegalArgumentException("Target account cannot be null.");
        }
        System.out.printf("[INFO] Transferring %.2f from Account-%d to Account-%d%n",
                amount, this.accountId, target.accountId);
        this.withdraw(amount);
        target.deposit(amount);
    }

public void unsafeWithdraw(double amount) {
    if (amount <= 0) {
        throw new InvalidAmountException(amount);
    }
    if (amount > this.balance) {
        throw new InsufficientFundsException(amount - this.balance);
    }
    
    try {
        Thread.sleep(100); 
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    this.balance -= amount;
    System.out.printf("[Thread: %s] Withdrawn %.2f | Balance: %.2f%n",
            Thread.currentThread().getName(), amount, balance);
}
public void printTransactionHistory() {
        System.out.println("===== Transaction History [A/C: "
                + accountId + "] =====");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        transactionHistory.forEach(System.out::println);
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

    // Getters
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