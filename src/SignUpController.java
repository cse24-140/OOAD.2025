import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextField txtFirstNames;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private Button btnCreateAccount;

    @FXML
    private Button btnBackToLogin;

    @FXML
    private void handleCreateAccount() {
        String customerID = txtCustomerID.getText();
        String firstName = txtFirstNames.getText();
        String lastName = txtLastName.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        // ✅ Validate fields
        if (customerID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
                username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // ✅ Check if username already exists
        if (UserData.userExists(username)) {
            showAlert("Error", "Username already exists! Please choose another one.");
            return;
        }

        // ✅ Check password confirmation
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match!");
            return;
        }

        // ✅ Save new user account
        UserData.addAccount(username, password);

        showAlert("Success", "Account created successfully for " + username + "!\nYou can now log in.");
        loadLoginScreen();
    }

    @FXML
    private void handleBackToLogin() {
        loadLoginScreen();
    }

    // 🔹 Utility: Show alert message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 🔹 Load the login screen
    private void loadLoginScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml")); // no package, same folder
            Stage stage = (Stage) btnCreateAccount.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Orange Bank of Botswana - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load Login.fxml");
        }
    }
}
