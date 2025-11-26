package models;

public class SimpleAccount extends Account {

    public SimpleAccount(String number, double balance, String custId, String type) {
        super(number, balance, custId, type);
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }

    @Override
    public void applyMonthlyInterest() { }
}
