package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import models.BankingSystem;
import models.Customer;

public class DashboardController {

    @FXML private Label lblCustomerInfo;
    @FXML private Label lblWelcome;
    @FXML private Label lblTotalBalance;
    @FXML private Label lblAccountCount;
    @FXML private Button btnLogOut;

    private Customer customer;
    private BankingSystem bankingSystem;

    // =======================================================
    // SET BANKING SYSTEM FIRST (IMPORTANT)
    // =======================================================
    public void setBankingSystem(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;

        // If customer already set, refresh now
        if (customer != null) {
            refreshFromDB();
            updateUI();
        }
    }

    // =======================================================
    // SET CUSTOMER SECOND
    // =======================================================
    public void setCustomer(Customer customer) {
        this.customer = customer;

        // Only refresh if bankingSystem is ready
        if (bankingSystem != null) {
            refreshFromDB();
        }

        updateUI();
    }

    // =======================================================
    // REFRESH DATA SAFELY
    // =======================================================
    private void refreshFromDB() {
        if (bankingSystem == null || customer == null) return;
        bankingSystem.refreshCustomerData(customer);
    }

    // =======================================================
    // UPDATE UI
    // =======================================================
    private void updateUI() {
        if (customer == null) return;

        lblCustomerInfo.setText("Logged in as: " + customer.getName());
        lblWelcome.setText("Welcome, " + customer.getName() + "!");

        double totalBalance = customer.getTotalBalance();
        lblTotalBalance.setText("Total Balance: BWP " + String.format("%.2f", totalBalance));

        lblAccountCount.setText("Accounts: " + customer.getAccounts().size());
    }

    // =======================================================
    // NAVIGATION LOADER (PASS CUSTOMER + BANKINGSYSTEM)
    // =======================================================
    private void loadScene(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/" + fxml));
            Parent root = loader.load();

            Object controller = loader.getController();

            // pass customer
            try {
                controller.getClass()
                        .getMethod("setCustomer", Customer.class)
                        .invoke(controller, customer);
            } catch (Exception ignored) {}

            // pass bankingSystem
            try {
                controller.getClass()
                        .getMethod("setBankingSystem", BankingSystem.class)
                        .invoke(controller, bankingSystem);
            } catch (Exception ignored) {}

            Stage stage = (Stage) btnLogOut.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load: " + fxml);
        }
    }

    // =======================================================
    // BUTTON ACTIONS
    // =======================================================
    @FXML private void handleDeposit()  { loadScene("Deposit.fxml", "Deposit"); }
    @FXML private void handleWithdraw() { loadScene("CashWithdrawal.fxml", "Withdraw Cash"); }

    @FXML private void handleOpenAccount() {
        loadScene("OpenAccount.fxml", "Open New Account");
    }

    @FXML private void handleManageAccounts() {
        loadScene("ManageAccount.fxml", "Manage Accounts");
    }

    @FXML private void handleTransactionHistory() {
        loadScene("TransactionHistory.fxml", "Transaction History");
    }

    @FXML private void handleInvestment() {
        loadScene("Investment.fxml", "Investment Options");
    }

    @FXML
    private void handleApplyInterest() {
        if (bankingSystem != null) {
            bankingSystem.processMonthlyInterest();
            refreshFromDB();
            updateUI();
            showAlert("Success", "Interest applied successfully.");
        }
    }

    @FXML
    private void handleViewBalance() {
        refreshFromDB();
        double totalBalance = customer.getTotalBalance();
        showAlert("Balance",
                "Your total balance is BWP " + String.format("%.2f", totalBalance));
    }

    @FXML
    private void handleLogOut() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/Login.fxml"));
            Stage stage = (Stage) btnLogOut.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "Could not load login.");
        }
    }

    // =======================================================
    // ALERT METHOD
    // =======================================================
    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
