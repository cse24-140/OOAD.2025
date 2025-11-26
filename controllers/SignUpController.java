package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;

public class SignUpController {

    // ===== SHARED =====
    @FXML private ComboBox<String> cmbCustomerType;
    @FXML private TextField txtCustomerId;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    // ===== INDIVIDUAL =====
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtIndAddress;
    @FXML private TextField txtOmang;
    @FXML private TextField txtIndEmail;
    @FXML private TextField txtIndPhone;
    @FXML private ComboBox<String> cmbGender;

    // ===== BUSINESS FIELDS =====
    @FXML private TextField txtContactFirstName;
    @FXML private TextField txtContactLastName;
    @FXML private TextField txtCompanyName;
    @FXML private TextField txtCompanyAddress;
    @FXML private TextField txtTaxNumber;
    @FXML private TextField txtBusinessRegNum;
    @FXML private TextField txtPosition;

    // ===== BUSINESS LABELS =====
    @FXML private Label lblContactFirstName;
    @FXML private Label lblContactLastName;
    @FXML private Label lblCompanyName;
    @FXML private Label lblCompanyAddress;
    @FXML private Label lblTaxNumber;
    @FXML private Label lblBusinessRegNum;
    @FXML private Label lblPosition;

    private BankingSystem bankingSystem = BankingSystem.getInstance();

    @FXML
    private void initialize() {
        cmbCustomerType.getItems().addAll("Individual", "Business");
        cmbGender.getItems().addAll("Male", "Female", "Other");

        cmbCustomerType.setOnAction(e -> {
            generateCustomerId();
            toggleFields();
        });

        toggleFields();
    }

    private void toggleFields() {

        boolean isInd = "Individual".equals(cmbCustomerType.getValue());

        txtFirstName.setVisible(isInd);     txtFirstName.setManaged(isInd);
        txtLastName.setVisible(isInd);      txtLastName.setManaged(isInd);
        txtIndAddress.setVisible(isInd);    txtIndAddress.setManaged(isInd);
        txtOmang.setVisible(isInd);         txtOmang.setManaged(isInd);
        txtIndEmail.setVisible(isInd);      txtIndEmail.setManaged(isInd);
        txtIndPhone.setVisible(isInd);      txtIndPhone.setManaged(isInd);
        cmbGender.setVisible(isInd);        cmbGender.setManaged(isInd);

        setBusinessVisibility(!isInd);
    }

    private void setBusinessVisibility(boolean show) {

        txtContactFirstName.setVisible(show);    txtContactFirstName.setManaged(show);
        txtContactLastName.setVisible(show);     txtContactLastName.setManaged(show);
        txtCompanyName.setVisible(show);         txtCompanyName.setManaged(show);
        txtCompanyAddress.setVisible(show);      txtCompanyAddress.setManaged(show);
        txtTaxNumber.setVisible(show);           txtTaxNumber.setManaged(show);
        txtBusinessRegNum.setVisible(show);      txtBusinessRegNum.setManaged(show);
        txtPosition.setVisible(show);            txtPosition.setManaged(show);

        lblContactFirstName.setVisible(show);    lblContactFirstName.setManaged(show);
        lblContactLastName.setVisible(show);     lblContactLastName.setManaged(show);
        lblCompanyName.setVisible(show);         lblCompanyName.setManaged(show);
        lblCompanyAddress.setVisible(show);      lblCompanyAddress.setManaged(show);
        lblTaxNumber.setVisible(show);           lblTaxNumber.setManaged(show);
        lblBusinessRegNum.setVisible(show);      lblBusinessRegNum.setManaged(show);
        lblPosition.setVisible(show);            lblPosition.setManaged(show);
    }

    private void generateCustomerId() {

        if (cmbCustomerType.getValue() == null) return;

        long stamp = System.currentTimeMillis();

        if (cmbCustomerType.getValue().equals("Individual")) {
            txtCustomerId.setText("IND-" + stamp);
        } else {
            txtCustomerId.setText("BUS-" + stamp);
        }
    }

    @FXML
    private void handleSignUp() {

        if (cmbCustomerType.getValue() == null) {
            showAlert("Error", "Please select a customer type.");
            return;
        }

        if (cmbCustomerType.getValue().equals("Individual")) {
            registerIndividual();
        } else {
            registerBusiness();
        }
    }

    private void registerIndividual() {

        String id = txtCustomerId.getText();
        String f = txtFirstName.getText();
        String l = txtLastName.getText();
        String user = txtUsername.getText();
        String pass = txtPassword.getText();
        String addr = txtIndAddress.getText();
        String omang = txtOmang.getText();
        String email = txtIndEmail.getText();
        String phone = txtIndPhone.getText();

        if (f.isEmpty() || l.isEmpty() || user.isEmpty() || pass.isEmpty()
                || addr.isEmpty() || omang.isEmpty()
                || email.isEmpty() || phone.isEmpty()) {

            showAlert("Error", "Please fill out all fields.");
            return;
        }

        IndividualCustomer cust = new IndividualCustomer(
                id, f, l, omang, phone, addr, email, pass, user
        );

        if (bankingSystem.createCustomer(cust)) {
            showAlert("Error", "Could not save customer.");
            return;
        }

        bankingSystem.createAccount(id, "savings", 0);
        showAlert("Success", "Account created.");
        goToLogin();
    }

    private void registerBusiness() {

        String id = txtCustomerId.getText();
        String cf = txtContactFirstName.getText();
        String cl = txtContactLastName.getText();
        String comp = txtCompanyName.getText();
        String addr = txtCompanyAddress.getText();
        String tax = txtTaxNumber.getText();
        String reg = txtBusinessRegNum.getText();
        String pos = txtPosition.getText();
        String email = txtIndEmail.getText();
        String phone = txtIndPhone.getText();

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (cf.isEmpty() || cl.isEmpty() || comp.isEmpty() || addr.isEmpty()
                || tax.isEmpty() || reg.isEmpty() || pos.isEmpty()
                || email.isEmpty() || phone.isEmpty()
                || username.isEmpty() || password.isEmpty()) {

            showAlert("Error", "Please fill out all fields.");
            return;
        }

        CompanyCustomer cust = new CompanyCustomer(
                id, comp, reg, cf, cl, phone, addr, pos, tax, email, password, username
        );

        if (!bankingSystem.createCustomer(cust)) {
            showAlert("Error", "Could not save business customer.");
            return;
        }

        bankingSystem.createAccount(id, "cheque", 0);
        showAlert("Success", "Business account created.");
        goToLogin();
    }

    @FXML
    private void handleLogin() {
        goToLogin();
    }

    private void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/Login.fxml"));
            Stage stage = (Stage) txtPassword.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Could not load Login page.");
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
