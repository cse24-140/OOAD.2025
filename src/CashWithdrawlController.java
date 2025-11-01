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

public class CashWithdrawlController {

    @FXML private ComboBox<String> cmbAccount;
    @FXML private TextField txtAmountField;
    @FXML private Button btnConfirm;
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
        // Initialize will be called before setCustomer
    }

    private void populateAccountComboBox() {
        cmbAccount.getItems().clear();
        if (customer != null) {
            for (Account account : customer.getAccounts()) {
                // Only show accounts that allow withdrawals (not Savings accounts)
                if (!(account instanceof SavingsAccount)) {
                    cmbAccount.getItems().add(account.getAccountNumber() + " - " + account.getAccountType() +
                            " (BWP " + String.format("%.2f", account.getBalance()) + ")");
                }
            }
            if (!cmbAccount.getItems().isEmpty()) {
                cmbAccount.setValue(cmbAccount.getItems().get(0));
            }
        }
    }

    @FXML
    private void handleConfirm() {
        String amountText = txtAmountField.getText().trim();
        String selectedAccount = cmbAccount.getValue();

        if (amountText.isEmpty()) {
            showAlert("Error", "Please enter an amount to withdraw.");
            return;
        }

        if (selectedAccount == null || selectedAccount.isEmpty()) {
            showAlert("Error", "Please select an account to withdraw from.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                showAlert("Error", "Amount must be greater than zero.");
                return;
            }

            if (amount > 5000) { // Daily withdrawal limit
                showAlert("Error", "Withdrawal amount exceeds daily limit of BWP 5,000.00");
                return;
            }

            // Extract account number from selection
            String accountNumber = selectedAccount.split(" - ")[0];

            // Check if it's a SavingsAccount (no withdrawals allowed)
            Account account = bankingSystem.getAccount(accountNumber);
            if (account instanceof SavingsAccount) {
                showAlert("Error", "Withdrawals are not allowed from Savings accounts.");
                return;
            }

            // Perform actual withdrawal
            boolean success = bankingSystem.withdraw(accountNumber, amount);

            if (success) {
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Withdrawal Successful");
                successAlert.setHeaderText("Transaction Completed");
                successAlert.setContentText(String.format(
                        "You have successfully withdrawn BWP %.2f from your account.\n\n" +
                                "Transaction Details:\n" +
                                "• Amount: BWP %.2f\n" +
                                "• Account: %s (%s)\n" +
                                "• Available Balance: BWP %.2f\n" +
                                "• Transaction ID: TXN%08d",
                        amount, amount, accountNumber, account.getAccountType(),
                        account.getBalance(),
                        (int)(Math.random() * 100000000)
                ));
                successAlert.showAndWait();

                txtAmountField.clear();
                populateAccountComboBox(); // Refresh account balances
            } else {
                showAlert("Error", "Withdrawal failed. Insufficient funds or account not found.");
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount. Please enter a valid numeric value (e.g., 100.50).");
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