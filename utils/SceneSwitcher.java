package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static void switchTo(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlPath));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.out.println("❌ ERROR loading: " + fxmlPath);
            e.printStackTrace();
        }
    }

    // For controllers that need initialization (Dashboard, ManageAccount…)
    public static FXMLLoader switchToWithLoader(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlPath));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();

            return loader;

        } catch (IOException e) {
            System.out.println("❌ ERROR loading (with loader): " + fxmlPath);
            e.printStackTrace();
            return null;
        }
    }
}
