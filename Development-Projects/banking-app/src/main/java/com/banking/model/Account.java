package com.banking.model;
import com.banking.model.Transaction;
import com.banking.util.TransactionLogger;
import com.banking.exceptions.InsufficientFundsException;
import com.banking.exceptions.InvalidAmountException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all bank accounts.
 *
 * Why abstract?
 *  - A plain "Account" doesn't exist in real banking
 *  - Always a SavingsAccount, CheckingAccount or FixedDepositAccount
 *  - withdraw() is abstract — each account type has its own rule
 *  - deposit() is concrete — same for all account types
 */
public abstract class Account {

    private final int accountId;
    private final String accountHolderName;
    protected double balance;
    private final String accountType;

    // In-memory transaction history
    private final List<Transaction> transactionHistory = new ArrayList<>();

    // Logger — injected from outside
    private TransactionLogger logger;
 // Inject logger from outside
    public void setLogger(TransactionLogger logger) {
        this.logger = logger;
    }

    /**
     * Logs transaction to file and adds to in-memory history.
     */
    protected void logTransaction(String type, double amount) {
        Transaction transaction =
                new Transaction(accountId, type, amount, balance);
        transactionHistory.add(transaction);
        if (logger != null) {
            logger.log(transaction);
        }
    }
    // Abstract class CAN have constructor
    // Used by child classes via super()
    public Account(int accountId, String accountHolderName,
                   double initialBalance, String accountType) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.accountType = accountType;
    }

   
    // Concrete method — same for ALL account types
    // No need to override in child classes
    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        this.balance += amount;
         logTransaction("DEPOSIT", amount);
        System.out.printf("[SUCCESS] Deposited %.2f | New Balance: %.2f%n",
                amount, balance);
    }

    // Abstract method — every child MUST implement their own rule
    // SavingsAccount  → minimum balance rule
    // CheckingAccount → overdraft rule
    // FixedDeposit    → maturity date rule
    public abstract void withdraw(double amount);

    // Concrete method — same for ALL account types
    public void transferTo(Account target, double amount) {
        if (target == null) {
            throw new IllegalArgumentException("Target account cannot be null.");
        }
        System.out.printf("[INFO] Transferring %.2f from Account-%d to Account-%d%n",
                amount, this.accountId, target.accountId);
        this.withdraw(amount);
        target.deposit(amount);
    }
/**
 * UNSAFE withdraw — demonstrates race condition.
 * Never use this in production!
 */
public void unsafeWithdraw(double amount) {
    if (amount <= 0) {
        throw new InvalidAmountException(amount);
    }
    if (amount > this.balance) {
        throw new InsufficientFundsException(amount - this.balance);
    }
    // Simulate processing delay — makes race condition visible
    try {
        Thread.sleep(100); // 100ms delay
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
    // Concrete method — base statement
    // Child classes can override to add extra info (like FixedDeposit does)
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