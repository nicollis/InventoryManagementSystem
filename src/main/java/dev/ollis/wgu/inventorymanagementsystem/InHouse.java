package dev.ollis.wgu.inventorymanagementsystem;

/**
 * InHouse Part class Extends the Part class and adds a machineId field
 * @author Nicholas Ollis
 * */
public class InHouse extends Part {

    private int machineId;

    /**
     * Constructor for InHouse Part
     * @param id Part ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Part Stock
     * @param min Part Min
     * @param max Part Max
     * @param machineId Part Machine ID
     * @throws IllegalArgumentException if min is greater than max, stock is less than min, or stock is greater than max
     * RUNTIME ERROR: If min is greater than max, stock is less than min, or stock is greater than max, an IllegalArgumentException is thrown
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) throws IllegalArgumentException {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    /**
     * Constructor for InHouse Part
     * @param id Part ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Part Stock
     * @param min Part Min
     * @param max Part Max
     * @throws IllegalArgumentException if min is greater than max, stock is less than min, or stock is greater than max
     * RUNTIME ERROR: If min is greater than max, stock is less than min, or stock is greater than max, an IllegalArgumentException is thrown
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Gets the machine ID
     * @return machineId
     * */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets the machine ID
     * @param machineId Part Machine ID
     * */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
