import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

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

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setBankingSystem(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
    }

    @FXML
    public void initialize() {
        cmbAccountType.getItems().addAll("Savings", "Cheque", "Investment");
        cmbAccountType.setValue("Savings");
        updateMinBalanceLabel();

        // Show/hide employer fields based on account type
        cmbAccountType.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean isCheque = "Cheque".equals(newVal);
            employerSection.setVisible(isCheque);
            employerSection.setManaged(isCheque);
            employerAddressSection.setVisible(isCheque);
            employerAddressSection.setManaged(isCheque);
            updateMinBalanceLabel();
        });

        employerSection.setVisible(false);
        employerSection.setManaged(false);
        employerAddressSection.setVisible(false);
        employerAddressSection.setManaged(false);
    }

    private void updateMinBalanceLabel() {
        String accountType = cmbAccountType.getValue();
        if ("Investment".equals(accountType)) {
            lblMinBalance.setText("Minimum deposit: BWP 500.00");
        } else {
            lblMinBalance.setText("Minimum deposit: BWP 0.00");
        }
    }

    @FXML
    private void handleOpenAccount() {
        String accountType = cmbAccountType.getValue();
        String depositText = txtInitialDeposit.getText().trim();

        if (depositText.isEmpty()) {
            showAlert("Error", "Please enter initial deposit amount.");
            return;
        }

        try {
            double initialDeposit = Double.parseDouble(depositText);

            if (initialDeposit < 0) {
                showAlert("Error", "Initial deposit cannot be negative.");
                return;
            }

            // Check minimum balance for Investment accounts
            if ("Investment".equals(accountType) && initialDeposit < 500.00) {
                showAlert("Error", "Investment account requires minimum deposit of BWP 500.00");
                return;
            }

            String accountNumber;
            if ("Cheque".equals(accountType)) {
                String employer = txtEmployer.getText().trim();
                String employerAddress = txtEmployerAddress.getText().trim();

                if (employer.isEmpty() || employerAddress.isEmpty()) {
                    showAlert("Error", "Please provide employer details for cheque account.");
                    return;
                }

                accountNumber = bankingSystem.createChequeAccount(
                        customer.getCustomerId(), initialDeposit, employer, employerAddress);
            } else {
                accountNumber = bankingSystem.createAccount(customer.getCustomerId(), accountType, initialDeposit);
            }

            if (accountNumber != null) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Account Created");
                success.setHeaderText("New Account Opened Successfully!");
                success.setContentText(String.format(
                        "Account Type: %s Account\n" +
                                "Account Number: %s\n" +
                                "Initial Deposit: BWP %.2f\n" +
                                "Customer: %s\n\n" +
                                "Your new account is now active!",
                        accountType, accountNumber, initialDeposit, customer.getCustomerId()
                ));
                success.showAndWait();

                // Clear fields
                txtInitialDeposit.clear();
                txtEmployer.clear();
                txtEmployerAddress.clear();
            } else {
                showAlert("Error", "Failed to create account. Please try again.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid amount for initial deposit.");
        } catch (Exception e) {
            showAlert("Error", "Failed to create account: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load dashboard: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}