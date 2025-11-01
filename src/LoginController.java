import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private ComboBox<String> cmbCustomerType;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    @FXML
    public void initialize() {
        // Initialize ComboBox items
        cmbCustomerType.setItems(FXCollections.observableArrayList("Individual", "Business"));
        cmbCustomerType.setValue("Individual");
    }

    @FXML
    private void handleLogin() {
        String customerType = cmbCustomerType.getValue();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        System.out.println("Login attempted:");
        System.out.println("Customer Type: " + customerType);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Username and password are required!");
            return;
        }

        // For demo purposes, accept any non-empty credentials
        // TODO: Replace with real authentication logic
        showAlert("Login Successful", "Welcome " + username + " to Orange Bank of Botswana!");

        // Navigate to Dashboard
        loadDashboard();
    }

    @FXML
    private void handleGoToSignUp() {
        try {
            // Load the sign-up screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) txtUsername.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root, 520, 850);
            stage.setScene(scene);
            stage.setTitle("Orange Bank - Sign Up");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Cannot load SignUp.fxml: " + e.getMessage());
        }
    }

    // Load Dashboard.fxml after login
    private void loadDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();

            // Get the stage from the current scene
            Stage stage = (Stage) txtUsername.getScene().getWindow();

            // Set the dashboard scene
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setTitle("Orange Bank of Botswana - Dashboard");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load Dashboard.fxml: " + e.getMessage());
        }
    }

    // Utility: Clear login form
    private void clearForm() {
        txtUsername.clear();
        txtPassword.clear();
        cmbCustomerType.setValue("Individual");
    }

    // Utility: Show popup alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
