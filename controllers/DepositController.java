package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;

public class DepositController {

    @FXML private ComboBox<String> cmbAccountType;
    @FXML private TextField txtAmountField;
    @FXML private Button btnConfirmDeposit;
    @FXML private Button btnBack;

    private BankingSystem bankingSystem;
    private Customer customer;

    public void setCustomer(Customer c) {
        this.customer = c;
        loadAccounts();
    }

    public void setBankingSystem(BankingSystem bs) {
        this.bankingSystem = bs;
    }

    @FXML
    private void initialize() { }

    private void loadAccounts() {
        cmbAccountType.getItems().clear();
        for (Account a : customer.getAccounts()) {
            cmbAccountType.getItems().add(a.getAccountNumber());
        }
    }

    @FXML
    private void handleConfirmDeposit() {
        String acc = cmbAccountType.getValue();
        String amt = txtAmountField.getText();

        if (acc == null) { show("Error", "Select account."); return; }
        if (amt.isEmpty()) { show("Error", "Enter amount."); return; }

        try {
            double amount = Double.parseDouble(amt);
            if (amount <= 0) { show("Error", "Amount must be positive."); return; }

            boolean ok = bankingSystem.deposit(acc, amount);

            if (!ok) { show("Error", "Deposit failed."); return; }

            show("Success", "Deposit successful.");
            txtAmountField.clear();

        } catch (Exception e) {
            show("Error", "Invalid number.");
        }
    }

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
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}
