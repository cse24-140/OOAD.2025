import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label lblWelcome;

    // Handle "Manage Accounts" button
    @FXML
    private void handleAccounts() {
        loadScene("/Accounts.fxml", "Accounts Management");
    }

    // Handle "View Balance" button
    @FXML
    private void handleViewBalance() {
        loadScene("/Balance.fxml", "View Balance");
    }

    // Handle "Transaction History" button
    @FXML
    private void handleViewTransactionHistory() {
        loadScene("/TransactionHistory.fxml", "Transaction History");
    }

    // Handle "Log Out" button
    @FXML
    private void handleLogOut() {
        showAlert("Logout", "You have been logged out successfully.");
        loadScene("/Login.fxml", "Orange Bank of Botswana - Login");
    }

    // Utility method for alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Utility method to change scenes dynamically
    private void loadScene(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btnAccounts.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Orange Bank of Botswana - " + title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load " + fxmlPath);
        }
    }

    // Optional: Called when login passes username
    public void setUsername(String username) {
        if (lblWelcome != null) {
            lblWelcome.setText("Welcome, " + username + "!");
        }
    }
}
