package dev.ollis.wgu.inventorymanagementsystem;

public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) throws IllegalArgumentException {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
