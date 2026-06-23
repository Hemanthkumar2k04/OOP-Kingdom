package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractGatehouse implements KingdomEntity {

    /**
     * Admits a visitor into the city and logs their entry.
     * @param visitorName the name of the visitor
     */
    public abstract void admitVisitor(String visitorName);

    /**
     * Records a visitor's departure from the city.
     * @param visitorName the name of the departing visitor
     * @return true if the visitor was found in the log and marked as departed
     */
    public abstract boolean departVisitor(String visitorName);

    /**
     * Returns the total number of visitors who have entered the city.
     * @return total visitor count
     */
    public abstract int getTotalVisitors();
}