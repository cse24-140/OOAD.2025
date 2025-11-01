import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Starting Orange Bank Application...");

            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            // Set up the stage
            primaryStage.setTitle("Orange Bank Of Botswana");
            primaryStage.setScene(new Scene(root, 520, 850)); // Increased height to 850
            primaryStage.setResizable(false);
            primaryStage.show();

            System.out.println("Application started successfully!");

        } catch (Exception e) {
            System.err.println("Failed to start application!");
            e.printStackTrace();
            showErrorDialog("FXML Loading Error",
                    "Cannot load Login.fxml. Make sure the file exists in the correct location.\n\n" +
                            "Error: " + e.getMessage());
        }
    }

    private void showErrorDialog(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        System.out.println("Launching Orange Bank Application...");
        launch(args);
    }
}