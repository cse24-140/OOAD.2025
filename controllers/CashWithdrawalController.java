package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;

public class CashWithdrawalController {

    @FXML private ComboBox<String> cmbAccount;
    @FXML private TextField txtAmountField;
    @FXML private Button btnConfirm;
    @FXML private Button btnBack;

    private Customer customer;
    private BankingSystem bankingSystem;

    public void setCustomer(Customer c) {
        this.customer = c;
        loadAccounts();
    }

    public void setBankingSystem(BankingSystem b) {
        this.bankingSystem = b;
    }

    // ==================================================
    // LOAD USER ACCOUNTS INTO DROPDOWN
    // ==================================================
    private void loadAccounts() {
        cmbAccount.getItems().clear();

        for (Account a : customer.getAccounts()) {
            cmbAccount.getItems().add(a.getAccountNumber() + " (" + a.getAccountType() + ")");
        }
    }

    // ==================================================
    // HANDLE WITHDRAWAL
    // ==================================================
    @FXML
    private void handleConfirm() {
        String selected = cmbAccount.getValue();
        String amt = txtAmountField.getText();

        if (selected == null) {
            show("Error", "Please select an account.");
            return;
        }

        if (amt.isEmpty()) {
            show("Error", "Please enter an amount.");
            return;
        }

        try {
            double amount = Double.parseDouble(amt);
            if (amount <= 0) {
                show("Error", "Amount must be greater than zero.");
                return;
            }

            // Extract account number only
            String accNumber = selected.split(" ")[0];

            // Get account OBJECT
            Account selectedAccount = customer.getAccounts().stream()
                    .filter(a -> a.getAccountNumber().equals(accNumber))
                    .findFirst()
                    .orElse(null);

            if (selectedAccount == null) {
                show("Error", "Account not found.");
                return;
            }

            // ==================================================
            // 🔥 RULE: Savings Account Cannot Withdraw
            // ==================================================
            if (selectedAccount.getAccountType().equalsIgnoreCase("Savings")) {
                show("Not Allowed", "Withdrawals are NOT allowed from Savings Accounts.");
                return;
            }

            // ==================================================
            // Proceed with withdrawal
            // ==================================================
            boolean ok = bankingSystem.withdraw(accNumber, amount);

            if (!ok) {
                show("Error", "Insufficient funds or withdrawal failed.");
                return;
            }

            show("Success", "Withdrawal of BWP " + amount + " successful.");
            txtAmountField.clear();

        } catch (NumberFormatException e) {
            show("Error", "Invalid number entered.");
        }
    }

    // ==================================================
    // BACK BUTTON
    // ==================================================
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Dashboard.fxml"));
            Parent root = loader.load();

            DashboardController dc = loader.getController();
            dc.setCustomer(customer);
            dc.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            show("Error", "Failed to load dashboard.");
        }
    }

    private void show(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setTitle(t);
        a.setContentText(m);
        a.showAndWait();
    }
}
