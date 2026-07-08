package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractBeaconTower implements KingdomEntity {

    /**
     * Lights the beacon with a specific signal type to communicate across the battlefield.
     * @param signalType the type of signal (e.g., "advance", "retreat", "reinforcements", "victory")
     */
    public abstract void lightBeacon(String signalType);

    /**
     * Returns whether the beacon is currently lit.
     * @return true if the beacon is lit
     */
    public abstract boolean isLit();

    /**
     * Returns the last signal type that was sent from this tower.
     * @return the last signal type, or empty string if none
     */
    public abstract String getLastSignal();

    /**
     * Returns the total number of signals sent from this tower.
     * @return signal count
     */
    public abstract int getSignalCount();
}