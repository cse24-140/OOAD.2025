public class SavingsAccount extends Account implements PayInterest {

    private static final double INTEREST_RATE = 0.0025; // 0.25% monthly interest

    // Constructor
    public SavingsAccount(String accountNumber, double balance, String customerId, Customer customer) {
        super(accountNumber, balance, customerId, customer);
    }

    // Implement deposit method from Deposit interface
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
        // No System.out.println() — controller will handle UI messages
    }

    // Method to apply monthly interest
    public void applyInterest() {
        double interest = balance * INTEREST_RATE;
        balance += interest;
        // No print — controller can fetch balance and display on GUI
    }

    @Override
    public void payInterest() {

    }
}
