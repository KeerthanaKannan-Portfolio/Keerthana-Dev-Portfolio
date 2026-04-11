package com.banking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private final int accountId;
    private final String type;
    private final double amount;
    private final double balanceAfter;
    private final LocalDateTime timestamp;
    private final String referenceNumber;  
    private final String remarks;          

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

   
    private Transaction(Builder builder) {
        this.accountId       = builder.accountId;
        this.type            = builder.type;
        this.amount          = builder.amount;
        this.balanceAfter    = builder.balanceAfter;
        this.timestamp       = builder.timestamp;
        this.referenceNumber = builder.referenceNumber;
        this.remarks         = builder.remarks;
    }

    
    public static class Builder {

       
        private final int accountId;
        private final String type;
        private final double amount;
        private final double balanceAfter;

        
        private LocalDateTime timestamp    = LocalDateTime.now();
        private String referenceNumber     = "N/A";
        private String remarks             = "";

       
        public Builder(int accountId, String type,
                       double amount, double balanceAfter) {
            this.accountId    = accountId;
            this.type         = type;
            this.amount       = amount;
            this.balanceAfter = balanceAfter;
        }

       
        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;           
        }

        public Builder referenceNumber(String referenceNumber) {
            this.referenceNumber = referenceNumber;
            return this;
        }

        public Builder remarks(String remarks) {
            this.remarks = remarks;
            return this;
        }

       
        public Transaction build() {
            return new Transaction(this);
        }
    }

    public String toCsvLine() {
        return String.format("%s,%d,%s,%.2f,%.2f,%s,%s",
                timestamp.format(FORMATTER),
                accountId, type, amount, balanceAfter,
                referenceNumber, remarks);
    }

    @Override
    public String toString() {
        return String.format("[%s] Account-%d | %-10s | Amount: %-10.2f | Balance: %-10.2f | Ref: %s",
                timestamp.format(FORMATTER),
                accountId, type, amount, balanceAfter, referenceNumber);
    }

    // Getters
    public int getAccountId()           { return accountId; }
    public String getType()             { return type; }
    public double getAmount()           { return amount; }
    public double getBalanceAfter()     { return balanceAfter; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getReferenceNumber()  { return referenceNumber; }
    public String getRemarks()          { return remarks; }
}