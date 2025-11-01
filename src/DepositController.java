import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class DepositController {

    @FXML private ComboBox<String> cmbAccountType;
    @FXML private TextField txtAmountField;
    @FXML private Button btnConfirmDeposit;
    @FXML private Button btnBack;

    private BankingSystem bankingSystem;
    private Customer customer;

    // ADD THESE MISSING METHODS
    public void setCustomer(Customer customer) {
        this.customer = customer;
        populateAccountComboBox();
    }

    public void setBankingSystem(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
    }

    @FXML
    public void initialize() {
        // Initialize will be called before setCustomer, so we populate in setCustomer
    }

    private void populateAccountComboBox() {
        cmbAccountType.getItems().clear();
        if (customer != null) {
            for (Account account : customer.getAccounts()) {
                cmbAccountType.getItems().add(account.getAccountNumber() + " - " + account.getAccountType());
            }
            if (!customer.getAccounts().isEmpty()) {
                cmbAccountType.setValue(cmbAccountType.getItems().get(0));
            }
        }
    }

    @FXML
    private void handleConfirmDeposit() {
        String amountText = txtAmountField.getText().trim();
        String selectedAccount = cmbAccountType.getValue();

        if (amountText.isEmpty()) {
            showAlert("Error", "Please enter an amount to deposit.");
            return;
        }

        if (selectedAccount == null || selectedAccount.isEmpty()) {
            showAlert("Error", "Please select an account.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                showAlert("Error", "Amount must be greater than zero.");
                return;
            }

            // Extract account number from selection (format: "ACC00001 - Savings")
            String accountNumber = selectedAccount.split(" - ")[0];

            // Perform actual deposit
            boolean success = bankingSystem.deposit(accountNumber, amount);

            if (success) {
                Account account = bankingSystem.getAccount(accountNumber);
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Deposit Successful");
                successAlert.setHeaderText("Funds Deposited");
                successAlert.setContentText(String.format(
                        "You have successfully deposited BWP %.2f to your account.\n\n" +
                                "Transaction Details:\n" +
                                "• Amount: BWP %.2f\n" +
                                "• Account: %s (%s)\n" +
                                "• New Balance: BWP %.2f\n" +
                                "• Transaction ID: DEP%08d",
                        amount, amount, accountNumber, account.getAccountType(),
                        account.getBalance(),
                        (int)(Math.random() * 100000000)
                ));
                successAlert.showAndWait();

                txtAmountField.clear();
                populateAccountComboBox(); // Refresh account list in case balances changed
            } else {
                showAlert("Error", "Deposit failed. Please try again.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount. Please enter a valid numeric value.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManageAccounts.fxml"));
            Parent root = loader.load();

            ViewAccountsController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Accounts - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load manage accounts screen: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}