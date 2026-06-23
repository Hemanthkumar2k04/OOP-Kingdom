package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractStorehouse implements KingdomEntity {

    /**
     * Stores grain from agriculture into the storehouse.
     * @param amount the amount of grain to store (must be positive)
     */
    public abstract void storeGrain(int amount);

    /**
     * Stores resources from trading into the storehouse.
     * @param amount the amount of resources to store (must be positive)
     */
    public abstract void storeResources(int amount);

    /**
     * Returns the current grain stockpile.
     * @return grain stockpile count
     */
    public abstract int getGrainStockpile();

    /**
     * Returns the current resource stockpile.
     * @return resource stockpile count
     */
    public abstract int getResourceStockpile();

    /**
     * Distributes stored goods to the citizens,
     * reducing both grain and resource stockpiles.
     * @return true if distribution was possible, false if stockpiles are empty
     */
    public abstract boolean distribute();
}