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

public class AddProductFormController implements Initializable {
    public Button cancelAddProduct;
    public Button saveAddProduct;
    public TableView<Part> allPartsTable;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn inventoryLevel;
    public TableColumn partPriceCost;
    public Button addAssociatedPartButton;
    public TextField addProductFormSearch;
    public TableView<Part> associatedPartsTable;
    public TableColumn associatedPartId;
    public TableColumn associatedPartName;
    public TableColumn associatedInventory;
    public TableColumn associatedPrice;
    public TextField addProductId;
    public TextField addProductName;
    public TextField addProductInventory;
    public TextField addProductPrice;
    public TextField addProductMax;
    public TextField addProductMin;
    public Button removeAssociatedPartButton;
    @FXML
    private ObservableList<Part> bottomTable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        allPartsTable.setItems(Inventory.getAllParts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void onCancelAddProduct(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Part Form", cancelAddProduct, 1000, 379);
    }

    public void onSaveAddProduct(ActionEvent actionEvent) throws IOException {
        try {
            int productId = generateUniqueId();
            String productName = addProductName.getText();
            int inv = Integer.parseInt(addProductInventory.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());

            // check invalid inputs
            if (!checkValues(min, max, inv)) {
                return; // Exit the calling method
            }

            Product newProduct = new Product(productId, productName, price, inv, min, max);

            // add associated parts to the new products
            for (Part part : bottomTable) {
                newProduct.addAssociatedPart(part);
            }

            // add the new product to the inventory
            Inventory.addProduct(newProduct);

            // clear the bottom table
            bottomTable.clear();

            // go back to the main screen
            getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Main Form", saveAddProduct, 1000, 379);

        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid Input");
            alert.showAndWait();
        }
    }

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

    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Part selectedItem = associatedPartsTable.getSelectionModel().getSelectedItem();
        bottomTable.remove(selectedItem);
    }
}