import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class SignUpController {

    // Customer Type and Gender
    @FXML private ComboBox<String> cmbCustomerType;
    @FXML private ComboBox<String> cmbGender;

    // Customer ID Field
    @FXML private TextField txtCustomerId;

    // Individual Customer Fields
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

    // Buttons
    @FXML private Button btnLogin;
    @FXML private Button btnSignUp;

    private BankingSystem bankingSystem;
    private String generatedCustomerId;

    public void setBankingSystem(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
        generateAndDisplayCustomerId();
    }

    @FXML
    public void initialize() {
        System.out.println("SignUpController initialized!");

        // Initialize ComboBox items
        cmbCustomerType.setItems(FXCollections.observableArrayList("Individual", "Business"));
        cmbGender.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        // Set default values
        cmbCustomerType.setValue("Individual");
        cmbGender.setValue("Male");

        // Generate initial Customer ID
        if (bankingSystem != null) {
            generateAndDisplayCustomerId();
        }

        // Listener for customer type switch
        cmbCustomerType.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean isBusiness = "Business".equals(newVal);
            toggleBusinessFields(isBusiness);
            // Regenerate Customer ID when type changes
            generateAndDisplayCustomerId();
        });

        // Start with business fields hidden
        toggleBusinessFields(false);
    }

    // Generate and display Customer ID
    private void generateAndDisplayCustomerId() {
        if (bankingSystem != null) {
            generatedCustomerId = generateCustomerId();
            txtCustomerId.setText(generatedCustomerId);
            javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip("This is your unique Customer ID. Save it for login.");
            txtCustomerId.setTooltip(tooltip);
            System.out.println("Generated Customer ID: " + generatedCustomerId);
        }
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
        System.out.println("=== CUSTOMER REGISTRATION ===");
        System.out.println("BankingSystem instance: " + (bankingSystem != null));
        System.out.println("Current customers before registration: " + (bankingSystem != null ? bankingSystem.getCustomers().size() : "N/A"));

        String customerType = cmbCustomerType.getValue();
        String password = txtPassword.getText().trim();  // ✅ Capture password
        Customer newCustomer = null;

        if (password.isEmpty()) {
            showAlert("Validation Error", "Password is required!");
            txtPassword.requestFocus();
            return;
        }

        // Individual customer registration
        if ("Individual".equals(customerType)) {
            if (!validateIndividualFields()) {
                return;
            }

            newCustomer = new IndividualCustomer(
                    generatedCustomerId,
                    txtFirstName.getText().trim(),
                    txtLastName.getText().trim(),
                    txtOmang.getText().trim(),
                    txtIndPhone.getText().trim(),
                    txtIndAddress.getText().trim(),
                    txtIndEmail.getText().trim(),
                    password   // ✅ Pass password
            );
        }
        // Business customer registration
        else if ("Business".equals(customerType)) {
            if (!validateBusinessFields()) {
                return;
            }

            newCustomer = new CompanyCustomer(
                    generatedCustomerId,
                    txtCompanyName.getText().trim(),
                    txtBusinessRegNum.getText().trim(),
                    txtContactFirstName.getText().trim() + " " + txtContactLastName.getText().trim(),
                    txtIndPhone.getText().trim(),
                    txtCompanyAddress.getText().trim(),
                    txtIndEmail.getText().trim(),
                    password  // ✅ Pass password
            );
        }

        if (newCustomer != null && bankingSystem != null) {
            bankingSystem.addCustomer(newCustomer);

            System.out.println("Customer registered successfully: " + newCustomer.getCustomerId());
            System.out.println("Total customers after registration: " + bankingSystem.getCustomers().size());
            System.out.println("All customers: " + bankingSystem.getCustomers().keySet());

            showRegistrationSuccess(newCustomer);
        } else {
            showAlert("Error", "Registration failed. Banking system not available.");
        }
    }

    private boolean validateIndividualFields() {
        if (txtFirstName.getText().trim().isEmpty()) {
            showAlert("Validation Error", "First Name is required!");
            txtFirstName.requestFocus();
            return false;
        }
        if (txtLastName.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Last Name is required!");
            txtLastName.requestFocus();
            return false;
        }
        if (txtOmang.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Omang Number is required!");
            txtOmang.requestFocus();
            return false;
        }
        if (txtIndEmail.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Email is required!");
            txtIndEmail.requestFocus();
            return false;
        }
        if (txtIndPhone.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Phone Number is required!");
            txtIndPhone.requestFocus();
            return false;
        }
        if (txtIndAddress.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Address is required!");
            txtIndAddress.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateBusinessFields() {
        if (txtCompanyName.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Company Name is required!");
            txtCompanyName.requestFocus();
            return false;
        }
        if (txtBusinessRegNum.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Business Registration Number is required!");
            txtBusinessRegNum.requestFocus();
            return false;
        }
        if (txtContactFirstName.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Contact First Name is required!");
            txtContactFirstName.requestFocus();
            return false;
        }
        if (txtContactLastName.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Contact Last Name is required!");
            txtContactLastName.requestFocus();
            return false;
        }
        if (txtCompanyAddress.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Company Address is required!");
            txtCompanyAddress.requestFocus();
            return false;
        }
        return true;
    }

    private String generateCustomerId() {
        if (bankingSystem == null) {
            return "IND00000"; // Fallback if bankingSystem is null
        }

        String customerTypePrefix = "Business".equals(cmbCustomerType.getValue()) ? "BUS" : "IND";
        int customerCount = bankingSystem.getCustomers().size() + 1;
        return String.format("%s%05d", customerTypePrefix, customerCount);
    }

    private void showRegistrationSuccess(Customer customer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText("Welcome to Orange Bank of Botswana!");

        String customerDetails = "";
        if (customer instanceof IndividualCustomer) {
            IndividualCustomer indCustomer = (IndividualCustomer) customer;
            customerDetails = String.format(
                    "Name: %s %s\nOmang: %s\nEmail: %s\nPhone: %s",
                    indCustomer.getFirstName(), indCustomer.getLastName(),
                    indCustomer.getNationalId(), indCustomer.getEmail(), indCustomer.getPhone()
            );
        } else if (customer instanceof CompanyCustomer) {
            CompanyCustomer compCustomer = (CompanyCustomer) customer;
            customerDetails = String.format(
                    "Company: %s\nRegistration: %s\nContact: %s\nEmail: %s",
                    compCustomer.getCompanyName(), compCustomer.getRegistrationNumber(),
                    compCustomer.getContactPerson(), compCustomer.getEmail()
            );
        }

        alert.setContentText(
                "Your account has been created successfully!\n\n" +
                        "Customer ID: " + customer.getCustomerId() + "\n\n" +
                        customerDetails + "\n\n" +
                        "Important: Please save your Customer ID and Password for login.\n" +
                        "You can now log in and access your dashboard."
        );

        alert.showAndWait().ifPresent(response -> loadLoginScreen());
    }

    // Handle Login button
    @FXML
    private void handleLogin() {
        loadLoginScreen();
    }

    private void loadLoginScreen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Orange Bank of Botswana - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load login screen");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}