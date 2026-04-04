package com.banking.model;

import java.time.LocalDate;

/**
 * Factory — centralizes account creation logic.
 *
 * Why Factory?
 *  - Caller doesn't need to know HOW accounts are created
 *  - Adding new account type = change only this class
 *  - Consistent creation logic across entire application
 */
public class AccountFactory {


    // Private constructor — no need to instantiate factory
    private AccountFactory() { }

    /**
     * Creates account based on type string.
     * Caller just says what type — factory handles the rest.
     *
     * @param type    "SAVINGS", "CHECKING", "FIXED_DEPOSIT"
     * @param accountId  unique account ID
     * @param holderName account holder name
     * @param initialBalance opening balance
     * @return correct Account subclass
     */
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
                        LocalDate.now().plusYears(1), // default 1 year maturity
                        6.5);                          // default interest rate

            default:
                throw new IllegalArgumentException(
                        "Unknown account type: " + type);
        }
    }
}