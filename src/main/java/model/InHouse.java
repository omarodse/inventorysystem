package model;

/**
 * Represents an in-house manufactured part.
 * <p>
 * This class extends {@link Part} to include additional methods
 */
public class InHouse extends Part {
    /**
     * The ID of the machine used to manufacture the part.
     */
    private int machineId;

    /**
     * Constructs an instance of an in-house part with specified details.
     *
     * @param id        The ID of the part.
     * @param name      The name of the part.
     * @param price     The price of the part.
     * @param stock     The current stock level of the part.
     * @param min       The minimum allowable stock level for the part.
     * @param max       The maximum allowable stock level for the part.
     * @param machineId The ID of the machine used to manufacture the part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets the machine ID for this in-house part.
     *
     * @param machineId The new machine ID.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Retrieves the machine ID for this in-house part.
     *
     * @return The machine ID.
     */
    public int getMachineId() {
        return this.machineId;
    }
}

