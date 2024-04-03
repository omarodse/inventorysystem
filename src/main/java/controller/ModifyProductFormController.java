package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainFormController.getWindow;

public class ModifyProductFormController implements Initializable {
    public Button cancelModifyProduct;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onCancelModifyProduct(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Part Form", cancelModifyProduct, 1000, 379);
    }
}