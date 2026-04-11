package com.banking.model;

import java.time.LocalDate;


public class AccountFactory {


   
    private AccountFactory() { }
    public static Account createAccount(String type, int accountId,
                                         String holderName,
                                         double initialBalance) {
        switch (type.toUpperCase()) {
            case "SAVINGS":
                return new SavingsAccount(accountId, holderName, initialBalance);

            case "CHECKING":
                return new CheckingAccount(accountId, holderName, initialBalance);

            case "FIXED_DEPOSIT":
                return new FixedDepositAccount(
                        accountId, holderName, initialBalance,
                        LocalDate.now().plusYears(1), 
                        6.5);                          

            default:
                throw new IllegalArgumentException(
                        "Unknown account type: " + type);
        }
    }
}