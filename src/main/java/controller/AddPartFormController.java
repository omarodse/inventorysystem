package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartFormController extends MainFormController implements Initializable {
    public RadioButton inHouseRadioButton;
    public RadioButton outSourcedRadioButton;
    public Label machineCompany;
    public Button cancelButton;
    public ToggleGroup inout;
    @FXML
    private Label welcomeText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onInHouse(ActionEvent actionEvent) {
        machineCompany.setText("Machine ID");
    }

    public void onOutSourced(ActionEvent actionEvent) {
        machineCompany.setText("Company Name");
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Part Form", cancelButton, 1000, 379);
    }
}