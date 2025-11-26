package models;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer {

    private String customerId;
    private String phone;
    private String address;
    private String email;
    private String password;

    private List<Account> accounts;
    private List<Transaction> transactions;

    public Customer(String customerId, String phone, String address, String email, String password) {
        this.customerId = customerId;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public Customer(String customerId, String phone, String address) {
        this(customerId, phone, address, "", "");
    }

    public String getCustomerId() { return customerId; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public void addAccount(Account acc) {
        accounts.add(acc);
    }

    public double getTotalBalance() {
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }

    public void applyMonthlyInterestToAllAccounts() {
        for (Account acc : accounts) acc.applyMonthlyInterest();
    }

    // FIXED — No invalid DB calls
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public abstract String getName();
    public abstract String getCustomerType();
    
    
    public void clearAccounts() {
    accounts.clear();
}

public void clearTransactions() {
    transactions.clear();
}

}


