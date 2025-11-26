package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    // Correctly declare the database URL as a string
    private static final String DB_PATH = "jdbc:sqlite:C:/Users/sbb/Documents/Solace.2025/src/database/bank.db";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_PATH);
                System.out.println("Connected to SQLite database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to SQLite.");
        }

        return connection;
    }
}
