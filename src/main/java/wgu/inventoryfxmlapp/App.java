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
//        InHouse leg = new InHouse(01, "Leg", 25.00, 500, 100, 1000, 0001);
//        OutSourced top = new OutSourced(02, "top", 45.00, 250, 80, 400, "Round Top");
//        InHouse bedBack = new InHouse(03, "bedBack", 80.00, 50, 20, 100, 0002);
//        InHouse bedFrame = new InHouse(04, "bedFrame", 100.00, 50, 20, 100, 0003);
//        OutSourced chairSeat = new OutSourced(05, "chairSeat", 45.00, 180, 108, 280, "Square Top");
//        Product table = new Product(001, "Table", 499.00, 100, 50, 200);
//        Product chair = new Product(002, "Chair", 50.00, 400, 100, 800);
//        Product sofa = new Product(001, "Sofa", 499.00, 100, 50, 200);
//        Inventory.addPart(leg);
//        Inventory.addPart(top);
//        Inventory.addPart(bedBack);
//        Inventory.addPart(bedFrame);
//        Inventory.addPart(chairSeat);
//        Inventory.addProduct(table);
//        Inventory.addProduct(chair);
//        Inventory.addProduct(sofa);

        launch();
    }
}