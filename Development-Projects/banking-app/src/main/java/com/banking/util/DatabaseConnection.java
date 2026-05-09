package com.banking.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnection {

    private static Connection connection;
    private static String url;
    private static String username;
    private static String password;

    static {
        loadProperties();
    }

    private DatabaseConnection() { }

   
    private static void loadProperties() {
    Properties props = new Properties();

    try (InputStream input = DatabaseConnection.class
            .getClassLoader()
            .getResourceAsStream("db.properties")) {

        if (input == null) {
            throw new RuntimeException(
                "[ERROR] db.properties not found. " +
                "Copy db.properties.example and fill credentials.");
        }

        props.load(input);

        
        url      = props.getProperty("db.url");
        username = props.getProperty("db.username");

    
        password = EncryptionUtil.decrypt(props.getProperty("db.password"));

        System.out.println("[DB] Credentials loaded successfully.");

    } catch (IOException e) {
        throw new RuntimeException(
                "[ERROR] Failed to load db.properties: "
                + e.getMessage());
    }
}

    public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection(url, username, password);
        System.out.println("[DB] Connection established successfully.");
    }
    return connection; 
}
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("[DB] Connection closed.");
            } catch (SQLException e) {
                System.out.println("[ERROR] Failed to close connection: "
                        + e.getMessage());
            }
        }
    }
}
