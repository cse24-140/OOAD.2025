public abstract class Account {
    // Attributes
    protected String accountNumber;
    protected double balance;
    protected String customerId;
    protected Customer customer; // corrected naming

    // Constructor
    public Account(String accountNumber, double balance, String customerId, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customerId = customerId;
        this.customer = customer;
    }

    // Alternative constructor (if you don’t need Customer object)
    public Account(String accountNumber, double balance, String customerId) {
        this(accountNumber, balance, customerId, null);
    }

    // Getter methods
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getAvailableBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber +
                "\nCustomer ID: " + customerId +
                "\nBalance: BWP " + balance;
    }

    public abstract void deposit(double amount);
}
