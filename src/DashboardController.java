import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    @FXML
    private Button btnAccounts;

    @FXML
    private Button btnViewBalance;

    @FXML
    private Button btnViewTransactionHistory;

    @FXML
    private Button btnLogOut;

    @FXML
    private TextArea txtDASHBOARD;

    @FXML
    private TextArea txtOrangeBankOfBotswana;

    // Handle Accounts button
    @FXML
    private void handleAccounts() {
        showAlert("Accounts", "Redirecting to Accounts Management...");
        // Future: loadAccountsScreen();
    }

    // Handle View Balance button
    @FXML
    private void handleViewBalance() {
        showAlert("Balance", "Fetching your account balance...");
        // Future: loadViewBalanceScreen();
    }

    // Handle View Transaction History button
    @FXML
    private void handleViewTransactionHistory() {
        showAlert("Transaction History", "Loading your transaction history...");
        // Future: loadTransactionHistoryScreen();
    }

    // Handle Log Out button
    @FXML
    private void handleLogOut() {
        showAlert("Logout", "You have been logged out successfully.");
        loadLoginScreen();
    }

    // Reusable alert popup
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Load Login screen
    private void loadLoginScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Stage stage = (Stage) btnLogOut.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Orange Bank of Botswana - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load Login.fxml");
        }
    }

    public void setUsername(String username) {
    }
}

