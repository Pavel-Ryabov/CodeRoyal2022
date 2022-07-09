package xyz.pary.raic.coderoyal2022;

import java.util.HashMap;
import java.util.Map;
import xyz.pary.raic.coderoyal2022.model.ActionOrder;
import xyz.pary.raic.coderoyal2022.model.Constants;
import xyz.pary.raic.coderoyal2022.model.Game;
import xyz.pary.raic.coderoyal2022.model.Order;
import xyz.pary.raic.coderoyal2022.model.Unit;
import xyz.pary.raic.coderoyal2022.model.UnitOrder;
import xyz.pary.raic.coderoyal2022.model.Vec2;

public class MyStrategy {

    public MyStrategy(Constants constants) {
    }

    public Order getOrder(Game game, DebugInterface debugInterface) {
        Map<Integer, UnitOrder> orders = new HashMap<>();
        for (Unit unit : game.getUnits()) {
            if (unit.getPlayerId() != game.getMyId()) {
                continue;
            }
            orders.put(unit.getId(), new UnitOrder(
                    new Vec2(-unit.getPosition().getX(), -unit.getPosition().getY()),
                    new Vec2(-unit.getDirection().getY(), unit.getDirection().getX()),
                    new ActionOrder.Aim(true)));
        }
        return new Order(orders);
    }

    public void debugUpdate(DebugInterface debugInterface) {
    }

    public void finish() {
    }
}
