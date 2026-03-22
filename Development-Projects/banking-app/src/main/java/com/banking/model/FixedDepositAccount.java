package com.banking.model;

import com.banking.exceptions.MaturityDateException;
import com.banking.exceptions.InvalidAmountException;
import java.time.LocalDate;

public class FixedDepositAccount extends Account {

    private final LocalDate maturityDate;
    private final double fixedInterestRate;

    public FixedDepositAccount(int accountId, String accountHolderName,
                                double depositAmount, LocalDate maturityDate,
                                double fixedInterestRate) {
        super(accountId, accountHolderName, depositAmount, "FIXED_DEPOSIT");
        this.maturityDate = maturityDate;
        this.fixedInterestRate = fixedInterestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (LocalDate.now().isBefore(maturityDate)) {
            throw new MaturityDateException(maturityDate);
        }
        super.withdraw(amount);
    }

    @Override
    public void printStatement() {
        super.printStatement();
        System.out.printf(" Maturity Date : %s%n", maturityDate);
        System.out.printf(" Interest Rate : %.2f%%%n", fixedInterestRate);
        System.out.println("-----------------------------------");
    }
}