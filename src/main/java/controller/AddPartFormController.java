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

/**
 * Controller for the Add Part Form UI.
 * <p>
 * This class handles the user interactions with the Add Part Form, allowing users
 * to input data for new parts, either InHouse or OutSourced, and adding them to the inventory.
 */
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

    /**
     * Initializes the controller class.
     * <p>
     * This method is automatically called after the FXML file has been loaded. It can be used
     * to perform initializations such as setting event handlers or configuring UI controls.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if unknown.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Handles the action event when the InHouse radio button is selected.
     * <p>
     * Changes the label to "Machine ID" to indicate the expected input for InHouse parts.
     *
     * @param actionEvent The event triggered when the InHouse radio button is selected.
     */
    public void onInHouse(ActionEvent actionEvent) {
        machineCompany.setText("Machine ID");
    }

    /**
     * Handles the action event when the OutSourced radio button is selected.
     * <p>
     * Changes the label to "Company Name" to indicate the expected input for OutSourced parts.
     *
     * @param actionEvent The event triggered when the OutSourced radio button is selected.
     */
    public void onOutSourced(ActionEvent actionEvent) {
        machineCompany.setText("Company Name");
    }

    /**
     * Handles the cancel action.
     * <p>
     * When the cancel button is pressed, the UI transitions back to the Main Form without adding a new part.
     *
     * @param actionEvent The event triggered when the cancel button is pressed.
     * @throws IOException if the Main Form FXML file cannot be loaded.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Part Form", cancelButton);
    }

    /**
     * Handles saving a new part to the inventory.
     * <p>
     * Validates user input and adds a new InHouse or OutSourced part to the inventory based on
     * the provided information. Displays confirmation or error messages as appropriate.
     *
     * @param actionEvent The event triggered when the save button is pressed.
     * @throws IOException if there is an issue saving the part.
     */
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