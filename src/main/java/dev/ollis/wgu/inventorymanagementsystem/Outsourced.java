package dev.ollis.wgu.inventorymanagementsystem;

/**
 * Outsourced part class extends the part class and adds a company name field.
 * @author Nicholas Ollis
 */
public class Outsourced extends Part{

    private String companyName;

    /**
     * Constructor for the Outsourced class
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     * @throws IllegalArgumentException
     * RUNTIME ERROR: If the min is greater than the max, or the inventory is not within min max bounds, throw an exception.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) throws IllegalArgumentException {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    /**
     * Getter for the company name
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter for the company name
     * @param companyName Company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
