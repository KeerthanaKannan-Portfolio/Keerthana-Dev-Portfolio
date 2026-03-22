package com.banking;

import com.banking.model.Account;
import com.banking.model.SavingsAccount;
import com.banking.model.CheckingAccount;
import com.banking.model.FixedDepositAccount;
import com.banking.exceptions.InsufficientFundsException;
import com.banking.exceptions.InvalidAmountException;
import com.banking.exceptions.MinimumBalanceException;
import com.banking.exceptions.MaturityDateException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Account keerthana = new SavingsAccount(1001, "Keerthana", 5000.00);
        Account monish    = new CheckingAccount(1002, "Monish", 2000.00);
        Account fd        = new FixedDepositAccount(
                                1003, "Keerthana", 50000.00,
                                LocalDate.now().plusYears(1), 6.5);

        // Test InvalidAmountException
        System.out.println("===== Test Invalid Amount =====");
        try {
            keerthana.deposit(-500);
        } catch (InvalidAmountException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        // Test MinimumBalanceException
        System.out.println("\n===== Test Minimum Balance =====");
        try {
            keerthana.withdraw(4500);
        } catch (MinimumBalanceException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        // Test InsufficientFundsException
        System.out.println("\n===== Test Insufficient Funds =====");
        try {
            monish.withdraw(9000);
        } catch (InsufficientFundsException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        // Test MaturityDateException
        System.out.println("\n===== Test Maturity Date =====");
        try {
            fd.withdraw(5000);
        } catch (MaturityDateException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        // Test successful operations
        System.out.println("\n===== Successful Operations =====");
        try {
            keerthana.deposit(1000);
            keerthana.withdraw(2000);
            keerthana.printStatement();
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
}