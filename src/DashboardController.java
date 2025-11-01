import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController {

    @FXML private Label lblWelcome;
    @FXML private Label lblCustomerInfo;
    @FXML private Label lblTotalBalance;
    @FXML private Label lblAccountCount;
    @FXML private Button btnOpenAccount;
    @FXML private Button btnViewBalance;
    @FXML private Button btnLogOut;
    @FXML private Button btnManageAccounts;
    @FXML private Button btnDeposit;
    @FXML private Button btnWithdraw;
    @FXML private Button btnTransactionHistory;
    @FXML private Button btnInvestment;
    @FXML private Button btnApplyInterest;

    private String customerId;
    private BankingSystem bankingSystem;
    private Customer customer;

    @FXML
    public void initialize() {
        System.out.println("DashboardController INITIALIZED!");
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getCustomerId();
        System.out.println("DashboardController: Customer set to " + customerId);
        updateDashboard();
    }

    public void setBankingSystem(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
        System.out.println("DashboardController: BankingSystem set");
        updateDashboard();
    }

    private void updateDashboard() {
        System.out.println("DashboardController: Updating dashboard for " + customerId);

        if (customer != null) {
            lblWelcome.setText("Welcome, " + getCustomerDisplayName(customer) + "!");

            if (customer instanceof IndividualCustomer) {
                IndividualCustomer indCustomer = (IndividualCustomer) customer;
                lblCustomerInfo.setText("Customer: " + indCustomer.getFirstName() + " " + indCustomer.getLastName());
            } else if (customer instanceof CompanyCustomer) {
                CompanyCustomer compCustomer = (CompanyCustomer) customer;
                lblCustomerInfo.setText("Company: " + compCustomer.getCompanyName());
            }

            if (bankingSystem != null) {
                double totalBalance = bankingSystem.getCustomerTotalBalance(customerId);
                int accountCount = bankingSystem.getCustomerAccounts(customerId).size();

                lblTotalBalance.setText("Total Balance: BWP " + String.format("%.2f", totalBalance));
                lblAccountCount.setText("Accounts: " + accountCount);
            }
        }

        System.out.println("DashboardController: Dashboard updated successfully!");
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

    // ===== REAL FUNCTIONALITY =====

    @FXML
    private void handleOpenAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/OpenAccount.fxml"));
            Parent root = loader.load();

            // Pass data to OpenAccountController
            OpenAccountController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnOpenAccount.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Open New Account - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load open account screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleManageAccounts() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManageAccounts.fxml"));
            Parent root = loader.load();

            // Pass data to ViewAccountsController
            ViewAccountsController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnManageAccounts.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Accounts - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load manage accounts screen: " + e.getMessage());
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
            stage.setTitle("Deposit Funds - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load deposit screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleWithdraw() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Withdraw.fxml"));
            Parent root = loader.load();

            // Pass data to CashWithdrawlController
            CashWithdrawlController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) btnWithdraw.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cash Withdrawal - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load withdrawal screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleViewBalance() {
        // Show detailed balance information
        StringBuilder balanceInfo = new StringBuilder();
        balanceInfo.append("=== ACCOUNT BALANCES ===\n\n");

        if (bankingSystem != null && customer != null) {
            double totalBalance = bankingSystem.getCustomerTotalBalance(customerId);
            balanceInfo.append(String.format("Total Balance: BWP %.2f\n\n", totalBalance));

            for (Account account : customer.getAccounts()) {
                balanceInfo.append(String.format("Account: %s\n", account.getAccountNumber()));
                balanceInfo.append(String.format("Type: %s\n", account.getAccountType()));
                balanceInfo.append(String.format("Balance: BWP %.2f\n", account.getBalance()));
                balanceInfo.append("----------------------------\n");
            }

            if (customer.getAccounts().isEmpty()) {
                balanceInfo.append("No accounts found. Please open an account first.");
            }
        }

        showAlert("Account Balances", balanceInfo.toString());
    }

    @FXML
    private void handleTransactionHistory() {
        // Show transaction history
        StringBuilder transactions = new StringBuilder();
        transactions.append("=== RECENT TRANSACTIONS ===\n\n");

        if (bankingSystem != null && customer != null) {
            for (Account account : customer.getAccounts()) {
                transactions.append(String.format("Account: %s (%s)\n", account.getAccountNumber(), account.getAccountType()));
                transactions.append(String.format("Current Balance: BWP %.2f\n", account.getBalance()));

                // Sample transactions - in real app, these would come from a transaction log
                if (account.getAccountNumber().equals("ACC00001")) {
                    transactions.append("• Jan 15: Deposit - BWP 1,000.00\n");
                    transactions.append("• Jan 10: Interest Applied - BWP 15.00\n");
                    transactions.append("• Jan 5: Account Opened - BWP 500.00\n");
                } else if (account.getAccountNumber().equals("ACC00002")) {
                    transactions.append("• Jan 12: Deposit - BWP 2,000.00\n");
                    transactions.append("• Jan 8: Withdrawal - BWP 500.00\n");
                }
                transactions.append("\n");
            }

            if (customer.getAccounts().isEmpty()) {
                transactions.append("No transaction history available.\nOpen an account to start banking!");
            }
        }

        showAlert("Transaction History", transactions.toString());
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
            stage.setTitle("Investment - Orange Bank of Botswana");
        } catch (IOException e) {
            showAlert("Error", "Unable to load investment screen: " + e.getMessage());
        }
    }

    @FXML
    private void handleApplyInterest() {
        if (bankingSystem != null && customer != null) {
            // Apply monthly interest to all accounts
            int accountsWithInterest = 0;
            double totalInterest = 0;

            for (Account account : customer.getAccounts()) {
                double oldBalance = account.getBalance();
                account.applyMonthlyInterest();
                double interestEarned = account.getBalance() - oldBalance;

                if (interestEarned > 0) {
                    accountsWithInterest++;
                    totalInterest += interestEarned;
                }
            }

            if (accountsWithInterest > 0) {
                showAlert("Interest Applied",
                        String.format("Monthly interest applied to %d account(s)!\n\n" +
                                        "Total interest earned: BWP %.2f\n" +
                                        "Your new total balance: BWP %.2f",
                                accountsWithInterest, totalInterest,
                                bankingSystem.getCustomerTotalBalance(customerId)));
                updateDashboard(); // Refresh the dashboard display
            } else {
                showAlert("No Interest Applied",
                        "No interest-bearing accounts found or no interest was applied this month.");
            }
        }
    }

    @FXML
    private void handleLogOut() {
        try {
            System.out.println("Logging out...");
            Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Stage stage = (Stage) btnLogOut.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Orange Bank of Botswana - Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}