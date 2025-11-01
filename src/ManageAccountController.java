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

    @FXML private Button btnCashWithdrawl;
    @FXML private Button btnInvestment;
    @FXML private Button btnDeposit;
    @FXML private Button btnBack;
    @FXML private TextArea txtViewAccounts;

    @FXML
    public void initialize() {
        displayAccountInformation();
    }

    private void displayAccountInformation() {
        String accountInfo = """
            Account Summary:
            ================
            Account Holder: John Doe
            Customer ID: CUST001
            Account Type: Savings
            Current Balance: BWP 5,000.00
            
            Account Details:
            • Account 1: SAV001 - BWP 3,000.00 (Savings)
            • Account 2: INV001 - BWP 2,000.00 (Investment)
            
            Recent Transactions:
            • Jan 15: Deposit - BWP 1,000.00
            • Jan 10: Interest Applied - BWP 15.00
            • Jan 5: Account Opened - BWP 500.00
            
            Total Balance: BWP 5,000.00
            """;

        txtViewAccounts.setText(accountInfo);
    }

    @FXML
    private void handleCashWithdrawl() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Withdrawal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnCashWithdrawl.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cash Withdrawal - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load cash withdrawal screen");
        }
    }

    @FXML
    private void handleInvestment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Investment.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnInvestment.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Investment Options - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load investment screen");
        }
    }

    @FXML
    private void handleDeposit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Deposit.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnDeposit.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Deposit - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load deposit screen");
        }
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load dashboard");
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
