package models;



public class InvestmentAccount extends Account {
    private static final double MINIMUM_BALANCE = 500.00;
    private static final double INTEREST_RATE = 0.035;

    public InvestmentAccount(String accountNumber, double balance, String customerId, Customer customer) {
        super(accountNumber, balance, customerId, customer, "Investment");
        if (balance < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Investment account requires minimum deposit of BWP " + MINIMUM_BALANCE);
        }
    }

    public InvestmentAccount(String accountNumber, double balance, String customerId) {
        super(accountNumber, balance, customerId, "Investment");
        if (balance < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Investment account requires minimum deposit of BWP " + MINIMUM_BALANCE);
        }
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