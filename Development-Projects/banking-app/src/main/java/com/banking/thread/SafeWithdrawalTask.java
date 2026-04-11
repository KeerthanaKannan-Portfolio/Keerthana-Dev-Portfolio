package com.banking.thread;

import com.banking.model.Account;


public class SafeWithdrawalTask implements Runnable {

    private final Account account;
    private final double amount;

    public SafeWithdrawalTask(Account account, double amount) {
        this.account = account;
        this.amount  = amount;
    }

    @Override
    public void run() {
        try {
            account.withdraw(amount);
        } catch (Exception e) {
            System.out.printf("[Thread: %s] [ERROR] %s%n",
                    Thread.currentThread().getName(), e.getMessage());
        }
    }
}