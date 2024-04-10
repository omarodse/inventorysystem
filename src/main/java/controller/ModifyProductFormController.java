package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static controller.MainFormController.*;

/**
 * Controller for the Modify Product Form.
 * <p>
 * Handles interactions within the Modify Product form, allowing users to update details
 * for existing products and manage their associated parts.
 */
public class ModifyProductFormController implements Initializable {
    public Button cancelModifyProduct;
    public TableView<Part> allPartsTable;
    public TableColumn<Part, Integer> partId;
    public TableColumn<Part, String> partName;
    public TableColumn<Part, Integer> inventoryLevel;
    public TableColumn<Part, Double> partPriceCost;
    public TableView<Part> associatedPartsTable;
    public TableColumn<Part, Integer> associatedPartId;
    public TableColumn<Part, String> associatedPartName;
    public TableColumn<Part, Integer> associatedInventory;
    public TableColumn<Part, Integer> associatedPrice;
    public TextField modifyProductId;
    public TextField modifyProductName;
    public TextField modifyProductInventory;
    public TextField modifyProductPrice;
    public TextField modifyProductMax;
    public TextField modifyProductMin;
    public Button modifyProductAddPart;
    public Button modifyProductRemove;
    public Button modifyProductSave;
    public TextField modifyProductSearch;
    private ObservableList<Part> bottomTable = FXCollections.observableArrayList();
    private ArrayList<Part> addedParts = new ArrayList<>();
    private ArrayList<Part> removedParts = new ArrayList<>();

    /**
     * Initializes the controller class. This method is called after the FXML file has been loaded.
     * It sets up the table columns and their cell value factories for displaying parts and associated parts.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allPartsTable.setItems(Inventory.getAllParts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(bottomTable);

        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Populates the form with the product's current details for modification.
     * Adjusts the view to display the product's associated parts in the table.
     *
     * @param product The product to be modified.
     */
    public void populateForm(Product product) {

        modifyProductId.setText(String.valueOf(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductInventory.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMax.setText(String.valueOf(product.getMax()));
        modifyProductMin.setText(String.valueOf(product.getMin()));

        bottomTable.clear();
        bottomTable.addAll(product.getAllAssociatedParts());

    }

    /**
     * Handles the event triggered by the Cancel button click.
     * Transitions the user back to the Main Form without saving any changes.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs while loading the Main Form.
     */
    public void onCancelModifyProduct(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Part Form", cancelModifyProduct);
    }

    /**
     * Adds the selected part from the all parts table to the list of associated parts for the product being modified.
     *
     * @param actionEvent The event that triggered this action.
     */
    public void onModifyProductAdd(ActionEvent actionEvent) {
        Part selectedItem = allPartsTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            bottomTable.add(selectedItem);
            addedParts.add(selectedItem);

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No part selected");
            alert.setContentText("Please select a part and try again");
            alert.showAndWait();
        }
    }

    /**
     * Removes the selected part from the list of associated parts for the product being modified.
     *<P>
     * RUNTIME ERROR/LOGICAL ERROR: Initially, the method was erroneously removing the part from both the inventory and the
     * table instead of solely from the table. Consequently, the part was removed from the inventory regardless
     * of whether the operation was saved or cancelled.
     *
     * SOLUTION: I addressed this issue by eliminating the code responsible for deleting the part from the inventory.
     * Instead, I introduced an removedParts ArrayList to be managed by the Save operation.
     *
     * @param actionEvent The event that triggered this action.
     */
    public void onModifyProductRemove(ActionEvent actionEvent) {
        addedParts.clear();
        Part selectedItem = associatedPartsTable.getSelectionModel().getSelectedItem();

        if(selectedItem != null) { // the user is removing from the table only, not the object
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                bottomTable.remove(selectedItem);
                removedParts.add(selectedItem);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"No item selected");
            alert.showAndWait();
        }
    }

    /**
     * Saves the modifications made to the product and its list of associated parts.
     * Validates the input fields and updates the product in the inventory.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs during the process.
     */
    public void onModifyProductSave(ActionEvent actionEvent) throws IOException {
        try {
            int productId = Integer.parseInt(modifyProductId.getText());
            String productName = modifyProductName.getText();
            int inv = Integer.parseInt(modifyProductInventory.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());

            // check invalid inputs
            if (!checkValues(min, max, inv, price)) {
                return; // Exit if validation fails
            }

            Product existingProduct = Inventory.lookupProduct(productId);

            // Updating fields
            existingProduct.setName(productName);
            existingProduct.setPrice(price);
            existingProduct.setStock(inv);
            existingProduct.setMax(max);
            existingProduct.setMin(min);

            if(addedParts.size() > 0) {
                for (Part part : addedParts) {
                    existingProduct.addAssociatedPart(part);
                }
            }
            if(removedParts.size() > 0) {
                for (Part part : removedParts) {
                    existingProduct.deleteAssociatedPart(part);
                }
            } else {
                getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Main Form", modifyProductSave);
                return;
            }

            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("Product updated");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("The product has been successfully updated.");
            confirmationAlert.showAndWait();

            addedParts.clear();
            removedParts.clear();

            getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Main Form", modifyProductSave);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Input");
            alert.showAndWait();
        }
    }

    /**
     * Searches for parts based on the input in the search field when the Enter key is pressed.
     * Updates the all parts table to display only parts that match the search criteria.
     *
     * @param keyEvent The key event that triggered this action.
     */
    public void modifyProductSearch(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            String partName = modifyProductSearch.getText();
            ObservableList<Part> parts = Inventory.lookupPart(partName);

            if(parts.size() == 0) {
                try {
                    int id = Integer.parseInt(partName);
                    Part part = Inventory.lookupPart(id);
                    if (part != null) {
                        parts.add(part);
                    } else {
                        allPartsTable.setItems(parts);
                        allPartsTable.setPlaceholder(new Label("No part found"));
                        return;
                    }
                }
                catch(NumberFormatException e) {
                    allPartsTable.setItems(parts);
                    allPartsTable.setPlaceholder(new Label("No part found"));
                }
            }

            allPartsTable.setItems(parts);
            modifyProductSearch.setText("");
        }
    }
}