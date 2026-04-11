package com.banking.util;


public class BankConfig {

    
    private static BankConfig instance;

   
    private final String bankName;
    private final String logFilePath;
    private final double savingsMinimumBalance;
    private final double checkingOverdraftLimit;

   
    private BankConfig() {
        this.bankName               = "Keerthana's Bank";
        this.logFilePath            = "transactions.log";
        this.savingsMinimumBalance  = 1000.00;
        this.checkingOverdraftLimit = 5000.00;
    }

    
    public static BankConfig getInstance() {
        if (instance == null) 
            {                    
            synchronized (BankConfig.class)
             {
                if (instance == null) 
                    {            
                    instance = new BankConfig();
                }
            }
        }
        return instance;
    }


    public String getBankName()               { return bankName; }
    public String getLogFilePath()            { return logFilePath; }
    public double getSavingsMinimumBalance()  { return savingsMinimumBalance; }
    public double getCheckingOverdraftLimit() { return checkingOverdraftLimit; }

    @Override
    public String toString() {
        return String.format("BankConfig{name='%s', logFile='%s'}", 
                bankName, logFilePath);
    }
}