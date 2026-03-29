package com.banking.model;

import com.banking.exceptions.MaturityDateException;
import com.banking.exceptions.InvalidAmountException;
import java.time.LocalDate;

/**
 * Fixed Deposit Account — extends Account.
 *
 * Business Rules:
 *  - No withdrawals allowed before maturity date
 *  - On maturity — entire amount must be withdrawn at once
 *  - Account closes after withdrawal (balance becomes 0)
 */
public class FixedDepositAccount extends Account {

    private final LocalDate maturityDate;
    private final double fixedInterestRate;
    private boolean isClosed;   // tracks if FD is closed after withdrawal

    public FixedDepositAccount(int accountId, String accountHolderName,
                                double depositAmount, LocalDate maturityDate,
                                double fixedInterestRate) {
        super(accountId, accountHolderName, depositAmount, "FIXED_DEPOSIT");
        this.maturityDate = maturityDate;
        this.fixedInterestRate = fixedInterestRate;
        this.isClosed = false;
    }

    /**
     * Withdraws entire FD amount on maturity.
     *
     * Business Rules:
     *  - Cannot withdraw before maturity date
     *  - Cannot withdraw if FD is already closed
     *  - Withdraws FULL amount — partial withdrawal not allowed
     *  - FD closes after withdrawal
     */
    @Override
    public synchronized  void withdraw(double amount) {

        // Rule 1 — FD already closed
        if (isClosed) {
            System.out.println("[ERROR] This Fixed Deposit is already closed.");
            return;
        }

        // Rule 2 — maturity not reached yet
        if (LocalDate.now().isBefore(maturityDate)) {
            throw new MaturityDateException(maturityDate);
        }
        if(amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        // Rule 3 — must withdraw full amount
        if (amount != this.balance) {
            System.out.printf("[ERROR] Fixed Deposit requires full withdrawal." +
                    " Your maturity amount is: %.2f%n", this.balance);
            return;
        }

        // All rules passed — close the FD
        this.balance = 0;
        this.isClosed = true;
        System.out.printf("[SUCCESS] Fixed Deposit closed." +
                " Amount %.2f withdrawn successfully.%n", amount);
    }

    /**
     * Calculates maturity amount including interest.
     * Simple Interest = (Principal * Rate * Time) / 100
     */
    public double calculateMaturityAmount() {
        double principal = this.getBalance();
        double time = 1; // 1 year
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