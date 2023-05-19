package dev.ollis.wgu.inventorymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class that holds all parts and products.
 * @author Nicholas Ollis
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // Add some sample data
    static {
        allParts.add(new Outsourced(1, "Breaks", 15.00, 10, 2, 20, "We do breaks, Inc."));
        allParts.add(new InHouse(2, "Wheel", 11.00, 16, 3, 40, 1));
        allParts.add(new Outsourced(3, "Seat", 10.00, 10, 2, 20, "Take a seat, LLC"));
        allParts.add(new InHouse(4, "Frame", 20.00, 5, 1, 12, 2));

        Product giant_bike = new Product(1000, "Giant Bike", 299.99, 5, 1, 10);
        Product tricycle = new Product(1001, "Tricycle", 99.99, 3, 1, 5);

        // Associate parts with products
        giant_bike.addAssociatedPart(allParts.get(0));
        giant_bike.addAssociatedPart(allParts.get(1));

        tricycle.addAssociatedPart(allParts.get(0));
        tricycle.addAssociatedPart(allParts.get(3));

        allProducts.add(giant_bike);
        allProducts.add(tricycle);
    }

    /**
     * Adds a part to the inventory.
     * @param newPart The part to add.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a product to the inventory.
     * @param newProduct The product to add.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Looks up a part by ID.
     * @param partId The ID of the part to look up.
     * @return The part if found, otherwise null.
     * FUTURE ENHANCEMENT: Partial ID matching should be supported.
     */
    public static Part lookupPart(int partId) {
        return allParts.stream().filter(part -> part.getId() == partId).findFirst().orElse(null);
    }

    /**
     * Looks up a product by ID.
     * @param productId The ID of the product to look up.
     * @return The product if found, otherwise null.
     * FUTURE ENHANCEMENT: Partial ID matching should be supported.
     */
    public static Product lookupProduct(int productId) {
        return allProducts.stream().filter(product -> product.getId() == productId).findFirst().orElse(null);
    }

    /**
     * Looks up a part by name.
     * @param partName The name of the part to look up.
     * @return A list of parts that match the name.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        return allParts.filtered(part -> part.getName().contains(partName));
    }

    /**
     * Looks up a product by name.
     * @param productName The name of the product to look up.
     * @return A list of products that match the name.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(product -> product.getName().contains(productName));
    }

    /**
     * Updates a part in the inventory.
     * @param index The index of the part to update.
     * @param selectedPart The part to update.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates a product in the inventory.
     * @param index The index of the product to update.
     * @param selectedProduct The product to update.
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Deletes a part from the inventory.
     * @param selectedPart The part to delete.
     * @return True if the part was deleted, otherwise false.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a product from the inventory.
     * @param selectedProduct The product to delete.
     * @return True if the product was deleted, otherwise false.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Gets all parts in the inventory.
     * @return A list of all parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products in the inventory.
     * @return A list of all products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
