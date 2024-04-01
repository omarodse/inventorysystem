package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static ObservableList getAllParts() {
        return allParts;
    }

    public static ObservableList getAllProducts() {
        return allProducts;
    }

}

//+ addPart(newPart:Part):void
//+ addProduct(newProduct:Product):void
//+ lookupPart(partId:int):Part
//+ lookupProduct(productId:int):Product
//+ lookupPart(partName:String):ObservableList<Part>
//+ lookupProduct(productName:String):ObservableList<Product>
//+ updatePart(index:int, selectedPart:Part):void
//+ updateProduct(index:int, newProduct:Product):void
//+ deletePart(selectedPart:Part):boolean
//+ deleteProduct(selectedProduct:Product):boolean
//+ getAllParts():ObservableList<Part>
//+ getAllProducts():ObservableList<Product>