package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractMine implements KingdomEntity {

    /**
     * Extracts raw ore from the mine and adds it to the stockpile.
     * May increase the depth of the mine over time.
     */
    public abstract void extractOre();

    /**
     * Returns the current amount of raw ore in the stockpile.
     * @return ore stockpile count
     */
    public abstract int getOreStockpile();

    /**
     * Returns the current depth of the mine.
     * Deeper mines yield more ore per extraction.
     * @return mine depth level
     */
    public abstract int getMineDepth();
}