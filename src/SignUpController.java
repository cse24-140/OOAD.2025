import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class SignUpController {

    // Individual Customer Fields
    @FXML private ComboBox<String> cmbCustomerType;
    @FXML private ComboBox<String> cmbGender;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtIndAddress;
    @FXML private TextField txtOmang;
    @FXML private TextField txtIndEmail;
    @FXML private TextField txtIndPhone;

    // Business Customer Fields
    @FXML private Label lblContactFirstName;
    @FXML private TextField txtContactFirstName;
    @FXML private Label lblContactLastName;
    @FXML private TextField txtContactLastName;
    @FXML private Label lblCompanyName;
    @FXML private TextField txtCompanyName;
    @FXML private Label lblCompanyAddress;
    @FXML private TextField txtCompanyAddress;
    @FXML private Label lblTaxNumber;
    @FXML private TextField txtTaxNumber;
    @FXML private Label lblBusinessRegNum;
    @FXML private TextField txtBusinessRegNum;
    @FXML private Label lblPosition;
    @FXML private TextField txtPosition;

    @FXML
    public void initialize() {
        System.out.println("SignUpController initialized!");

        // Initialize ComboBox items
        cmbCustomerType.setItems(FXCollections.observableArrayList("Individual", "Business"));
        cmbGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        // Set default values
        cmbCustomerType.setValue("Individual");
        cmbGender.setValue("Male");

        // Listener for customer type switch
        cmbCustomerType.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean isBusiness = "Business".equals(newVal);
            toggleBusinessFields(isBusiness);
        });

        // Start with business fields hidden
        toggleBusinessFields(false);
    }

    // Toggle visibility of business fields
    private void toggleBusinessFields(boolean showBusiness) {
        lblContactFirstName.setVisible(showBusiness);
        txtContactFirstName.setVisible(showBusiness);
        lblContactLastName.setVisible(showBusiness);
        txtContactLastName.setVisible(showBusiness);
        lblCompanyName.setVisible(showBusiness);
        txtCompanyName.setVisible(showBusiness);
        lblCompanyAddress.setVisible(showBusiness);
        txtCompanyAddress.setVisible(showBusiness);
        lblTaxNumber.setVisible(showBusiness);
        txtTaxNumber.setVisible(showBusiness);
        lblBusinessRegNum.setVisible(showBusiness);
        txtBusinessRegNum.setVisible(showBusiness);
        lblPosition.setVisible(showBusiness);
        txtPosition.setVisible(showBusiness);

        if (!showBusiness) {
            txtContactFirstName.clear();
            txtContactLastName.clear();
            txtCompanyName.clear();
            txtCompanyAddress.clear();
            txtTaxNumber.clear();
            txtBusinessRegNum.clear();
            txtPosition.clear();
        }
    }

    // Handle the Sign Up button
    @FXML
    private void handleSignUp() {
        System.out.println("=== SIGN UP ATTEMPT ===");

        String customerType = cmbCustomerType.getValue();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String address = txtIndAddress.getText();
        String omang = txtOmang.getText();
        String email = txtIndEmail.getText();
        String phone = txtIndPhone.getText();
        String gender = cmbGender.getValue();

        // Individual customer validation
        if ("Individual".equals(customerType)) {
            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                    password.isEmpty() || email.isEmpty() || omang.isEmpty()) {
                showAlert("Sign Up Error", "Please fill in all required fields for individual account!");
                return;
            }
        }

        // Business customer validation
        if ("Business".equals(customerType)) {
            String contactFirstName = txtContactFirstName.getText();
            String contactLastName = txtContactLastName.getText();
            String companyName = txtCompanyName.getText();
            String companyAddress = txtCompanyAddress.getText();
            String taxNumber = txtTaxNumber.getText();
            String businessRegNum = txtBusinessRegNum.getText();

            if (contactFirstName.isEmpty() || contactLastName.isEmpty() ||
                    companyName.isEmpty() || companyAddress.isEmpty() ||
                    taxNumber.isEmpty() || businessRegNum.isEmpty()) {
                showAlert("Sign Up Error", "Please fill in all required fields for business account!");
                return;
            }
        }

        // If validation passed
        showAlert("Sign Up Successful", "Account created successfully! Redirecting to login...");
        loadLoginScreen();
    }

    // Handle Login button (optional direct login link)
    @FXML
    private void handleLogin() {
        loadLoginScreen();
    }

    // Load the Login.fxml scene
    private void loadLoginScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Orange Bank of Botswana - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load Login.fxml");
        }
    }

    // Helper: show popup message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
