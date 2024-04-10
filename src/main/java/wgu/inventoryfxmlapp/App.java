package wgu.inventoryfxmlapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class that launches the inventory management system.
 * <p>
 * This class is responsible for initializing and displaying the primary stage and scene,
 * serving as the entry point for the JavaFX application.
 *<p>
 * FUTURE ENHANCEMENT: Implement user authentication and authorization functionality.
 * This would involve adding user login screens and user management features,
 * allowing administrators to create, modify, and delete user accounts with different levels of access rights.
 */
public class App extends Application {
    /**
     * Starts the primary stage for this application.
     * <p>
     * This method loads the main application scene from an FXML file and sets it on the primary stage.
     *
     * @param stage The primary stage for this application, onto which the application scene is set.
     * @throws IOException If loading the FXML resource fails.
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * <p>
     * The JavaDocs Folder is located in src/main/java
     * <p>
     * @param args the command line arguments
     * <p>
     */
    public static void main(String[] args) {
        launch();
    }
}