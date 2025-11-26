package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

// ======== REQUIRED MODEL IMPORTS ========
import models.BankingSystem;
import models.Customer;

public class OpenAccountController {

    @FXML private ComboBox<String> cmbAccountType;
    @FXML private TextField txtInitialDeposit;
    @FXML private TextField txtEmployer;
    @FXML private TextField txtEmployerAddress;
    @FXML private Button btnOpenAccount;
    @FXML private Button btnBack;
    @FXML private VBox employerSection;
    @FXML private VBox employerAddressSection;
    @FXML private Label lblMinBalance;

    private BankingSystem bankingSystem;
    private Customer customer;

    /** ---------------------------
     *  DATA RECEIVERS
     * --------------------------- */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setBankingSystem(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
    }

    /** ---------------------------
     *  INITIALIZATION
     * --------------------------- */
    @FXML
    public void initialize() {
        // account types
        cmbAccountType.getItems().addAll("Savings", "Cheque", "Investment");
        cmbAccountType.setValue("Savings");

        employerSection.setVisible(false);
        employerSection.setManaged(false);
        employerAddressSection.setVisible(false);
        employerAddressSection.setManaged(false);

        updateMinBalanceLabel();

        // when user switches account type
        cmbAccountType.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean isCheque = "Cheque".equals(newVal);

            employerSection.setVisible(isCheque);
            employerSection.setManaged(isCheque);

            employerAddressSection.setVisible(isCheque);
            employerAddressSection.setManaged(isCheque);

            updateMinBalanceLabel();
        });
    }

    /** ---------------------------
     *  UPDATE MINIMUM BALANCE LABEL
     * --------------------------- */
    private void updateMinBalanceLabel() {
        String type = cmbAccountType.getValue();
        if ("Investment".equals(type)) {
            lblMinBalance.setText("Minimum deposit: BWP 500.00");
        } else {
            lblMinBalance.setText("Minimum deposit: BWP 0.00");
        }
    }

    /** ---------------------------
     *  OPEN ACCOUNT
     * --------------------------- */
    @FXML
    private void handleOpenAccount() {
        if (customer == null || bankingSystem == null) {
            showError("System Error", "Customer data is missing.");
            return;
        }

        String type = cmbAccountType.getValue();
        String depositText = txtInitialDeposit.getText().trim();

        if (depositText.isEmpty()) {
            showError("Error", "Please enter initial deposit amount.");
            return;
        }

        double initialDeposit;
        try {
            initialDeposit = Double.parseDouble(depositText);
        } catch (Exception e) {
            showError("Error", "Invalid deposit amount.");
            return;
        }

        if (initialDeposit < 0) {
            showError("Error", "Deposit cannot be negative.");
            return;
        }

        if ("Investment".equals(type) && initialDeposit < 500.00) {
            showError("Error", "Investment account requires at least BWP 500.00");
            return;
        }

        String accountNumber;

        // Cheque account requires employer info
        if ("Cheque".equals(type)) {
            String employer = txtEmployer.getText().trim();
            String employerAddress = txtEmployerAddress.getText().trim();

            if (employer.isEmpty() || employerAddress.isEmpty()) {
                showError("Error", "Please enter employer name and address.");
                return;
            }

            accountNumber = bankingSystem.createChequeAccount(
                    customer.getCustomerId(),
                    initialDeposit,
                    employer,
                    employerAddress
            );

        } else {
            accountNumber = bankingSystem.createAccount(
                    customer.getCustomerId(),
                    type,
                    initialDeposit
            );
        }

        if (accountNumber == null) {
            showError("Error", "Failed to create account.");
            return;
        }

        // SUCCESS
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Account Created");
        success.setHeaderText("Account Successfully Opened");
        success.setContentText(
                "Account Type: " + type +
                "\nAccount Number: " + accountNumber +
                "\nInitial Deposit: BWP " + initialDeposit +
                "\nCustomer: " + customer.getName()
        );
        success.showAndWait();

        // Clear fields
        txtInitialDeposit.clear();
        txtEmployer.clear();
        txtEmployerAddress.clear();
    }

    /** ---------------------------
     *  BACK TO DASHBOARD
     * --------------------------- */
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setCustomer(customer);          // FIXED
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard - Orange Bank of Botswana");
            stage.show();

        } catch (IOException e) {
            showError("Error", "Unable to load Dashboard: " + e.getMessage());
        }
    }

    /** ---------------------------
     *  SHOW ERROR
     * --------------------------- */
    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
