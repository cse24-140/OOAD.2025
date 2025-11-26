package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;

// ====== REQUIRED MODEL IMPORTS ======
import models.BankingSystem;
import models.Customer;
import models.Account;
import models.IndividualCustomer;
import models.CompanyCustomer;

public class ViewAccountsController {

    @FXML private Button btnCashWithdrawl;
    @FXML private Button btnInvestment;
    @FXML private Button btnDeposit;
    @FXML private Button btnBack;
    @FXML private TextArea txtViewAccounts;

    private BankingSystem bankingSystem;
    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        displayAccountInformation();
    }

    public void setBankingSystem(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
    }

    @FXML
    public void initialize() {
        displayAccountInformation();
    }

    private void displayAccountInformation() {
        if (customer == null || bankingSystem == null) {
            txtViewAccounts.setText("No customer data available.");
            return;
        }

        StringBuilder accountInfo = new StringBuilder();
        accountInfo.append("=== ACCOUNT SUMMARY ===\n\n");

        if (customer instanceof IndividualCustomer ind) {
            accountInfo.append(String.format("Account Holder: %s %s\n",
                    ind.getFirstName(), ind.getLastName()));
        } else if (customer instanceof CompanyCustomer comp) {
            accountInfo.append(String.format("Company: %s\n", comp.getCompanyName()));
        }

        accountInfo.append(String.format("Customer ID: %s\n", customer.getCustomerId()));
        accountInfo.append(String.format("Total Balance: BWP %.2f\n\n",
                bankingSystem.getCustomerTotalBalance(customer.getCustomerId())));

        accountInfo.append("ACCOUNT DETAILS:\n");
        accountInfo.append("================\n");

        if (customer.getAccounts().isEmpty()) {
            accountInfo.append("No accounts found.\n");
        } else {
            for (Account account : customer.getAccounts()) {
                accountInfo.append(String.format("• %s: %s - BWP %.2f\n",
                        account.getAccountType(), account.getAccountNumber(), account.getBalance()));
            }
        }

        accountInfo.append("\nRECENT ACTIVITY:\n");
        accountInfo.append("================\n");

        if (!customer.getAccounts().isEmpty()) {
            accountInfo.append("• Last login: Today\n");
            accountInfo.append("• Accounts: " + customer.getAccounts().size() + " active\n");
            accountInfo.append("• Status: All accounts in good standing\n");
        } else {
            accountInfo.append("• No recent activity\n");
        }

        txtViewAccounts.setText(accountInfo.toString());
    }

    @FXML
    private void handleCashWithdrawl() {
        loadScene("/Withdraw.fxml", "Cash Withdrawal - Orange Bank of Botswana", "CashWithdrawlController");
    }

    @FXML
    private void handleInvestment() {
        loadScene("/Investment.fxml", "Investment Options - Orange Bank of Botswana", "InvestmentController");
    }

    @FXML
    private void handleDeposit() {
        loadScene("/Deposit.fxml", "Deposit - Orange Bank of Botswana", "DepositController");
    }

    @FXML
    private void handleBack() {
        loadScene("/Dashboard.fxml", "Dashboard - Orange Bank of Botswana", "DashboardController");
    }

    private void loadScene(String fxml, String title, String controllerName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Object controller = loader.getController();
            setControllerData(controller, controllerName);

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);

        } catch (IOException e) {
            showAlert("Navigation Error", "Unable to load screen: " + e.getMessage());
        }
    }

    private void setControllerData(Object controller, String controllerName) {
        if (controller == null) return;

        try {
            var m = controller.getClass().getMethod("setCustomer", Customer.class);
            m.invoke(controller, customer);
        } catch (Exception ignored) {}

        try {
            var m2 = controller.getClass().getMethod("setBankingSystem", BankingSystem.class);
            m2.invoke(controller, bankingSystem);
        } catch (Exception ignored) {}
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
