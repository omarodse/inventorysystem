package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartFormController extends MainFormController implements Initializable {
    public RadioButton inHouseRadioButton;
    public RadioButton outSourcedRadioButton;
    public Label machineCompany;
    public Button cancelButton;
    public ToggleGroup inout;
    public Button saveAddPart;
    public TextField addPartNameField;
    public TextField addPartInvField;
    public TextField addPartPriceField;
    public TextField addPartMaxField;
    public TextField addPartMachineCompany;
    public TextField addPartMinField;
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

    public void onSaveAddPart(ActionEvent actionEvent) throws IOException {
        try {
            int partId = generateUniqueId();
            String partName = addPartNameField.getText();
            int inv = Integer.parseInt(addPartInvField.getText());
            double price = Double.parseDouble(addPartPriceField.getText());
            int max = Integer.parseInt(addPartMaxField.getText());
            int min = Integer.parseInt(addPartMinField.getText());
            int machineId;
            String companyName;

            // check invalid inputs
            if (!checkValues(min, max, inv)) {
                return; // Exit the calling method
            }

            // check if InHouse or OutSourced to change to Machine ID or Company Name
            if(inHouseRadioButton.isSelected()) {
                machineId = Integer.parseInt(addPartMachineCompany.getText());
                InHouse newPart = new InHouse(partId, partName, price, inv, min, max, machineId);
                Inventory.addPart(newPart);
            } else {
                companyName = addPartMachineCompany.getText();
                OutSourced newPart = new OutSourced(partId, partName, price, inv, min, max, companyName);
                    }

            } catch(NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid Input");
                alert.showAndWait();
                return;
        }

        // Go back to the main screen
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Part Form", cancelButton, 1000, 379);

    }
}