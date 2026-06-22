package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractOreProcessor implements KingdomEntity {

    /**
     * Processes raw ore into clean, usable velfire stone.
     * Consumes raw ore from the stockpile.
     */
    public abstract void processOre();

    /**
     * Returns the number of clean velfire stones ready for trade or use.
     * @return processed stone count
     */
    public abstract int getProcessedStoneCount();

    /**
     * Returns how many units of raw ore are needed for one processing cycle.
     * @return raw ore required per cycle
     */
    public abstract int getRawOreNeeded();

    /**
     * Returns the current amount of raw ore available for processing.
     * @return raw ore stockpile
     */
    public abstract int getRawOreStockpile();
}