package models;

import database.DatabaseConnector;
import java.sql.Connection;

public abstract class Account {

    protected String accountNumber;
    protected double balance;
    protected String customerId;
    protected Customer customer;
    protected String accountType;

    // Database connection
    protected Connection conn;

    // Constructor
    public Account(String accountNumber, double balance, String customerId, Customer customer, String accountType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customerId = customerId;
        this.customer = customer;
        this.accountType = accountType;

        // FIXED: correct DB access
        conn = DatabaseConnector.getConnection();
    }

    // Alternative constructor
    public Account(String accountNumber, double balance, String customerId, String accountType) {
        this(accountNumber, balance, customerId, null, accountType);
    }

    // For backward compatibility
    public Account(String accountNumber, double balance, String customerId, Customer customer) {
        this(accountNumber, balance, customerId, customer, "Generic");
    }

    public Account(String accountNumber, double balance, String customerId) {
        this(accountNumber, balance, customerId, null, "Generic");
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getCustomerId() { return customerId; }
    public String getAccountType() { return accountType; }

    public double getAvailableBalance() { return balance; }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber +
                "\nCustomer ID: " + customerId +
                "\nAccount Type: " + accountType +
                "\nBalance: BWP " + balance;
    }

    public abstract void deposit(double amount);
    public abstract boolean withdraw(double amount);
    public abstract void applyMonthlyInterest();
}
