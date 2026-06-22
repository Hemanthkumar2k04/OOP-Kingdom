package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractPrison implements KingdomEntity {

    /**
     * Admits a convicted criminal to the prison.
     * @param criminalName the name of the criminal
     */
    public abstract void imprison(String criminalName);

    /**
     * Releases a criminal from the prison.
     * @param criminalName the name of the criminal to release
     */
    public abstract void release(String criminalName);

    /**
     * Returns the number of inmates currently in the prison.
     * @return inmate count
     */
    public abstract int getInmateCount();
}