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
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Part Form", cancelButton);
    }

    public void onSaveAddPart(ActionEvent actionEvent) throws IOException {
        try {
            int partId = generateUniqueId();
            String partName = addPartNameField.getText();
            int inv = Integer.parseInt(addPartInvField.getText());
            double price = Double.parseDouble(addPartPriceField.getText());
            int max = Integer.parseInt(addPartMaxField.getText());
            int min = Integer.parseInt(addPartMinField.getText());

            // check invalid inputs
            if (!checkValues(min, max, inv, price)) {
                return; // Exit the calling method
            }

            Part newPart = null;
            if (inHouseRadioButton.isSelected()) {
                int machineId = Integer.parseInt(addPartMachineCompany.getText());
                newPart = new InHouse(partId, partName, price, inv, min, max, machineId);
            } else if (outSourcedRadioButton.isSelected()) {
                String companyName = addPartMachineCompany.getText();
                newPart = new OutSourced(partId, partName, price, inv, min, max, companyName);
            }

            Inventory.addPart(newPart);
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("Part added");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("The part has been successfully added.");
            confirmationAlert.showAndWait();

            // Go back to the main screen
            getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Main Form", saveAddPart);

        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid Input");
            alert.showAndWait();
        }

    }
}