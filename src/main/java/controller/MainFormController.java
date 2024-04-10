package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Controller for the main form of the application.
 * <p>
 * This class is responsible for handling all user interactions on the main form,
 * including navigating to add or modify parts/products, deleting parts/products,
 * and searching through parts/products.
 */
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

    /**
     * Initializes the controller, setting up the table columns and their cell value factories.
     *
     * @param url The location used to resolve relative paths for the root object, or null if unknown.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
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

    /**
     * Helper method to load and display a new window based on the FXML file specified.
     *
     * @param source The path to the FXML file.
     * @param title The title of the window.
     * @param button A button from the current window to get the stage.
     * @throws IOException If loading the FXML file fails.
     */
    public static void getWindow(String source, String title, Button button) throws IOException {
        URL fxmlLocation = MainFormController.class.getResource(source);
        if (fxmlLocation == null) {
            // Log an error or show an alert to the user
            System.err.println("FXML file not found");
            return;
        }

        Parent root = FXMLLoader.load(fxmlLocation);
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Helper method to load and display a new window based on the FXML file specified.
     *
     * @param source The path to the FXML file.
     * @param title The title of the window.
     * @param button A button from the current window to get the stage.
     * @param selectedItem A Part selected from a table
     * @throws IOException If loading the FXML file fails.
     */
    public static void getWindow(String source, String title, Button button, Part selectedItem) throws IOException {
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
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Helper method to load and display a new window based on the FXML file specified.
     *
     * @param source The path to the FXML file.
     * @param title The title of the window.
     * @param button A button from the current window to get the stage.
     * @param selectedItem A Product selected from a table
     * @throws IOException If loading the FXML file fails.
     */
    public static void getWindow(String source, String title, Button button, Product selectedItem) throws IOException {
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
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Generates a unique ID for parts and products.
     *
     * @return An integer representing a unique ID.
     */
    public static int generateUniqueId() {
        while (true) {
            int id = 100 + random.nextInt(900);
            if (!usedIds.contains(id)) {
                usedIds.add(id);
                return id;
            }
        }
    }

    /**
     * Displays an alert dialog with the specified content.
     *
     * @param alertType The type of alert.
     * @param content The content message of the alert.
     */
    private static void showAlert(Alert.AlertType alertType, String content) {
        Alert alert = new Alert(alertType, content);
        alert.showAndWait();
    }

    /**
     * Validates input values for parts and products.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @param inStock The stock level.
     * @param price The price.
     * @return true if the values are valid, false otherwise.
     */
    public static boolean checkValues(int min, int max, int inStock, Double price) {
        if(min < 0 || max < 0 || inStock < 0 || price < 0){
            showAlert(Alert.AlertType.ERROR, "Negative numbers are not accepted");
            return false;
        }
        if (max < min) {
            showAlert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
            return false;
        }
        if (inStock < min || max < inStock) {
            showAlert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
            return false;
        }
        return true;
    }

    /**
     * Gets the product's index.
     *
     * @param partId Part's ID
     * @return Part's index, -1 if not found
     */
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

    /**
     * Gets the product's index.
     *
     * @param productId Products's ID
     * @return Product's index, -1 if not found
     */
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

    /**
     * Handles the event when the Add Part button is clicked.
     * <p>
     * This method transitions the user to the Add Part form.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs during the transition.
     */
    public void onAddPartButton(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/AddPartForm.fxml", "Add Part Form", addPartButton);
    }

    /**
     * Handles the event when the Modify Part button is clicked.
     * <p>
     * Transitions the user to the Modify Part form for the selected part.
     * Displays a warning if no part is selected.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs during the transition.
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        Part selectedItem = allPartsTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            getWindow("/wgu/inventoryfxmlapp/ModifyPartForm.fxml", "Modify Part Form", modifyPartButton, selectedItem);
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

    /**
     * Handles the event when the Delete Part button is clicked.
     * <p>
     * Prompts the user for confirmation before deleting the selected part from the inventory.
     *
     * @param actionEvent The event that triggered this action.
     */
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

    /**
     * Handles part search functionality.
     * <p>
     * Searches for parts based on the given input in the part search text field.
     *
     * @param actionEvent The event that triggered this action.
     */
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

    /**
     * Handles product search functionality.
     * <p>
     * Searches for products based on the given input in the product search text field.
     *
     * @param actionEvent The event that triggered this action.
     */
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

    /**
     * Handles the event when the Add Product button is clicked.
     * <p>
     * Transitions the user to the Add Product form.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs during the transition.
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/AddProductForm.fxml", "Add Product Form", addProductButton);
    }

    /**
     * Handles the event when the Delete Product button is clicked.
     * <p>
     * Prompts the user for confirmation before deleting the selected product from the inventory.
     *
     * @param actionEvent The event that triggered this action.
     */
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

    /**
     * Handles the event when the Modify Product button is clicked.
     * <p>
     * Transitions the user to the Modify Product form for the selected product.
     * Displays a warning if no product is selected.
     *
     * @param actionEvent The event that triggered this action.
     * @throws IOException If an error occurs during the transition.
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        Product selectedItem = allProductsTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            getWindow("/wgu/inventoryfxmlapp/ModifyProductForm.fxml", "Modify Product Form", modifyProductButton, selectedItem);
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

    /**
     * Handles the application exit.
     * <p>
     * Closes the application when the Exit button is clicked.
     *
     * @param actionEvent The event that triggered this action.
     */
    public void onExitButton(ActionEvent actionEvent) {
        Platform.exit();
    }
}
