package com.banking.util;

import com.banking.model.Transaction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogger {

    private final String logFilePath;

    public TransactionLogger(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    
    public void log(Transaction transaction) {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(transaction.toCsvLine());
            writer.newLine();
            System.out.println("[LOG] Transaction logged: " + transaction);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to log transaction: "
                    + e.getMessage());
        }
    }

  
    public List<String> readAll() {
        List<String> logs = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to read logs: "
                    + e.getMessage());
        }
        return logs;
    }

    
    public List<String> readByAccountId(int accountId) {
        List<String> logs = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
              
                if (line.contains("," + accountId + ",")) {
                    logs.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to read logs: "
                    + e.getMessage());
        }
        return logs;
    }

    
    public void printAll() {
        List<String> logs = readAll();
        if (logs.isEmpty()) {
            System.out.println("[INFO] No transactions found.");
            return;
        }
        System.out.println("===== Transaction Log =====");
        System.out.println("Timestamp            | AccID | Type       | Amount   | Balance");
        System.out.println("------------------------------------------------------------------");
        logs.forEach(System.out::println);
    }
}