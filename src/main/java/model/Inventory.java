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

    public static boolean deletePart(Part selectedPart) {
        int selectedPartId = selectedPart.getId();
        for(Part part: allParts) {
            if(part.getId() == selectedPartId) {
                allParts.remove(part);
                return true;
            }
        }
        return false;
    }

    //+ updatePart(index:int, selectedPart:Part):void
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        int selectedPartId = selectedProduct.getId();
        for(Product product: allProducts) {
            if(product.getId() == selectedPartId) {
                allProducts.remove(product);
                return true;
            }
        }
        return false;
    }
    public static ObservableList getAllParts() {
        return allParts;
    }

    public static Part lookupPart(int partId) {
        for(Part part: allParts) {
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for(Part part: allParts) {
            if(part.getName().contains(partName)) {
                parts.add(part);
            }
        }
        return parts;
    }

    public static Product lookupProduct(int productId) {
        for(Product product: allProducts) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        for(Product product: allProducts) {
            if(product.getName().contains(productName)) {
                products.add(product);
            }
        }
        return products;
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