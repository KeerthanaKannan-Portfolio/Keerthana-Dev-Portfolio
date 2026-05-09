package com.banking.util;


public class EncryptionGenerator {

    public static void main(String[] args) {

        String password = "";

        System.out.println("===== Encrypted Credentials =====");
        System.out.println("db.password=" + EncryptionUtil.encrypt(password));
    }
}
