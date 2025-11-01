import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;

public class InvestmentController {

    @FXML private Button btnCalculateReturn;
    @FXML private Button btnInvest;
    @FXML private Button btnBack;
    @FXML private TextField txtInvestmentAmount;

    private BankingSystem bankingSystem;
    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setBankingSystem(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
    }

    @FXML
    public void initialize() {
        System.out.println("InvestmentController initialized");
    }

    @FXML
    private void handleCalculateReturn() {
        String amountText = txtInvestmentAmount.getText().trim();

        if (amountText.isEmpty()) {
            showAlert("Error", "Please enter an investment amount to calculate returns.");
            return;
        }

        try {
            double initialInvestment = Double.parseDouble(amountText);

            if (initialInvestment < InvestmentAccount.getMinOpeningBalance()) {
                showAlert("Error", "Minimum investment amount is BWP " +
                        InvestmentAccount.getMinOpeningBalance());
                return;
            }

            // Use the constant directly since we know the interest rate
            double monthlyRate = 0.05; // 5% monthly interest rate
            double annualRate = monthlyRate * 12;

            // Calculate returns for different periods
            double monthlyReturn = initialInvestment * monthlyRate;
            double yearlyReturn = initialInvestment * annualRate;
            double futureValue1Year = initialInvestment * (1 + annualRate);
            double futureValue5Years = initialInvestment * Math.pow(1 + annualRate, 5);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Investment Return Calculation");
            alert.setHeaderText("Projected Investment Returns");
            alert.setContentText(String.format(
                    "Initial Investment: BWP %.2f\n\n" +
                            "Monthly Interest Rate: %.1f%%\n" +
                            "Annual Interest Rate: %.1f%%\n\n" +
                            "Projected Returns:\n" +
                            "• Monthly Return: BWP %.2f\n" +
                            "• Yearly Return: BWP %.2f\n" +
                            "• Value after 1 year: BWP %.2f\n" +
                            "• Value after 5 years: BWP %.2f\n\n" +
                            "Note: Returns are projections and may vary.",
                    initialInvestment,
                    monthlyRate * 100,
                    annualRate * 100,
                    monthlyReturn,
                    yearlyReturn,
                    futureValue1Year,
                    futureValue5Years
            ));
            alert.showAndWait();

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount. Please enter a valid numeric value.");
        }
    }

    @FXML
    private void handleInvest() {
        String amountText = txtInvestmentAmount.getText().trim();

        if (amountText.isEmpty()) {
            showAlert("Error", "Please enter an investment amount.");
            return;
        }

        try {
            double initialInvestment = Double.parseDouble(amountText);

            if (initialInvestment < InvestmentAccount.getMinOpeningBalance()) {
                showAlert("Error", "Minimum investment amount is BWP " +
                        InvestmentAccount.getMinOpeningBalance());
                return;
            }

            // Use the constant directly
            double monthlyRate = 0.05; // 5% monthly interest rate
            double annualRate = monthlyRate * 12;

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Investment");
            confirmation.setHeaderText("Open Investment Account");
            confirmation.setContentText(String.format(
                    "Are you sure you want to open an investment account?\n\n" +
                            "Investment Details:\n" +
                            "• Initial Investment: BWP %.2f\n" +
                            "• Minimum Balance: BWP %.2f\n" +
                            "• Monthly Interest: %.1f%%\n" +
                            "• Annual Interest: %.1f%%",
                    initialInvestment,
                    InvestmentAccount.getMinOpeningBalance(),
                    monthlyRate * 100,
                    annualRate * 100
            ));

            confirmation.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    try {
                        // Create investment account
                        String accountNumber = bankingSystem.createAccount(
                                customer.getCustomerId(), "Investment", initialInvestment);

                        if (accountNumber != null) {
                            Alert success = new Alert(AlertType.INFORMATION);
                            success.setTitle("Investment Successful");
                            success.setHeaderText("Investment Account Opened");
                            success.setContentText(String.format(
                                    "Your investment account has been opened successfully!\n\n" +
                                            "Account Details:\n" +
                                            "• Account Number: %s\n" +
                                            "• Initial Investment: BWP %.2f\n" +
                                            "• Monthly Interest Rate: %.1f%%\n" +
                                            "• Next interest application: End of month",
                                    accountNumber, initialInvestment,
                                    monthlyRate * 100
                            ));
                            success.showAndWait();
                            txtInvestmentAmount.clear();
                        } else {
                            showAlert("Error", "Failed to create investment account. Please try again.");
                        }
                    } catch (Exception e) {
                        showAlert("Error", "Failed to create investment account: " + e.getMessage());
                    }
                }
            });

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount. Please enter a valid numeric value.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManageAccounts.fxml"));
            Parent root = loader.load();

            ViewAccountsController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Accounts - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load manage accounts screen: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}