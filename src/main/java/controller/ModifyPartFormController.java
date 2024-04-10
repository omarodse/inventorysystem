package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Modify Part Form.
 * <p>
 * Manages the interaction between the user and the Modify Part Form UI, allowing
 * users to modify details of existing parts in the inventory. Supports modification
 * of both InHouse and OutSourced part types.
 */
public class ModifyPartFormController extends MainFormController implements Initializable {
    public RadioButton inHouseRadioButton;
    public RadioButton outSourcedRadioButton;
    public Label machineCompany;
    public Button modifyPartCancelButton;
    public TextField modifyId;
    public TextField modifyName;
    public TextField modifyInv;
    public TextField modifyPriceCost;
    public TextField modifyMax;
    public TextField ModifyMachineCompany;
    public TextField modifyMin;
    public Button modifyPartSave;
    @FXML
    private Label welcomeText;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Handles the selection of the InHouse radio button, updating the UI to reflect
     * the InHouse part type specifics.
     *
     * @param actionEvent The event that triggered this action.
     */
    public void onInHouse(ActionEvent actionEvent) {
        machineCompany.setText("Machine ID");
    }

    /**
     * Handles the selection of the OutSourced radio button, updating the UI to reflect
     * the OutSourced part type specifics.
     *
     * @param actionEvent The event that triggered this action.
     */
    public void onOutSourced(ActionEvent actionEvent) {
        machineCompany.setText("Company Name");
    }

    /**
     * Populates the form fields with the data from the part being modified.
     * Adjusts the form to show fields relevant to the type of the part.
     *
     * @param part The part whose data is to be loaded into the form fields.
     */
    public void populateForm(Part part) {
        if(part instanceof OutSourced) {
            outSourcedRadioButton.setSelected(true);
            machineCompany.setText("Company Name");
            ModifyMachineCompany.setText(((OutSourced) part).getCompanyName());
        } else {
            ModifyMachineCompany.setText(String.valueOf(((InHouse) part).getMachineId()));
        }

        modifyId.setText(String.valueOf(part.getId()));
        modifyName.setText(part.getName());
        modifyInv.setText(String.valueOf(part.getStock()));
        modifyPriceCost.setText(String.valueOf(part.getPrice()));
        modifyMax.setText(String.valueOf(part.getMax()));
        modifyMin.setText(String.valueOf(part.getMin()));

    }

    /**
     * Handles the action of cancelling part modification. Returns to the main screen
     * without saving any changes.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs while loading the main form.
     */
    public void onModifyPartCancel(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Part Form", modifyPartCancelButton);
    }

    /**
     * Handles the action of saving the modified part. Validates the input fields and
     * updates the part in the inventory with the new details. Then returns to the main
     * screen.
     *
     * @param actionEvent The event that triggered this action.
     */
    public void onModifyPartSave(ActionEvent actionEvent) {
        try {
            int partId = Integer.parseInt(modifyId.getText());
            String partName = modifyName.getText();
            int inv = Integer.parseInt(modifyInv.getText());
            double price = Double.parseDouble(modifyPriceCost.getText());
            int max = Integer.parseInt(modifyMax.getText());
            int min = Integer.parseInt(modifyMin.getText());

            // get the current index
            int currentIndex = getPartIndex(partId);

            // check invalid inputs
            if (!checkValues(min, max, inv, price)) {
                return; // Exit the calling method
            }

            Part existingPart = Inventory.lookupPart(partId);
            Part newPart = null;

            // Check if the type has changed and update accordingly
            if (existingPart instanceof InHouse && outSourcedRadioButton.isSelected()) {
                // Type changed to OutSourced
                String companyName = ModifyMachineCompany.getText();
                newPart = new OutSourced(partId, partName, price, inv, min, max, companyName);
            } else if (existingPart instanceof OutSourced && inHouseRadioButton.isSelected()) {
                // Type changed to InHouse
                int machineId = Integer.parseInt(ModifyMachineCompany.getText());
                newPart = new InHouse(partId, partName, price, inv, min, max, machineId);
            } else {
                // Type hasn't changed, but fields might have
                existingPart.setName(partName);
                existingPart.setPrice(price);
                existingPart.setStock(inv);
                existingPart.setMax(max);
                existingPart.setMin(min);
                if (existingPart instanceof InHouse) {
                    int machineId = Integer.parseInt(ModifyMachineCompany.getText());
                    ((InHouse) existingPart).setMachineId(machineId);
                } else if (existingPart instanceof OutSourced) {
                    String companyName = ModifyMachineCompany.getText();
                    ((OutSourced) existingPart).setCompanyName(companyName);
                }
                newPart = existingPart; // No type change, update existing part
            }

            // Replace or update the part in Inventory
            Inventory.updatePart(currentIndex, newPart);

            // Confirmation alert
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("Update Successful");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("The part has been successfully updated.");
            confirmationAlert.showAndWait();

            // Go back to the main screen
            getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Save Part", modifyPartSave);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid Input");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}