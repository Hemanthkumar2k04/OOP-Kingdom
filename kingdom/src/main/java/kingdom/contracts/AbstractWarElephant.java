package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractWarElephant implements KingdomEntity {

    /**
     * Assigns a rider (archer) to the war elephant.
     * @param riderName the name of the rider
     */
    public abstract void assignRider(String riderName);

    /**
     * Returns the number of riders assigned to this elephant.
     * @return rider count
     */
    public abstract int getRiderCount();

    /**
     * Orders the war elephant to charge into enemy lines.
     * @return the result of the charge as a descriptive string
     */
    public abstract String charge();

    /**
     * Checks whether the elephant is battle-ready.
     * @return true if morale is high enough to fight
     */
    public abstract boolean isBattleReady();
}