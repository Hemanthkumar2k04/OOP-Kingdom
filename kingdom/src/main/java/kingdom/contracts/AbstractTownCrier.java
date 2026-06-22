package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractTownCrier implements KingdomEntity {

    /**
     * Announces a message to the citizens.
     * @param message the message to announce
     */
    public abstract void announce(String message);

    /**
     * Returns the total number of announcements made.
     * @return announcement count
     */
    public abstract int getAnnouncementCount();

    /**
     * Returns the most recent announcement.
     * @return the latest announcement text, or empty string if none
     */
    public abstract String getLatestAnnouncement();
}