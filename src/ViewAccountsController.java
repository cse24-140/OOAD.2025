import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;

public class ViewAccountsController {

    @FXML private Button btnCashWithdrawl;
    @FXML private Button btnInvestment;
    @FXML private Button btnDeposit;
    @FXML private Button btnBack;
    @FXML private TextArea txtViewAccounts;

    private BankingSystem bankingSystem;
    private Customer customer;

    // Add these methods to accept customer and banking system data
    public void setCustomer(Customer customer) {
        this.customer = customer;
        displayAccountInformation(); // Refresh display with real customer data
    }

    public void setBankingSystem(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
    }

    @FXML
    public void initialize() {
        displayAccountInformation();
    }

    private void displayAccountInformation() {
        if (customer == null || bankingSystem == null) {
            txtViewAccounts.setText("No customer data available.");
            return;
        }

        StringBuilder accountInfo = new StringBuilder();
        accountInfo.append("=== ACCOUNT SUMMARY ===\n\n");

        // Customer Information
        if (customer instanceof IndividualCustomer) {
            IndividualCustomer indCustomer = (IndividualCustomer) customer;
            accountInfo.append(String.format("Account Holder: %s %s\n",
                    indCustomer.getFirstName(), indCustomer.getLastName()));
        } else if (customer instanceof CompanyCustomer) {
            CompanyCustomer compCustomer = (CompanyCustomer) customer;
            accountInfo.append(String.format("Company: %s\n", compCustomer.getCompanyName()));
        }

        accountInfo.append(String.format("Customer ID: %s\n", customer.getCustomerId()));
        accountInfo.append(String.format("Total Balance: BWP %.2f\n\n",
                bankingSystem.getCustomerTotalBalance(customer.getCustomerId())));

        // Account Details
        accountInfo.append("ACCOUNT DETAILS:\n");
        accountInfo.append("================\n");

        if (customer.getAccounts().isEmpty()) {
            accountInfo.append("No accounts found. Please open an account to get started.\n");
        } else {
            for (Account account : customer.getAccounts()) {
                accountInfo.append(String.format("• %s: %s - BWP %.2f\n",
                        account.getAccountType(), account.getAccountNumber(), account.getBalance()));
            }
        }

        accountInfo.append("\nRECENT ACTIVITY:\n");
        accountInfo.append("================\n");

        // Sample recent activity - in real app, this would come from transaction history
        if (!customer.getAccounts().isEmpty()) {
            accountInfo.append("• Last login: Today\n");
            accountInfo.append("• Accounts: " + customer.getAccounts().size() + " active\n");
            accountInfo.append("• Status: All accounts in good standing\n");

            // Add some sample transactions based on account types
            for (Account account : customer.getAccounts()) {
                if (account.getAccountNumber().equals("ACC00001")) {
                    accountInfo.append("• Recent: Interest applied to Savings account\n");
                } else if (account.getAccountNumber().equals("ACC00002")) {
                    accountInfo.append("• Recent: Cheque account active\n");
                } else if (account.getAccountNumber().equals("ACC00003")) {
                    accountInfo.append("• Recent: Business account in good standing\n");
                }
            }
        } else {
            accountInfo.append("• No recent activity\n");
        }

        txtViewAccounts.setText(accountInfo.toString());
    }

    @FXML
    private void handleCashWithdrawl() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Withdraw.fxml"));
            Parent root = loader.load();

            // Pass data to CashWithdrawlController
            CashWithdrawlController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnCashWithdrawl.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cash Withdrawal - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load cash withdrawal screen");
        }
    }

    @FXML
    private void handleInvestment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Investment.fxml"));
            Parent root = loader.load();

            // Pass data to InvestmentController
            InvestmentController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnInvestment.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Investment Options - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load investment screen");
        }
    }

    @FXML
    private void handleDeposit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Deposit.fxml"));
            Parent root = loader.load();

            // Pass data to DepositController
            DepositController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnDeposit.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Deposit - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load deposit screen");
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Parent root = loader.load();

            // Pass data back to DashboardController
            DashboardController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load dashboard");
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