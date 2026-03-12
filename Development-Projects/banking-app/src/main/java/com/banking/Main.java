package com.banking;
import com.banking.model.Account;
    public class Main {
    public static void main(String[] args) {
        System.out.println("Banking Management System - Started");

        Account keerthana=new Account(001,"Keerthana",1000.00,"Savings");
        keerthana.getBalance();
        keerthana.deposit(500.00);
        keerthana.withdraw(200.00);
        keerthana.printStatement();

        Account monish = new Account(002,"Monish",2000.00,"Checking");
        monish.getBalance();
        monish.deposit(1000.00);
        monish.withdraw(500.00);
        monish.printStatement();
        
        keerthana.transferTo(monish, 100);
    }
}
    
