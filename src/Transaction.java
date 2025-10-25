import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String transactionId;
    private String accountNumber;
    private double amount;
    private String type; 
    private LocalDateTime dateTime;

    
    public Transaction(String transactionId, String accountNumber, double amount, String type) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.type = type;
        this.dateTime = LocalDateTime.now(); 
    }


    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    
    @Override
    public String toString() {
        return "Transaction ID: " + transactionId +
               "\nAccount Number: " + accountNumber +
               "\nType: " + type +
               "\nAmount: " + amount +
               "\nDate & Time: " + getDateTime();
    }

    
    public static void main(String[] args) {
        Transaction t1 = new Transaction("T001", "AC001", 500.00, "Deposit");
        System.out.println(t1.toString());
    }
}
