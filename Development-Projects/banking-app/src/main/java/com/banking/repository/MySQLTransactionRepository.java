package com.banking.repository;

import com.banking.model.Transaction;
import com.banking.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and retrieving transactions from MySQL.
 */
public class MySQLTransactionRepository {

    /**
     * Saves transaction to MySQL transactions table.
     */
    public void save(Transaction transaction) {
        String sql = "INSERT INTO transactions " +
                     "(account_id, type, amount, balance_after, " +
                     "reference_number, remarks) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transaction.getAccountId());
            stmt.setString(2, transaction.getType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setDouble(4, transaction.getBalanceAfter());
            stmt.setString(5, transaction.getReferenceNumber());
            stmt.setString(6, transaction.getRemarks());

            stmt.executeUpdate();
            System.out.println("[DB] Transaction saved: "
                    + transaction.getType()
                    + " | Amount: " + transaction.getAmount());

        } catch (SQLException e) {
            throw new RuntimeException(
                    "[ERROR] Failed to save transaction: "
                    + e.getMessage());
        }
    }

    /**
     * Finds all transactions for a specific account.
     */
    public List<Transaction> findByAccountId(int accountId) {
        String sql = "SELECT * FROM transactions " +
                     "WHERE account_id = ? " +
                     "ORDER BY created_at DESC";

        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapRowToTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "[ERROR] Failed to fetch transactions: "
                    + e.getMessage());
        }
        return transactions;
    }

    /**
     * Finds all transactions across all accounts.
     */
    public List<Transaction> findAll() {
        String sql = "SELECT * FROM transactions ORDER BY created_at DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                transactions.add(mapRowToTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "[ERROR] Failed to fetch transactions: "
                    + e.getMessage());
        }
        return transactions;
    }

    /**
     * Maps database row to Transaction object.
     */
    private Transaction mapRowToTransaction(ResultSet rs)
            throws SQLException {
        return new Transaction.Builder(
                rs.getInt("account_id"),
                rs.getString("type"),
                rs.getDouble("amount"),
                rs.getDouble("balance_after"))
                .referenceNumber(rs.getString("reference_number"))
                .remarks(rs.getString("remarks"))
                .build();
    }
}