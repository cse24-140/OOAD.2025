public class Account {
    // Attributes
    protected String accountNumber;
    protected double balance;
    protected String customerId; // Better name for clarity
    protected Customer Customer;

    // Constructor
    public Account(String accountNumber, double balance, String customerId, Customer cmt2) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customerId = customerId;
        this.cmt2 = cmt2;
    }

    // Gemtter methods
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
               "\nBalance: " + balance;
    }


    public static void main(String[] args) {
        Account acc = new Account("AC001", 1500.75, "C001");
        System.out.println(acc.toString());
        System.out.println("Available Balance: " + acc.getAvailableBalance());
    }
}
