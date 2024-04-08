package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

public class MainFormController implements Initializable {
    public Button modifyPartButton;
    public TableView<Part> allPartsTable;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn inventoryLevel;
    public TableColumn partPriceCost;
    public TableView<Product> allProductsTable;
    public TableColumn productId;
    public TableColumn productName;
    public TableColumn productInventoryLevel;
    public TableColumn productPriceCost;
    public Button deletePartButton;
    public Button searchPartButton;
    public Button searchProduct;
    public TextField partTextField;
    public Button addProductButton;
    public Button modifyProductButton;
    public Button deleteProduct;
    public TextField productTextField;
    public Button exitButton;
    private final static Random random = new Random();
    private final static Set<Integer> usedIds = new HashSet<>();
    @FXML
    private Button addPartButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allPartsTable.setItems(Inventory.getAllParts());
        allProductsTable.setItems(Inventory.getAllProducts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCost.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    //Helper method to parse the FXML file and stage the window
    public static void getWindow(String source, String title, Button button, int width, int height) throws IOException {
        URL fxmlLocation = MainFormController.class.getResource(source);
        if (fxmlLocation == null) {
            // Log an error or show an alert to the user
            System.err.println("FXML file not found");
            return;
        }

        Parent root = FXMLLoader.load(fxmlLocation);
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(root, width, height);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    //overload getWindow to pass a Part
    public static void getWindow(String source, String title, Button button, int width, int height, Part selectedItem) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFormController.class.getResource(source));
        Parent root = loader.load();

        if (selectedItem != null) {
            Object controller = loader.getController();
            if (controller instanceof ModifyPartFormController) {
                ((ModifyPartFormController) controller).populateForm(selectedItem);
            }
        }

        Stage stage = (Stage) button.getScene().getWindow();
        stage.setTitle(title);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    //overload getWindow to pass a Product
    public static void getWindow(String source, String title, Button button, int width, int height, Product selectedItem) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFormController.class.getResource(source));
        Parent root = loader.load();

        if (selectedItem != null) {
            System.out.println("This is working");
            Object controller = loader.getController();
            if (controller instanceof ModifyProductFormController) {
                ((ModifyProductFormController) controller).populateForm(selectedItem);
            }
        }

        Stage stage = (Stage) button.getScene().getWindow();
        stage.setTitle(title);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    public static int generateUniqueId() {
        while (true) {
            int id = 100 + random.nextInt(900);
            if (!usedIds.contains(id)) {
                usedIds.add(id);
                return id;
            }
        }
    }

    private static void showAlert(Alert.AlertType alertType, String content) {
        Alert alert = new Alert(alertType, content);
        alert.showAndWait();
    }

    // Method to check invalid inputs
    public static boolean checkValues(int min, int max, int inStock) {
        if (max < min) {
            showAlert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
            return false; // Indicates failure
        }
        if (inStock < min || max < inStock) {
            showAlert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
            return false; // Indicates failure
        }
        return true; // Indicates success
    }

    //method to get the current index
    public static int getPartIndex(int partId) {
        int index = 0;

        ObservableList<Part> parts = Inventory.getAllParts();
        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i).getId() == partId) {
                return i; // Return the index of the found part
            }
        }
        return -1; // Return -1 if not found
    }

    //method to get the current index
    public static int getProductIndex(int productId) {
        int index = 0;

        ObservableList<Product> products = Inventory.getAllProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == productId) {
                return i; // Return the index of the found part
            }
        }
        return -1; // Return -1 if not found
    }
    public void onAddPartButton(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/AddPartForm.fxml", "Add Part Form", addPartButton, 600, 600);
    }

    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        Part selectedItem = allPartsTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            getWindow("/wgu/inventoryfxmlapp/ModifyPartForm.fxml", "Modify Part Form", modifyPartButton, 600, 600, selectedItem);
        } else if(allPartsTable.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No parts in inventory");
            alert.setContentText("Please add parts");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"No item selected");
            alert.showAndWait();
        }
    }

    public void onDeletePart(ActionEvent actionEvent) {
        Part selectedItem = allPartsTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected item?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Inventory.deletePart(selectedItem);
            }
        } else if(allPartsTable.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No parts in inventory");
            alert.setContentText("Please add parts");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"No item selected");
            alert.showAndWait();
        }
    }

    public void onSearchPart(ActionEvent actionEvent) {
        String partName = partTextField.getText();
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
        partTextField.setText("");
    }

    public void onSearchProduct(ActionEvent actionEvent) {
        String productName = productTextField.getText();
        ObservableList<Product> products = Inventory.lookupProduct(productName);

        if(products.size() == 0) {
            try {
                int id = Integer.parseInt(productName);
                Product product = Inventory.lookupProduct(id);
                if (product != null) {
                    products.add(product);
                } else {
                    allProductsTable.setItems(products);
                    allProductsTable.setPlaceholder(new Label("No product found"));
                    return;
                }
            }
            catch(NumberFormatException e) {
                allProductsTable.setItems(products);
                allProductsTable.setPlaceholder(new Label("No product found"));
            }
        }

        allProductsTable.setItems(products);
        productTextField.setText("");
    }

    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/AddProductForm.fxml", "Add Product Form", addProductButton, 1000, 600);
    }

    public void onDeleteProduct(ActionEvent actionEvent) {
        Product selectedItem = allProductsTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            ObservableList<Part> associatedParts = selectedItem.getAllAssociatedParts();
            System.out.println(associatedParts);
            if(associatedParts.size() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Product has associated parts");
                alert.setContentText("Please remove the parts before deleting the product");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected item?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                Inventory.deleteProduct(selectedItem);
            }
        } else if(allProductsTable.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No products in inventory");
            alert.setContentText("Please add products");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"No item selected");
            alert.showAndWait();
        }
    }

    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        Product selectedItem = allProductsTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            getWindow("/wgu/inventoryfxmlapp/ModifyProductForm.fxml", "Modify Product Form", modifyProductButton, 1000, 600, selectedItem);
        } else if(allProductsTable.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No products in inventory");
            alert.setContentText("Please add products");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"No item selected");
            alert.showAndWait();
        }
    }

    public void onExitButton(ActionEvent actionEvent) {
        Platform.exit();
    }
}
