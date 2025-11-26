package database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            Statement st = conn.createStatement();

            // === CUSTOMERS TABLE ===
            st.execute("""
                CREATE TABLE IF NOT EXISTS Customers (
                    customerId TEXT PRIMARY KEY,
                    type TEXT,
                    
                    -- INDIVIDUAL FIELDS
                    fname TEXT,
                    lname TEXT,
                    nationalId TEXT,

                    -- BUSINESS FIELDS
                    companyName TEXT,
                    companyReg TEXT,
                    contactFirst TEXT,
                    contactLast TEXT,
                    position TEXT,
                    taxNumber TEXT,

                    -- SHARED FIELDS
                    phone TEXT,
                    address TEXT,
                    email TEXT UNIQUE,
                    username TEXT,
                    password TEXT
                );
            """);

            // === ACCOUNTS TABLE ===
            st.execute("""
                CREATE TABLE IF NOT EXISTS Accounts (
                    accountNumber TEXT PRIMARY KEY,
                    customerId TEXT,
                    type TEXT,
                    balance REAL,
                    interestRate REAL,
                    employer TEXT,
                    employerAddress TEXT
                );
            """);

            // === TRANSACTIONS TABLE ===
            st.execute("""
                CREATE TABLE IF NOT EXISTS Transactions (
                    transactionId TEXT PRIMARY KEY,
                    accountNumber TEXT,
                    customerId TEXT,
                    type TEXT,
                    amount REAL,
                    timestamp TEXT
                );
            """);

            System.out.println("Database initialized!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
