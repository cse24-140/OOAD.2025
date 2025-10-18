import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class DepositController {

    @FXML
    private TextArea txtOrangeBankOfBotswana;

    @FXML
    private TextArea txtDeposit;

    @FXML
    private TextArea txtEnterAmount;

    @FXML
    private TextField txtEnterAmountField;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnBack;

    // Handle Confirm button
    @FXML
    private void handleConfirm() {
        String amountText = txtEnterAmountField.getText();

        if (amountText.isEmpty()) {
            showAlert("Error", "Please enter an amount to deposit.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                showAlert("Error", "Amount must be greater than zero.");
                return;
            }

            showAlert("Success", "You have successfully deposited P" + amount + " into your account.");
            txtEnterAmountField.clear();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount. Please enter a numeric value.");
        }
    }

    // Handle Back button
    @FXML
    private void handleBack() {
        loadDashboardScreen();
    }

    // Show alert messages
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Load Dashboard screen
    private void loadDashboardScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Orange Bank of Botswana - Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load Dashboard.fxml");
        }
    }
}
