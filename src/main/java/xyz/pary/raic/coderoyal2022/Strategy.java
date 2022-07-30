package xyz.pary.raic.coderoyal2022;

import xyz.pary.raic.coderoyal2022.model.Game;
import xyz.pary.raic.coderoyal2022.model.Order;

public interface Strategy {

    public Order getOrder(Game game);

    public default void debugUpdate(int displayedTick, DebugInterface debugInterface) {
    }

    public default void finish() {
    }
}
