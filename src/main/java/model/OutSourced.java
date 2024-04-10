package model;

/**
 * Represents an outsourced part in the inventory system.
 * <p>
 * This class extends {@link Part}
 */
public class OutSourced extends Part {
    /**
     * The name of the company from which the part was outsourced.
     */
    private String companyName;

    /**
     * Constructs a new instance of an outsourced part with specified details.
     *
     * @param id          The unique identifier for the part.
     * @param name        The name of the part.
     * @param price       The price of the part.
     * @param stock       The inventory level of the part.
     * @param min         The minimum allowable inventory level for the part.
     * @param max         The maximum allowable inventory level for the part.
     * @param companyName The name of the company from which the part was outsourced.
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the name of the company from which the part was outsourced.
     *
     * @param companyName The new company name.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the name of the company from which the part was outsourced.
     *
     * @return The company name.
     */
    public String getCompanyName() {
        return this.companyName;
    }
}

