package models;

import java.sql.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankingSystem {

    private static BankingSystem instance;
    private Connection conn;

    private Customer loggedInCustomer;

    // ============================================================
    // SINGLETON
    // ============================================================
    private BankingSystem() {
        connectDatabase();
        createTablesIfNotExist();
    }

    public static BankingSystem getInstance() {
        if (instance == null) instance = new BankingSystem();
        return instance;
    }

    // ============================================================
    // CONNECT TO SQLITE
    // ============================================================
    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:C:/Users/sbb/Documents/Solace.2025/src/database/bank.db"
            );
            System.out.println("[SQLite] Connected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // CREATE TABLES
    // ============================================================
    private void createTablesIfNotExist() {
        try (Statement st = conn.createStatement()) {

            st.execute("""
                CREATE TABLE IF NOT EXISTS Customers(
                    customerId TEXT PRIMARY KEY,
                    type TEXT,
                    fname TEXT,
                    lname TEXT,
                    nationalId TEXT,
                    gender TEXT,
                    companyName TEXT,
                    companyReg TEXT,
                    contactFirstName TEXT,
                    contactLastName TEXT,
                    position TEXT,
                    taxNumber TEXT,
                    phone TEXT,
                    address TEXT,
                    email TEXT UNIQUE,
                    username TEXT UNIQUE,
                    password TEXT
                );
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS Accounts(
                    accountNumber TEXT PRIMARY KEY,
                    customerId TEXT,
                    type TEXT,
                    balance REAL
                );
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS Transactions(
                    transactionId TEXT PRIMARY KEY,
                    accountNumber TEXT,
                    amount REAL,
                    type TEXT,
                    timestamp TEXT
                );
            """);

            System.out.println("[SQLite] Tables Ready.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // LOGGED IN CUSTOMER
    // ============================================================
    public void setLoggedInCustomer(Customer c) { this.loggedInCustomer = c; }
    public Customer getLoggedInCustomer() { return loggedInCustomer; }

    // ============================================================
    // CREATE INDIVIDUAL CUSTOMER
    // ============================================================
    public boolean createIndividualCustomer(IndividualCustomer c) {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO Customers(customerId, type, fname, lname, nationalId, gender,
                                      phone, address, email, username, password)
                VALUES (?, 'individual', ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """);

            ps.setString(1, c.getCustomerId());
            ps.setString(2, c.getFirstName());
            ps.setString(3, c.getLastName());
            ps.setString(4, c.getNationalId());
            ps.setString(5, c.getGender());
            ps.setString(6, c.getPhone());
            ps.setString(7, c.getAddress());
            ps.setString(8, c.getEmail());
            ps.setString(9, c.getUsername());
            ps.setString(10, c.getPassword());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("ERROR saving individual:");
            e.printStackTrace();
            return false;
        }
    }

    // ============================================================
    // CREATE COMPANY CUSTOMER
    // ============================================================
    public boolean createCompanyCustomer(CompanyCustomer c) {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO Customers(customerId, type, companyName, companyReg,
                    contactFirstName, contactLastName, phone, address,
                    email, username, password, position, taxNumber)
                VALUES (?, 'company', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """);

            ps.setString(1, c.getCustomerId());
            ps.setString(2, c.getCompanyName());
            ps.setString(3, c.getCompanyReg());
            ps.setString(4, c.getContactFirstName());
            ps.setString(5, c.getContactLastName());
            ps.setString(6, c.getPhone());
            ps.setString(7, c.getAddress());
            ps.setString(8, c.getEmail());
            ps.setString(9, c.getUsername());
            ps.setString(10, c.getPassword());
            ps.setString(11, c.getPosition());
            ps.setString(12, c.getTaxNumber());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("ERROR saving company:");
            e.printStackTrace();
            return false;
        }
    }

    // ============================================================
    // GENERIC createCustomer (Customer)
    // ============================================================
    public boolean createCustomer(Customer customer) {
        if (customer instanceof IndividualCustomer) {
            return createIndividualCustomer((IndividualCustomer) customer);
        } else if (customer instanceof CompanyCustomer) {
            return createCompanyCustomer((CompanyCustomer) customer);
        }
        return false;
    }

    // ============================================================
    // *** FIXED: Method your Controller is calling ***
    // ============================================================
    public boolean createCustomer(IndividualCustomer customer) {
        return createIndividualCustomer(customer);
    }

    // ============================================================
    // LOGIN
    // ============================================================
    public Customer login(String username, String password) {

        try {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT * FROM Customers 
                WHERE (email=? OR username=?) AND password=?
            """);

            ps.setString(1, username);
            ps.setString(2, username);
            ps.setString(3, password);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            Customer customer;

            if (rs.getString("type").equals("individual")) {
                customer = new IndividualCustomer(
                        rs.getString("customerId"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("nationalId"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("username")
                );
            } else {
                customer = new CompanyCustomer(
                        rs.getString("customerId"),
                        rs.getString("companyName"),
                        rs.getString("companyReg"),
                        rs.getString("contactFirstName"),
                        rs.getString("contactLastName"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("position"),
                        rs.getString("taxNumber"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("username")
                );
            }

            refreshCustomerData(customer);
            return customer;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ============================================================
    // REFRESH CUSTOMER DATA
    // ============================================================
    public void refreshCustomerData(Customer c) {
        c.clearAccounts();
        c.clearTransactions();
        loadAccounts(c);
        loadTransactions(c);
    }

    // ============================================================
    // LOAD ACCOUNTS
    // ============================================================
    private void loadAccounts(Customer c) {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT * FROM Accounts WHERE customerId=?
            """);

            ps.setString(1, c.getCustomerId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account acc = new BasicAccount(
                        rs.getString("accountNumber"),
                        rs.getDouble("balance"),
                        c.getCustomerId(),
                        rs.getString("type")
                );
                c.addAccount(acc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // LOAD TRANSACTIONS
    // ============================================================
    private void loadTransactions(Customer c) {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                SELECT * FROM Transactions
                WHERE accountNumber IN 
                (SELECT accountNumber FROM Accounts WHERE customerId=?)
            """);

            ps.setString(1, c.getCustomerId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction(
                        rs.getString("transactionId"),
                        rs.getString("accountNumber"),
                        rs.getDouble("amount"),
                        rs.getString("type")
                );
                c.addTransaction(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // CREATE ACCOUNT
    // ============================================================
    public String createAccount(String customerId, String type, double initialBalance) {
        try {
            String accNo = "ACC" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO Accounts(accountNumber, customerId, type, balance)
                VALUES (?, ?, ?, ?)
            """);

            ps.setString(1, accNo);
            ps.setString(2, customerId);
            ps.setString(3, type);
            ps.setDouble(4, initialBalance);
            ps.executeUpdate();

            return accNo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ============================================================
    // DEPOSIT
    // ============================================================
    public boolean deposit(String accountNumber, double amount) {
        try {
            PreparedStatement ps = conn.prepareStatement("""
                UPDATE Accounts SET balance = balance + ? 
                WHERE accountNumber=?
            """);

            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);

            if (ps.executeUpdate() > 0) {
                recordTransaction(accountNumber, amount, "Deposit");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ============================================================
    // WITHDRAW
    // ============================================================
    public boolean withdraw(String accountNumber, double amount) {
        try {
            PreparedStatement check = conn.prepareStatement("""
                SELECT balance FROM Accounts WHERE accountNumber=?
            """);
            check.setString(1, accountNumber);
            ResultSet rs = check.executeQuery();

            if (!rs.next() || rs.getDouble("balance") < amount)
                return false;

            PreparedStatement ps = conn.prepareStatement("""
                UPDATE Accounts SET balance = balance - ?
                WHERE accountNumber=?
            """);

            ps.setDouble(1, amount);
            ps.setString(2, accountNumber);

            if (ps.executeUpdate() > 0) {
                recordTransaction(accountNumber, amount, "Withdrawal");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ============================================================
    // RECORD TRANSACTION
    // ============================================================
    public void recordTransaction(String acc, double amt, String type) {
        try {
            String id = "TX" + UUID.randomUUID().toString().substring(0, 6);

            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO Transactions(transactionId, accountNumber, amount, type, timestamp)
                VALUES (?, ?, ?, ?, ?)
            """);

            ps.setString(1, id);
            ps.setString(2, acc);
            ps.setDouble(3, amt);
            ps.setString(4, type);
            ps.setString(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // PROCESS MONTHLY INTEREST
    // ============================================================
    public void processMonthlyInterest() {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT accountNumber, balance, type FROM Accounts"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String accNo = rs.getString("accountNumber");
                double balance = rs.getDouble("balance");
                String type = rs.getString("type");

                double interestRate = 0.00;

                switch (type.toLowerCase()) {
                    case "savings":
                        interestRate = 0.05;
                        break;
                    case "fixed":
                        interestRate = 0.10;
                        break;
                    case "current":
                        interestRate = 0.01;
                        break;
                }

                double newBalance = balance + (balance * interestRate);

                PreparedStatement update = conn.prepareStatement(
                        "UPDATE Accounts SET balance=? WHERE accountNumber=?"
                );
                update.setDouble(1, newBalance);
                update.setString(2, accNo);
                update.executeUpdate();
            }

            System.out.println("[INTEREST] Monthly interest applied.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
