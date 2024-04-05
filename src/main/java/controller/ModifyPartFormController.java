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

    public void populateForm(Part part) {
        boolean outSourced = false;
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

    public void onModifyPartCancel(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Part Form", modifyPartCancelButton, 1000, 379);
    }

    public void onModifyPartSave(ActionEvent actionEvent) throws IOException {
        try {
            int partId = Integer.parseInt(modifyId.getText());
            String partName = modifyName.getText();
            int inv = Integer.parseInt(modifyInv.getText());
            double price = Double.parseDouble(modifyPriceCost.getText());
            int max = Integer.parseInt(modifyMax.getText());
            int min = Integer.parseInt(modifyMin.getText());
            int machineId;
            String companyName;

            // get the current index
            int currentIndex = getIndex(partId);

            // check invalid inputs
            if (!checkValues(min, max, inv)) {
                return; // Exit the calling method
            }

            // check if InHouse or OutSourced to change to Machine ID or Company Name
            if(inHouseRadioButton.isSelected()) {
                machineId = Integer.parseInt(ModifyMachineCompany.getText());
                InHouse newPart = new InHouse(partId, partName, price, inv, min, max, machineId);
                Inventory.updatePart(currentIndex, newPart);
            } else {
                companyName = ModifyMachineCompany.getText();
                OutSourced newPart = new OutSourced(partId, partName, price, inv, min, max, companyName);
                Inventory.updatePart(currentIndex, newPart);
            }

        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid Input");
            alert.showAndWait();
            return;
        }

        // Go back to the main screen
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Save Part", modifyPartSave, 1000, 379);

    }
}