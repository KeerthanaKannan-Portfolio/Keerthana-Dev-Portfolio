package com.banking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single banking transaction.
 * Used for audit logging and transaction history.
 */
public class Transaction {

    private final int accountId;
    private final String type;        // DEPOSIT, WITHDRAWAL, TRANSFER
    private final double amount;
    private final double balanceAfter;
    private final LocalDateTime timestamp;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Transaction(int accountId, String type,
                       double amount, double balanceAfter) {
        this.accountId    = accountId;
        this.type         = type;
        this.amount       = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp    = LocalDateTime.now();
    }

    /**
     * Formats transaction as CSV line for file logging.
     * Format: timestamp,accountId,type,amount,balanceAfter
     */
    public String toCsvLine() {
        return String.format("%s,%d,%s,%.2f,%.2f",
                timestamp.format(FORMATTER),
                accountId, type, amount, balanceAfter);
    }

    @Override
    public String toString() {
        return String.format("[%s] Account-%d | %-10s | Amount: %-10.2f | Balance: %.2f",
                timestamp.format(FORMATTER),
                accountId, type, amount, balanceAfter);
    }

    // Getters
    public int getAccountId()       { return accountId; }
    public String getType()         { return type; }
    public double getAmount()       { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public LocalDateTime getTimestamp() { return timestamp; }
}