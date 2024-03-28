module wgu.inventoryfxmlapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens wgu.inventoryfxmlapp to javafx.fxml;
    exports wgu.inventoryfxmlapp;
    exports controller;
    opens controller to javafx.fxml;
}