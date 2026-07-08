package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractDiplomaticEnclave implements KingdomEntity {

    /**
     * Sends an envoy with a message to a specified kingdom.
     * @param kingdomName the target kingdom
     * @param message the message to deliver
     */
    public abstract void sendEnvoy(String kingdomName, String message);

    /**
     * Returns the number of unread messages received from allied kingdoms.
     * @return pending message count
     */
    public abstract int getPendingMessageCount();

    /**
     * Reads and returns the latest message received from an allied kingdom.
     * @return the latest message, or empty string if none
     */
    public abstract String readLatestMessage();

    /**
     * Attempts to forge or reinforce an alliance with a given kingdom.
     * @param kingdomName the kingdom to form an alliance with
     * @return true if the alliance was successfully established
     */
    public abstract boolean forgeAlliance(String kingdomName);
}