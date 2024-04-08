package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static controller.MainFormController.*;
import static model.Inventory.lookupProduct;

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
    private ObservableList<Part> bottomTable = FXCollections.observableArrayList();
    private ArrayList<Part> addedParts = new ArrayList<>();

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

    // overloading populateForm to accept a Product
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

    public void onCancelModifyProduct(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Add Part Form", cancelModifyProduct, 1000, 379);
    }

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

    public void onModifyProductRemove(ActionEvent actionEvent) {
        addedParts.clear();
        Part selectedItem = associatedPartsTable.getSelectionModel().getSelectedItem();

        if(selectedItem != null) { // the user is removing from the table only, not the object
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                bottomTable.remove(selectedItem);
                int productId = Integer.parseInt(modifyProductId.getText());
                Product product = lookupProduct(productId);
                product.deletedAssociatedPart(selectedItem);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"No item selected");
            alert.showAndWait();
        }
    }

    public void onModifyProductSave(ActionEvent actionEvent) throws IOException {
        try {
            int productId = Integer.parseInt(modifyProductId.getText());
            String productName = modifyProductName.getText();
            int inv = Integer.parseInt(modifyProductInventory.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());

            // check invalid inputs
            if (!checkValues(min, max, inv)) {
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
            } else {
                getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Main Form", modifyProductSave, 1000, 379);
                return;
            }

            addedParts.clear();

            getWindow("/wgu/inventoryfxmlapp/MainForm.fxml", "Main Form", modifyProductSave, 1000, 379);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Input");
            alert.showAndWait();
        }
    }
}