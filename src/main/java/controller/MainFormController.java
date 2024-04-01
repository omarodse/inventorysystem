package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    public Button modifyPartButton;
    public TableView allPartsTable;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn inventoryLevel;
    public TableColumn partPriceCost;
    public TableView allProductsTable;
    public TableColumn productId;
    public TableColumn productName;
    public TableColumn productInventoryLevel;
    public TableColumn productPriceCost;

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
    public void getWindow(String source, String title, Button button, int width, int height) throws IOException {
        URL fxmlLocation = getClass().getResource(source);
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
    public void onAddPartButton(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/AddPartForm.fxml", "Add Part Form", addPartButton, 600, 600);
    }

    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        getWindow("/wgu/inventoryfxmlapp/ModifyPartForm.fxml", "Modify Part Form", modifyPartButton, 600, 600);
    }
}
