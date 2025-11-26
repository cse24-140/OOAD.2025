package models;


public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.02;

    public SavingsAccount(String accountNumber, double balance, String customerId, Customer customer) {
        super(accountNumber, balance, customerId, customer, "Savings");
    }

    public SavingsAccount(String accountNumber, double balance, String customerId) {
        super(accountNumber, balance, customerId, "Savings");
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public void applyMonthlyInterest() {
        double monthlyInterest = balance * (INTEREST_RATE / 12);
        balance += monthlyInterest;
    }
}