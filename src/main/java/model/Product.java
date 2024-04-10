package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a product composed of one or more parts.
 * <p>
 * This class holds information about the product, including its ID, name, price, stock levels,
 * and the list of parts associated with it.
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructs a new Product with the specified details.
     *
     * @param id    The unique identifier for the product.
     * @param name  The name of the product.
     * @param price The price of the product.
     * @param stock The current inventory level of the product.
     * @param min   The minimum allowable inventory level for the product.
     * @param max   The maximum allowable inventory level for the product.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets the product's ID.
     *
     * @param id The new ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the product's name.
     *
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the product's price.
     *
     * @param price The new price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the product's inventory level.
     *
     * @param stock The new inventory level.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the product's minimum inventory level.
     *
     * @param min The new minimum inventory level.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets the product's maximum inventory level.
     *
     * @param max The new maximum inventory level.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Returns the product's ID.
     *
     * @return The ID of the product.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the product's name.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the product's price.
     *
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the product's inventory level.
     *
     * @return The inventory level of the product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Returns the product's minimum inventory level.
     *
     * @return The minimum inventory level of the product.
     */
    public int getMin() {
        return min;
    }

    /**
     * Returns the product's maximum inventory level.
     *
     * @return The maximum inventory level of the product.
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds a part to the product's list of associated parts.
     *
     * @param part The part to add.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes a part from the product's list of associated parts.
     *
     * @param selectedAssociatedPart The part to delete.
     * @return true if the part was successfully removed, false otherwise.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Returns an observable list of all parts associated with the product.
     *
     * @return An observable list of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }
}
