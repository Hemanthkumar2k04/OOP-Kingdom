package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractGuardStation implements KingdomEntity {

    /**
     * Assigns a patrol to a district.
     * @param districtName the name of the district to patrol
     */
    public abstract void assignPatrol(String districtName);

    /**
     * Returns the number of active patrols currently deployed.
     * @return active patrol count
     */
    public abstract int getActivePatrolCount();

    /**
     * Coordinates a response to an incident across all guard posts.
     * @return a summary of the coordinated response
     */
    public abstract String coordinateResponse();
}