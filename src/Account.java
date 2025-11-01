public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String customerId;
    protected String accountType;

    public Account(String accountNumber, double balance, String customerId, String accountType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customerId = customerId;
        this.accountType = accountType;
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getCustomerId() { return customerId; }
    public String getAccountType() { return accountType; }

    // Abstract methods
    public abstract void deposit(double amount);
    public abstract boolean withdraw(double amount);
    public abstract void applyMonthlyInterest();

    @Override
    public String toString() {
        return String.format("Account: %s | Type: %s | Balance: BWP %.2f",
                accountNumber, accountType, balance);
    }
}