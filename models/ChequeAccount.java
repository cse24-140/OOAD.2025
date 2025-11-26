package models;


public class ChequeAccount extends Account {
    private String employer;
    private String employerAddress;

    public ChequeAccount(String accountNumber, double balance, String customerId,
                         String employer, String employerAddress) {
        super(accountNumber, balance, customerId, "Cheque");
        this.employer = employer;
        this.employerAddress = employerAddress;
    }

    public ChequeAccount(String accountNumber, double balance, String customerId,
                         Customer customer, String employer, String employerAddress) {
        super(accountNumber, balance, customerId, customer, "Cheque");
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
        // Cheque accounts can go into overdraft (negative balance)
        if (amount > 0) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public void applyMonthlyInterest() {
        // Cheque accounts usually don't earn interest
    }

    public String getEmployer() {
        return employer;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }
}