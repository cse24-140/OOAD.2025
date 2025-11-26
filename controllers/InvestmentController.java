package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import models.Customer;
import models.BankingSystem;
import models.Transaction;
import models.InvestmentAccount;

public class InvestmentController {

    @FXML private Button btnCalculateReturn;
    @FXML private Button btnInvest;
    @FXML private Button btnBack;

    private Customer customer;
    private BankingSystem bankingSystem;

    // ------------------------------
    // SETTERS (Dashboard passes these)
    // ------------------------------
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setBankingSystem(BankingSystem system) {
        this.bankingSystem = system;
    }

    // ------------------------------
    //  CALCULATE RETURN BUTTON
    // ------------------------------
    @FXML
    private void handleCalculateReturn() {
        if (customer == null) {
            showAlert("Error", "Customer not loaded.");
            return;
        }

        double totalInvestment = customer.getAccounts().stream()
                .filter(a -> a instanceof InvestmentAccount)
                .mapToDouble(a -> a.getBalance())
                .sum();

        double totalReturn = totalInvestment * 0.12;  // 12% sample return

        showAlert("Return Projection",
                "Your total investment: BWP " + totalInvestment +
                        "\nEstimated Annual Return (12%): BWP " + totalReturn);
    }

    // ------------------------------
    //  INVEST BUTTON
    // ------------------------------
    @FXML
    private void handleInvest() {
        if (customer == null || bankingSystem == null) {
            showAlert("Error", "System error. Try again.");
            return;
        }

        // For now, deposit a fixed BWP500 into new Investment Account
        double amount = 500;
        String newAcc = bankingSystem.createAccount(
                customer.getCustomerId(),
                "investment",
                amount
        );

        if (newAcc == null) {
            showAlert("Error", "Failed to open investment account.");
            return;
        }

        // add transaction
        customer.addTransaction(new Transaction(
                "TR" + System.currentTimeMillis(),
                newAcc,
                amount,
                "Investment"
        ));

        showAlert("Success",
                "Investment Account Opened!\n" +
                        "Account: " + newAcc +
                        "\nAmount: BWP " + amount);
    }

    // ------------------------------
    //  BACK BUTTON
    // ------------------------------
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard - Orange Bank of Botswana");
            stage.show();

        } catch (Exception e) {
            showAlert("Error", "Unable to load Dashboard.\n" + e.getMessage());
        }
    }

    // ------------------------------
    //  ALERT HELPER
    // ------------------------------
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
