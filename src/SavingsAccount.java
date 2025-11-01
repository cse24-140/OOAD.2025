public class SavingsAccount extends Account implements InterestBearing {
    private static final double MONTHLY_INTEREST_RATE = 0.0005; // 0.05% monthly

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
        // Savings account does not allow withdrawals as per assignment
        return false;
    }

    @Override
    public void applyMonthlyInterest() {
        double interest = balance * MONTHLY_INTEREST_RATE;
        balance += interest;
    }

    @Override
    public double getMonthlyInterestRate() {
        return 0;
    }
}