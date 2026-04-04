package com.banking.util;

/**
 * Singleton — holds bank-wide configuration.
 *
 * Why Singleton?
 *  - Bank config is same across entire application
 *  - Only ONE instance needed
 *  - All classes share same config
 *
 * Thread-safe using double-checked locking.
 */
public class BankConfig {

    // Step 1 — static instance — belongs to class not object
    private static BankConfig instance;

    // Bank configuration
    private final String bankName;
    private final String logFilePath;
    private final double savingsMinimumBalance;
    private final double checkingOverdraftLimit;

    // Step 2 — private constructor
    // Nobody outside can do: new BankConfig() 
    private BankConfig() {
        this.bankName               = "Keerthana's Bank";
        this.logFilePath            = "transactions.log";
        this.savingsMinimumBalance  = 1000.00;
        this.checkingOverdraftLimit = 5000.00;
    }

    /**
     * Step 3 — public static method to get instance.
     * Creates instance only ONCE — returns same instance every time.
     *
     * Double-checked locking — thread safe 
     */
    public static BankConfig getInstance() {
        if (instance == null) 
            {                    // check 1 — avoid locking every time
            synchronized (BankConfig.class)
             {
                if (instance == null) 
                    {            // check 2 — avoid duplicate creation
                    instance = new BankConfig();
                }
            }
        }
        return instance;
    }

    // Getters
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