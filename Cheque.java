public class Cheque {
    import java.util.Date;

public class Cheque extends Account implements Deposit, Withdrawal {
    private String chequeNumber;
    private Date issueDate;
    private double amount;
    private String status;

    public Cheque(String accountNumber, double balance, String customer,
                  String chequeNumber, Date issueDate, double amount, String status) {
        super(accountNumber, balance, customer);
        this.chequeNumber = chequeNumber;
        this.issueDate = issueDate;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds!");
        }
    }
}

}
