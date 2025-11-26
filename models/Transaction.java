package models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String transactionId;
    private String accountNumber;
    private double amount;
    private String type;
    private LocalDateTime dateTime;

    private SimpleStringProperty dateProperty;
    private SimpleStringProperty typeProperty;
    private SimpleDoubleProperty amountProperty;
    private SimpleStringProperty accountNumberProperty;

    // Constructor for NEW transaction (system-generated timestamp)
    public Transaction(String transactionId, String accountNumber, double amount, String type) {
        this(transactionId, accountNumber, amount, type,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    // Constructor for DB-loaded transactions
    public Transaction(String transactionId, String accountNumber, double amount, String type, String timestamp) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.type = type;

        this.dateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        this.dateProperty = new SimpleStringProperty(timestamp);
        this.typeProperty = new SimpleStringProperty(type);
        this.amountProperty = new SimpleDoubleProperty(amount);
        this.accountNumberProperty = new SimpleStringProperty(accountNumber);
    }

    public String getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public double getAmount() { return amount; }
    public String getType() { return type; }

    public String getDateTime() {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public SimpleStringProperty dateProperty() { return dateProperty; }
    public SimpleStringProperty typeProperty() { return typeProperty; }
    public SimpleDoubleProperty amountProperty() { return amountProperty; }
    public SimpleStringProperty accountNumberProperty() { return accountNumberProperty; }
}
