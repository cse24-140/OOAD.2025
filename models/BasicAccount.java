package models;

public class BasicAccount extends Account {

    public BasicAccount(String accountNumber, double balance, String customerId, String accountType) {
        super(accountNumber, balance, customerId, accountType);
    }

    @Override
    public void deposit(double amount) {
        this.balance += amount;
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance < amount) return false;
        this.balance -= amount;
        return true;
    }

    @Override
    public void applyMonthlyInterest() {
        // No interest by default
    }
}
