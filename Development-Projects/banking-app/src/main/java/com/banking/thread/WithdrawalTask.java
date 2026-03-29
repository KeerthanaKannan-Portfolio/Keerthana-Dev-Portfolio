package com.banking.thread;

import com.banking.model.Account;

/**
 * Simulates a withdrawal transaction running in its own thread.
 * Implements Runnable — preferred over extending Thread.
 *
 * Why Runnable over Thread?
 *  - Java allows only ONE parent class
 *  - If we extend Thread — can't extend anything else
 *  - Runnable is just a task — Thread is the worker
 */
public class WithdrawalTask implements Runnable {

    private final Account account;
    private final double amount;

    public WithdrawalTask(Account account, double amount) {
        this.account = account;
        this.amount  = amount;
    }

    @Override
    public void run() {
        try {
            account.unsafeWithdraw(amount);
        } catch (Exception e) {
            System.out.printf("[Thread: %s] [ERROR] %s%n",
                    Thread.currentThread().getName(), e.getMessage());
        }
    }
}