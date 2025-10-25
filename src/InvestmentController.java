import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InvestmentController {

    @FXML
    private TextArea txtOrangeBankOfBotswana;

    @FXML
    private TextArea txtMinimumInvestment;

    @FXML
    private TextArea txtInvestmentPlans;

    @FXML
    private Button btnCalculatereturn;

    @FXML
    private Button btnInvest;

    @FXML
    private Button btnBack;

    // Method for Calculate Return button
    @FXML
    private void handleCalculatereturn() {
        // Example logic
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Calculate Return");
        alert.setHeaderText(null);
        alert.setContentText("Calculating your return based on your investment...");
        alert.showAndWait();
    }

    // Method for Invest button
    @FXML
    private void handleInvest() {
        // Example logic
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Invest");
        alert.setHeaderText(null);
        alert.setContentText("Investment successful!");
        alert.showAndWait();
    }

    // Method for Back button
    @FXML
    private void handleBack() {
        // Example logic
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Back");
        alert.setHeaderText(null);
        alert.setContentText("Going back to the previous screen...");
        alert.showAndWait();
        // You can add actual scene-switching logic here
    }
}

