package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Manages the inventory of parts and products.
 * <p>
 * This class provides methods for adding, deleting, and updating parts and products
 * in the inventory, as well as looking up parts and products by ID or name.
 */
public class Inventory {

    /**
     * A list of all parts in the inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * A list of all products in the inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the inventory.
     *
     * @param newPart The part to add.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new product to the inventory.
     *
     * @param newProduct The product to add.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Deletes a part from the inventory.
     *
     * @param selectedPart The part to delete.
     * @return {@code true} if the part was deleted successfully, {@code false} otherwise.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Updates a part in the inventory at the specified index.
     *
     * @param index The index of the part to update.
     * @param selectedPart The updated part.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates a product in the inventory at the specified index.
     *
     * @param index The index of the product to update.
     * @param selectedProduct The updated product.
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Deletes a product from the inventory.
     *
     * @param selectedProduct The product to delete.
     * @return {@code true} if the product was deleted successfully, {@code false} otherwise.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Returns a list of all parts in the inventory.
     *
     * @return An observable list of all parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Looks up a part in the inventory by ID.
     *
     * @param partId The ID of the part to look up.
     * @return The part with the specified ID, or {@code null} if not found.
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Looks up parts in the inventory by name.
     *
     * @param partName The name or partial name of the parts to look up.
     * @return An observable list of parts that match the name.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                matchingParts.add(part);
            }
        }
        return matchingParts;
    }

    /**
     * Looks up a product in the inventory by ID.
     *
     * @param productId The ID of the product to look up.
     * @return The product with the specified ID, or {@code null} if not found.
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Looks up products in the inventory by name.
     *
     * @param productName The name or partial name of the products to look up.
     * @return An observable list of products that match the name.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    /**
     * Returns a list of all products in the inventory.
     *
     * @return An observable list of all products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
