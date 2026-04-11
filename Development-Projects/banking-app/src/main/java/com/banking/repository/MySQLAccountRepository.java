package com.banking.repository;

import com.banking.exceptions.AccountNotFoundException;
import com.banking.model.Account;
import com.banking.model.CheckingAccount;
import com.banking.model.SavingsAccount;
import com.banking.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySQLAccountRepository implements AccountRepository {

    
    @Override
    public void save(Account account) {
        String sql = "INSERT INTO accounts " +
                     "(account_id, holder_name, balance, account_type) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, account.getAccountId());
            stmt.setString(2, account.getAccountHolderName());
            stmt.setDouble(3, account.getBalance());
            stmt.setString(4, account.getAccountType());

            stmt.executeUpdate();
            System.out.println("[DB] Account saved: "
                    + account.getAccountHolderName()
                    + " [ID: " + account.getAccountId() + "]");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "[ERROR] Failed to save account: " + e.getMessage());
        }
    }

    
    @Override
    public Account findById(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToAccount(rs);
            } else {
                throw new AccountNotFoundException(accountId);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "[ERROR] Failed to find account: " + e.getMessage());
        }
    }

    
    @Override
    public List<Account> findAll() {
        String sql = "SELECT * FROM accounts";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapRowToAccount(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "[ERROR] Failed to fetch accounts: " + e.getMessage());
        }
        return accounts;
    }

    
    @Override
    public void delete(int accountId) {
        String sql = "DELETE FROM accounts WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            int rows = stmt.executeUpdate();

            if (rows == 0) {
                throw new AccountNotFoundException(accountId);
            }
            System.out.println("[DB] Account deleted: [ID: " + accountId + "]");

        } catch (SQLException e) {
            throw new RuntimeException(
                    "[ERROR] Failed to delete account: " + e.getMessage());
        }
    }

   
    public void updateBalance(int accountId, double newBalance) {
    String sql = "UPDATE accounts SET balance = ? " +
                 "WHERE account_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setDouble(1, newBalance);
        stmt.setInt(2, accountId);
        stmt.executeUpdate();
        System.out.println("[DB] Balance updated for Account-"
                + accountId + " | New Balance: " + newBalance);

    } catch (SQLException e) {
        throw new RuntimeException(
                "[ERROR] Failed to update balance: "
                + e.getMessage());
    }
}

    
    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM accounts";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "[ERROR] Failed to count accounts: " + e.getMessage());
        }
        return 0;
    }

    
    private Account mapRowToAccount(ResultSet rs) throws SQLException {
        int accountId       = rs.getInt("account_id");
        String holderName   = rs.getString("holder_name");
        double balance      = rs.getDouble("balance");
        String accountType  = rs.getString("account_type");

        switch (accountType) {
            case "SAVINGS":
                return new SavingsAccount(accountId, holderName, balance);
            case "CHECKING":
                return new CheckingAccount(accountId, holderName, balance);
            default:
                throw new RuntimeException(
                        "Unknown account type: " + accountType);
        }
    }
}