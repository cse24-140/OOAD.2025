import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label txtBankTitle;

    private BankingSystem bankingSystem;

    @FXML
    public void initialize() {
        // Initialize the BankingSystem instance (Singleton)
        bankingSystem = BankingSystem.getInstance();
        System.out.println("LoginController initialized. Total customers: " + bankingSystem.getCustomerCount());
        System.out.println("Available customers: " + bankingSystem.getCustomers().keySet());
    }

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        System.out.println("Login attempt - Username: " + username + ", Password: " + password);

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both Customer ID and password.");
            return;
        }

        Customer customer = bankingSystem.getCustomer(username);
        System.out.println("Customer found: " + (customer != null));

        if (customer != null) {
            if (isValidCustomerLogin(username, password)) {
                showAlert("Login Successful", "Welcome back, " + getCustomerDisplayName(customer) + "!");
                loadDashboard(customer);
            } else {
                showAlert("Login Failed", "Invalid password. Please try again.");
            }
        } else {
            showAlert("Account Not Found",
                    "No customer account found with ID: " + username + "\n\n" +
                            "Available Customer IDs: " + bankingSystem.getCustomers().keySet() + "\n" +
                            "Please sign up for a new account or check your Customer ID.");
        }
    }

    private boolean isValidCustomerLogin(String customerId, String password) {
        Customer customer = bankingSystem.getCustomer(customerId);
        if (customer == null) return false;

        // Compare entered password with the one stored in the Customer object
        return password.equals(customer.getPassword());
    }

    private String getCustomerDisplayName(Customer customer) {
        if (customer instanceof IndividualCustomer) {
            IndividualCustomer indCustomer = (IndividualCustomer) customer;
            return indCustomer.getFirstName() + " " + indCustomer.getLastName();
        } else if (customer instanceof CompanyCustomer) {
            CompanyCustomer compCustomer = (CompanyCustomer) customer;
            return compCustomer.getCompanyName();
        }
        return customer.getCustomerId();
    }

    @FXML
    private void handleSignUp() {
        loadSignUp();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadDashboard(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Parent root = loader.load();

            // Send data to DashboardController
            DashboardController dashboardController = loader.getController();
            dashboardController.setCustomer(customer);
            dashboardController.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Orange Bank of Botswana - Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load dashboard: " + e.getMessage());
        }
    }

    private void loadSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
            Parent root = loader.load();

            // Pass banking system to sign-up controller
            SignUpController signUpController = loader.getController();
            signUpController.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnSignUp.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Orange Bank of Botswana - Sign Up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load sign-up form: " + e.getMessage());
        }
    }
}