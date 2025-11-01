public class InvestmentAccount extends Account implements InterestBearing, Withdrawal {
    private static final double MONTHLY_INTEREST_RATE = 0.05;
    private static final double MIN_OPENING_BALANCE = 500.00;

    public InvestmentAccount(String accountNumber, double balance, String customerId) {
        super(accountNumber, balance, customerId, "Investment");
        if (balance < MIN_OPENING_BALANCE) {
            throw new IllegalArgumentException("Investment account requires minimum BWP " + MIN_OPENING_BALANCE);
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public double getWithdrawalLimit() {
        return -1; // No specific limit
    }

    @Override
    public void applyMonthlyInterest() {
        balance += balance * MONTHLY_INTEREST_RATE;
    }

    @Override
    public double getMonthlyInterestRate() {
        return MONTHLY_INTEREST_RATE;
    }

    // Keep this static method for external access
    public static double getMinOpeningBalance() {
        return MIN_OPENING_BALANCE;
    }
}