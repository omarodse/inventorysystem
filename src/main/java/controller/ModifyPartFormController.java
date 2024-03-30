package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartFormController extends MainFormController implements Initializable {
    public RadioButton inHouseRadioButton;
    public RadioButton outSourcedRadioButton;
    public Label machineCompany;
    public Button modifyPartCancelButton;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onInHouse(ActionEvent actionEvent) {
        machineCompany.setText("Machine ID");
    }

    public void onOutSourced(ActionEvent actionEvent) {
        machineCompany.setText("Company Name");
    }

    public void onModifyPartCancel(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Part Form", modifyPartCancelButton, 1000, 379);
    }
}