package dev.ollis.wgu.inventorymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

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

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        return allParts.stream().filter(part -> part.getId() == partId).findFirst().orElse(null);
    }

    public static Product lookupProduct(int productId) {
        return allProducts.stream().filter(product -> product.getId() == productId).findFirst().orElse(null);
    }

    public static ObservableList<Part> lookupPart(String partName) {
        return allParts.filtered(part -> part.getName().contains(partName));
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(product -> product.getName().contains(productName));
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
