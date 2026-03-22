package com.banking;

import com.banking.model.Account;
import com.banking.model.SavingsAccount;
import com.banking.model.CheckingAccount;

public class Main {

    public static void main(String[] args) {

        // Parent type reference — Child object
        // This is Polymorphism
        Account keerthana = new SavingsAccount(1001, "Keerthana", 5000.00);
        Account monish    = new CheckingAccount(1002, "Monish", 2000.00);

        System.out.println("===== Savings Account =====");
        keerthana.deposit(500);
        keerthana.withdraw(4200);  // fails — minimum balance rule
        keerthana.withdraw(1000);  // succeeds

        System.out.println();
       System.out.println("===== Checking Account =====");
monish.deposit(1000);
monish.withdraw(6000);     // Balance: -3000 ✅ within limit
monish.withdraw(2000);     // Balance: -5000 ✅ exactly at limit
monish.withdraw(1);        // Balance: -5001 ❌ exceeds limit — should fail

        System.out.println();
        keerthana.printStatement();
        monish.printStatement();
    }
}