package com.banking.thread;

import com.banking.model.Account;


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