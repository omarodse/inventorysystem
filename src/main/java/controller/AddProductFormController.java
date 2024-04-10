package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.util.ResourceBundle;

import static controller.MainFormController.*;

/**
 * Controller class for the Add Product Form in a JavaFX application.
 * <p>
 * Handles all user interactions within the Add Product form, enabling the creation
 * and addition of new products to the inventory, along with their associated parts.
 */
public class AddProductFormController implements Initializable {
    public Button cancelAddProduct;
    public Button saveAddProduct;
    public TableView<Part> allPartsTable;
    public TableColumn<Part, Integer> partId;
    public TableColumn<Part, String> partName;
    public TableColumn<Part, Integer> inventoryLevel;
    public TableColumn<Part, Double> partPriceCost;
    public Button addAssociatedPartButton;
    public TextField addProductFormSearch;
    public TableView<Part> associatedPartsTable;
    public TableColumn<Part, Integer> associatedPartId;
    public TableColumn<Part, String> associatedPartName;
    public TableColumn<Part, Integer> associatedInventory;
    public TableColumn<Part, Double> associatedPrice;
    public TextField addProductId;
    public TextField addProductName;
    public TextField addProductInventory;
    public TextField addProductPrice;
    public TextField addProductMax;
    public TextField addProductMin;
    public Button removeAssociatedPartButton;
    @FXML
    private ObservableList<Part> bottomTable = FXCollections.observableArrayList();

    /**
     * Initializes the controller and sets up the table columns and their cell value factories.
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
    }

    /**
     * Handles cancel button action to return to the main form without saving the product.
     *
     * @param actionEvent The event that triggered the action.
     * @throws IOException If an I/O error occurs during loading the main form.
     */
    public void onCancelAddProduct(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Product Form", cancelAddProduct);
    }

    /**
     * Handles saving the new product and its associated parts to the inventory.
     *
     * @param actionEvent The event that triggered the action.
     * @throws IOException If an I/O error occurs.
     */
    public void onSaveAddProduct(ActionEvent actionEvent) throws IOException {
        try {
            int productId = generateUniqueId();
            String productName = addProductName.getText();
            int inv = Integer.parseInt(addProductInventory.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());

            // check invalid inputs
            if (!checkValues(min, max, inv, price)) {
                return; // Exit the calling method
            }

            Product newProduct = new Product(productId, productName, price, inv, min, max);

            // add associated parts to the new products
            for (Part part : bottomTable) {
                newProduct.addAssociatedPart(part);
            }

            // add the new product to the inventory
            Inventory.addProduct(newProduct);
            Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
            confirmationAlert.setTitle("Product added");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("The product has been successfully added.");
            confirmationAlert.showAndWait();

            // clear the bottom table
            bottomTable.clear();

            // go back to the main screen
            getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Main Form", saveAddProduct);

        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid Input");
            alert.showAndWait();
        }
    }

    /**
     * Adds the selected part from the all parts table to the associated parts of the new product.
     *
     * @param actionEvent The event that triggered the action.
     */
    public void onAddAssociatedPart(ActionEvent actionEvent) {
        Part selectedItem = allPartsTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            bottomTable.add(selectedItem);
            associatedPartsTable.setItems(bottomTable);

            associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
            associatedInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
            associatedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No part selected");
            alert.setContentText("Please select a part and try again");
            alert.showAndWait();
        }
    }

    /**
     * Searches for parts based on the input in the search field when enter key is pressed.
     *
     * @param keyEvent The key event that triggered the action.
     */
    public void onProductFormSearch(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) {
            String partName = addProductFormSearch.getText();
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
            addProductFormSearch.setText("");
        }
    }

    /**
     * Removes the selected part from the associated parts of the new product.
     *
     * @param actionEvent The event that triggered the action.
     */
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Part selectedItem = associatedPartsTable.getSelectionModel().getSelectedItem();
        bottomTable.remove(selectedItem);
    }
}