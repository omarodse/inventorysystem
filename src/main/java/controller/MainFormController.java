package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    @FXML
    private Button addPartButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onAddPartButton(ActionEvent actionEvent) throws IOException {
        URL fxmlLocation = getClass().getResource("/wgu/inventoryfxmlapp/AddPartForm.fxml");
        if (fxmlLocation == null) {
            // Log an error or show an alert to the user
            System.err.println("FXML file not found");
            return;
        }

        Parent root = FXMLLoader.load(fxmlLocation);
        Stage stage = (Stage) addPartButton.getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }
}