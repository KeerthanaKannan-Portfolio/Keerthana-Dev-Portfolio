package com.banking.model;

import com.banking.exceptions.MaturityDateException;
import com.banking.exceptions.InvalidAmountException;
import java.time.LocalDate;

public class FixedDepositAccount extends Account {

    private final LocalDate maturityDate;
    private final double fixedInterestRate;
    private boolean isClosed;  

    public FixedDepositAccount(int accountId, String accountHolderName,
                                double depositAmount, LocalDate maturityDate,
                                double fixedInterestRate) {
        super(accountId, accountHolderName, depositAmount, "FIXED_DEPOSIT");
        this.maturityDate = maturityDate;
        this.fixedInterestRate = fixedInterestRate;
        this.isClosed = false;
    }

    
    @Override
    public synchronized  void withdraw(double amount) {

        
        if (isClosed) {
            System.out.println("[ERROR] This Fixed Deposit is already closed.");
            return;
        }

       
        if (LocalDate.now().isBefore(maturityDate)) {
            throw new MaturityDateException(maturityDate);
        }
        if(amount <= 0) {
            throw new InvalidAmountException(amount);
        }
       
        if (amount != this.balance) {
            System.out.printf("[ERROR] Fixed Deposit requires full withdrawal." +
                    " Your maturity amount is: %.2f%n", this.balance);
            return;
        }

        
        this.balance = 0;
        this.isClosed = true;
        logTransaction("WITHDRAWAL", amount);
        System.out.printf("[SUCCESS] Fixed Deposit closed." +
                " Amount %.2f withdrawn successfully.%n", amount);
    }

    
    public double calculateMaturityAmount() {
        double principal = this.getBalance();
        double time = 1; 
        return principal + (principal * fixedInterestRate * time) / 100;
    }

    @Override
    public void printStatement() {
        super.printStatement();
        System.out.printf(" Maturity Date    : %s%n", maturityDate);
        System.out.printf(" Interest Rate    : %.2f%%%n", fixedInterestRate);
        System.out.printf(" Maturity Amount  : %.2f%n", calculateMaturityAmount());
        System.out.printf(" Status           : %s%n", isClosed ? "CLOSED" : "ACTIVE");
        System.out.println("-----------------------------------");
    }

    public LocalDate getMaturityDate()     { return maturityDate; }
    public double getFixedInterestRate()   { return fixedInterestRate; }
    public boolean isClosed()              { return isClosed; }
}