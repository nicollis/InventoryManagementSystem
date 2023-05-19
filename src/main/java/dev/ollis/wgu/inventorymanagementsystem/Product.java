package dev.ollis.wgu.inventorymanagementsystem;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class
 * @author Nicholas Ollis
 */
public class Product {

    private final ObservableList<Part> associatedParts  = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor
     * @param id Product ID
     * @param name Product name
     * @param price Product price
     * @param stock Product stock
     * @param min Product min
     * @param max Product max
     * @throws IllegalArgumentException if min is greater than max or stock is not between min and max
     */
    public Product(int id, String name, double price, int stock, int min, int max) throws IllegalArgumentException {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);


    }

    /**
     * Default constructor
     * Used to allow for the ProductFormViewController to create a new product before data is set
     */
    public Product() {
    }

    /**
     * Gets the ID
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID
     * @param id ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price
     * @param price price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the stock
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock
     * @param stock stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets the min
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the min
     * @param min min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets the max
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the max
     * @param max max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds a part to the associated parts list
     * @param part part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes a part from the associated parts list
     * @param part part
     * @return true if part was removed, false if part was not found
     */
    public boolean deleteAssociatedPart(Part part) {
        return associatedParts.remove(part);
    }

    /**
     * Gets all associated parts
     * @return associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Validates the product
     * @return true if valid, false if not
     * @throws IllegalArgumentException if min is greater than max or stock is not between min and max
     */
    public boolean validateProduct() throws IllegalArgumentException {
        if (min > max) {
            throw new IllegalArgumentException("Min must be less than max");
        }

        if (stock < min || stock > max) {
            throw new IllegalArgumentException("Inventory must be between min and max");
        }
        return true;
    }
}
