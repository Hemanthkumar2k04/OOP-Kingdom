package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractCourtHouse implements KingdomEntity {

    /**
     * Holds a trial for a given case.
     * @param caseName the name or description of the case
     */
    public abstract void holdTrial(String caseName);

    /**
     * Returns the total number of cases resolved by this court.
     * @return case count
     */
    public abstract int getCasesResolved();

    /**
     * Passes a judgment on the current case.
     * @param guilty true if the accused is found guilty, false if acquitted
     * @return the verdict as a string
     */
    public abstract String passJudgment(boolean guilty);
}