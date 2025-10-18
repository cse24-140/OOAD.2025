import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ViewAccountController {

    @FXML
    private TextArea txtOrangeBankOfBotswana;

    @FXML
    private TextArea txtViewAccounts;

    @FXML
    private Button txtCashWithdrawl;

    @FXML
    private Button btnInvestment;

    @FXML
    private Button btnDeposit;

    @FXML
    private Button btnBack;

    // Handle Cash Withdrawal
    @FXML
    private void handleCashWithdrawl() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cash Withdrawal");
        alert.setHeaderText(null);
        alert.setContentText("Processing cash withdrawal...");
        alert.showAndWait();
        // Add actual withdrawal logic here
    }

    // Handle Investment
    @FXML
    private void handleInvestment() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Investment");
        alert.setHeaderText(null);
        alert.setContentText("Redirecting to investment options...");
        alert.showAndWait();
        // Add scene switching to investment page here
    }

    // Handle Deposit
    @FXML
    private void handleDeposit() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Deposit");
        alert.setHeaderText(null);
        alert.setContentText("Processing deposit...");
        alert.showAndWait();
        // Add actual deposit logic here
    }

    // Handle Back
    @FXML
    private void handleBack() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Back");
        alert.setHeaderText(null);
        alert.setContentText("Going back to the previous screen...");
        alert.showAndWait();
        // Add actual scene-switching logic here
    }
}
