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

public class ManageAccountController {

    @FXML
    private TextArea txtOrangeBankOfBotswana;

    @FXML
    private TextArea txtManageAccounts;

    @FXML
    private Button txtCashWithdrawl;  // Note: matches FXML spelling

    @FXML
    private Button btnInvestment;

    @FXML
    private Button btnDeposit;

    @FXML
    private Button btnBack;

    @FXML
    public void initialize() {
        // Initialize the text areas with content
        txtOrangeBankOfBotswana.setText("Orange Bank of Botswana\nYour Trusted Banking Partner");
        txtOrangeBankOfBotswana.setEditable(false);
        txtOrangeBankOfBotswana.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        displayAccountInformation();
    }

    private void displayAccountInformation() {
        String accountInfo = """
            Account Management Dashboard
            ===========================
            
            Available Accounts:
            • Savings Account: BWP 3,000.00
            • Investment Account: BWP 2,000.00
            
            Quick Actions:
            • Cash Withdrawal - Withdraw funds from your account
            • Investment - Manage your investment portfolio
            • Deposit - Add funds to your account
            
            Total Balance: BWP 5,000.00
            Available Balance: BWP 4,800.00
            """;

        txtManageAccounts.setText(accountInfo);
        txtManageAccounts.setEditable(false);
    }

    // Handle Cash Withdrawal
    @FXML
    private void handleCashWithdrawl() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CashWithdrawl.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) txtCashWithdrawl.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cash Withdrawal - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load cash withdrawal screen: " + e.getMessage());
        }
    }

    // Handle Investment
    @FXML
    private void handleInvestment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Investment.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnInvestment.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Investment - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load investment screen: " + e.getMessage());
        }
    }

    // Handle Deposit
    @FXML
    private void handleDeposit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Deposit.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnDeposit.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Deposit - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load deposit screen: " + e.getMessage());
        }
    }

    // Handle Back
    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load dashboard: " + e.getMessage());
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