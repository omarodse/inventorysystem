package wgu.inventoryfxmlapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        //Added data for testing
        InHouse leg = new InHouse(01, "Leg", 25.00, 50, 14, 100, 0001);
        OutSourced top = new OutSourced(02, "Top", 45.00, 40, 10, 80, "Round Top");
        Product table = new Product(001, "Table", 499.00, 100, 50, 200);
        Product chair = new Product(002, "Chair", 50.00, 400, 100, 800);
        Inventory.addPart(leg);
        Inventory.addPart(top);
        Inventory.addProduct(table);
        Inventory.addProduct(chair);

        launch();
    }
}