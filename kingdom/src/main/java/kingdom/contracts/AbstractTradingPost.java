package kingdom.contracts;

import kingdom.core.KingdomEntity;

public abstract class AbstractTradingPost implements KingdomEntity {

    /**
     * Trades processed velfire stones for resources from the trader.
     * Adds the bartered resources to the stockpile.
     * @param stoneCount the number of velfire stones to trade
     * @return true if the trade was successful, false if insufficient stones
     */
    public abstract boolean barter(int stoneCount);

    /**
     * Returns the current stockpile of bartered resources.
     * @return resource stockpile count
     */
    public abstract int getResourceStockpile();

    /**
     * Returns the total number of completed trades.
     * @return trade count
     */
    public abstract int getTradesCompleted();
}