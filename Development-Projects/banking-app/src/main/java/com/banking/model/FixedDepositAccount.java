package com.banking.model;

import java.time.LocalDate;

/**
 * Fixed Deposit Account — extends Account.
 *
 * Business Rule:
 *  - No withdrawals allowed before maturity date
 */
public class FixedDepositAccount extends Account 
{

    private final LocalDate maturityDate;
    private final double fixedInterestRate;

    public FixedDepositAccount(int accountId, String accountHolderName,
                                double depositAmount, LocalDate maturityDate,
                                double fixedInterestRate) 
     {
        super(accountId, accountHolderName, depositAmount, "FIXED_DEPOSIT");
        this.maturityDate = maturityDate;
        this.fixedInterestRate = fixedInterestRate;
    }

    @Override
    public void withdraw(double amount)
     {
        LocalDate today = LocalDate.now();
        if (today.isBefore(maturityDate)) {
            System.out.println("[ERROR] No withdrawals allowed before maturity date: "
                    + maturityDate);
            return;
        }
        // maturity reached — allow withdrawal
        super.withdraw(amount);
    }

    @Override
    public void printStatement() 
    {
        super.printStatement();
        System.out.printf(" Maturity Date    : %s%n", maturityDate);
        System.out.printf(" Interest Rate    : %.2f%%%n", fixedInterestRate);
        System.out.println("-----------------------------------");
    
       }
}

