package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractGuardPost implements KingdomEntity {

    /**
     * Sends a guard on patrol through the assigned district.
     * May discover incidents or keep the peace.
     * @return a patrol report describing what was found
     */
    public abstract String patrol();

    /**
     * Returns the number of incidents reported at this guard post.
     * @return incident count
     */
    public abstract int getIncidentCount();

    /**
     * Reports the guard post's status and incidents to the central Guard Station.
     * @return true if the report was successfully sent
     */
    public abstract boolean reportToStation();
}