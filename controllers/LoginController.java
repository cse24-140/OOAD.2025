package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import models.BankingSystem;
import models.Customer;

import java.io.IOException;

public class LoginController {

    @FXML private Button btnLogin;
    @FXML private Button btnSignUp;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    private BankingSystem bankingSystem;

    @FXML
    public void initialize() {
        bankingSystem = BankingSystem.getInstance();
    }

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }

        // 🔥 Authenticate directly through the database now
        Customer customer = bankingSystem.login(username, password);

        if (customer != null) {
            bankingSystem.setLoggedInCustomer(customer);
            loadDashboard(customer);
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private void loadDashboard(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard - Orange Bank of Botswana");
            stage.show();

        } catch (IOException e) {
            showAlert("Error", "Unable to load dashboard: " + e.getMessage());
        }
    }

    @FXML
    private void handleSignUp() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/SignUp.fxml"));
            Stage stage = (Stage) btnSignUp.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Unable to load sign up page.");
        }
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
