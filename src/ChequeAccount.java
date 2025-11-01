public class ChequeAccount extends Account implements Withdrawal {
    private String employer;
    private String employerAddress;

    public ChequeAccount(String accountNumber, double balance, String customerId,
                         String employer, String employerAddress) {
        super(accountNumber, balance, customerId, "Cheque");
        this.employer = employer;
        this.employerAddress = employerAddress;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
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
        return 0;
    }

    @Override
    public void applyMonthlyInterest() {
        // Cheque accounts don't earn interest
    }

    // Getters
    public String getEmployer() { return employer; }
    public String getEmployerAddress() { return employerAddress; }
}