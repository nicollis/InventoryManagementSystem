package dev.ollis.wgu.inventorymanagementsystem;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private final ObservableList<Part> associatedParts  = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) throws IllegalArgumentException {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);


    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part part) {
        return associatedParts.remove(part);
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

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
