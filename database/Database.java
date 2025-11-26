package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/sbb/Documents/Solace.2025/src/database/bank.db";
    private static Connection connection;

    // -------------------------
    // GET CONNECTION (Singleton)
    // -------------------------
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Connected to SQLite database.");
                initializeTables();
            } catch (SQLException e) {
                System.out.println("Database connection failed: " + e.getMessage());
            }
        }
        return connection;
    }

    // -------------------------
    // Create Tables if Missing
    // -------------------------
    private static void initializeTables() {
        try (Statement stmt = getConnection().createStatement()) {

            // Customer Table
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS customer (
                        customerId TEXT PRIMARY KEY,
                        name TEXT,
                        phone TEXT,
                        email TEXT,
                        address TEXT,
                        password TEXT
                    );
            """);

            // Account Table
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS account (
                        accountNumber TEXT PRIMARY KEY,
                        customerId TEXT,
                        accountType TEXT,
                        balance REAL,
                        FOREIGN KEY (customerId) REFERENCES customer(customerId)
                    );
            """);

            // Transaction Table
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS transaction (
                        id TEXT PRIMARY KEY,
                        accountNumber TEXT,
                        amount REAL,
                        type TEXT,
                        dateTime TEXT,
                        FOREIGN KEY (accountNumber) REFERENCES account(accountNumber)
                    );
            """);

            System.out.println("Database tables ready.");

        } catch (SQLException e) {
            System.out.println("Failed creating tables: " + e.getMessage());
        }
    }
}
