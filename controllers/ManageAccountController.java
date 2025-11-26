package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;

public class ManageAccountController {

    @FXML
    private TextArea txtViewAccounts;

    @FXML
    private Button btnCashWithdrawl;

    @FXML
    private Button btnInvestment;

    @FXML
    private Button btnDeposit;

    @FXML
    private Button btnBack;

    private Customer customer;
    private BankingSystem bankingSystem;

    // ======================================================
    // RECEIVE CUSTOMER + BANKING SYSTEM FROM DASHBOARD
    // ======================================================
    public void setCustomer(Customer customer) {
        this.customer = customer;
        loadFromDB();
    }

    public void setBankingSystem(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
    }

  
    // LOAD REAL ACCOUNT DATA FROM DATABASE
    
    private void loadFromDB() {

        if (customer == null || bankingSystem == null) {
            txtViewAccounts.setText("ERROR: No user data found.");
            return;
        }

        // Refresh the customer to get latest accounts + transactions
        bankingSystem.refreshCustomerData(customer);

        StringBuilder sb = new StringBuilder();

        sb.append("ACCOUNT SUMMARY\n");
        sb.append("-------------------------\n");
        sb.append("Customer: ").append(customer.getName()).append("\n");
        sb.append("Customer ID: ").append(customer.getCustomerId()).append("\n\n");

        if (customer.getAccounts().isEmpty()) {
            sb.append("No accounts found.\n");
        } else {
            for (Account acc : customer.getAccounts()) {
                sb.append("Account Number: ").append(acc.getAccountNumber()).append("\n");
                sb.append("Type: ").append(acc.getAccountType()).append("\n");
                sb.append("Balance: BWP ").append(String.format("%.2f", acc.getBalance())).append("\n");
                sb.append("-------------------------\n");
            }
        }

        sb.append("\nRECENT TRANSACTIONS\n");
        sb.append("-------------------------\n");

        if (customer.getTransactions().isEmpty()) {
            sb.append("No transactions yet.\n");
        } else {
            for (Transaction t : customer.getTransactions()) {
                sb.append(t.getDateTime()).append(" | ");
                sb.append(t.getType()).append(" | ");
                sb.append("BWP ").append(String.format("%.2f", t.getAmount())).append(" | ");
                sb.append("Acc: ").append(t.getAccountNumber()).append("\n");
            }
        }

        txtViewAccounts.setText(sb.toString());
    }

    // ======================================================
    // BUTTON: CASH WITHDRAWAL
    // ======================================================
    @FXML
    private void handleCashWithdrawl(ActionEvent event) {
        loadSceneWithUser("CashWithdrawal.fxml", "Cash Withdrawal");
    }

    @FXML
    private void handleInvestment(ActionEvent event) {
        loadSceneWithUser("Investment.fxml", "Investment Plans");
    }

    @FXML
    private void handleDeposit(ActionEvent event) {
        loadSceneWithUser("Deposit.fxml", "Deposit Funds");
    }

    @FXML
    private void handleBack(ActionEvent event) {
        loadSceneWithUser("Dashboard.fxml", "Dashboard");
    }

    // ======================================================
    // LOAD SCENE WITH USER DATA PASSED
    // ======================================================
    private void loadSceneWithUser(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/" + fxml));
            Parent root = loader.load();

            Object controller = loader.getController();

            // Pass customer
            controller.getClass().getMethod("setCustomer", Customer.class)
                    .invoke(controller, customer);

            // Pass banking system
            controller.getClass().getMethod("setBankingSystem", BankingSystem.class)
                    .invoke(controller, bankingSystem);

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load: " + fxml);
        }
    }

    // ======================================================
    // ALERT
    // ======================================================
    private void showAlert(String title, String message) {
        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
