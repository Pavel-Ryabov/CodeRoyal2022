package xyz.pary.raic.coderoyal2022;

import java.util.HashMap;
import java.util.Map;
import xyz.pary.raic.coderoyal2022.debugging.Color;
import xyz.pary.raic.coderoyal2022.debugging.DebugData;
import xyz.pary.raic.coderoyal2022.model.ActionOrder;
import xyz.pary.raic.coderoyal2022.model.Constants;
import xyz.pary.raic.coderoyal2022.model.Game;
import xyz.pary.raic.coderoyal2022.model.Order;
import xyz.pary.raic.coderoyal2022.model.Unit;
import xyz.pary.raic.coderoyal2022.model.UnitOrder;
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.model.Zone;
import xyz.pary.raic.coderoyal2022.util.Vec2Util;

public class MyStrategy implements Strategy {

    private Constants constants;

    @Override
    public void updateConstants(Constants constants) {
        this.constants = constants;
    }

    @Override
    public Order getOrder(Game game, DebugInterface debugInterface) {
        Map<Integer, UnitOrder> orders = new HashMap<>();
        Zone zone = game.getZone();
        for (Unit unit : game.getMyUnits()) {
            Unit nearestEnemy = Vec2Util.getNearestTarget(unit, game.getEnemyUnits());
            ActionOrder action = null;
            if (unit.getShield() == 0 && unit.getShieldPotions() > 0) {
                action = new ActionOrder.UseShieldPotion();
            } else if (nearestEnemy != null) {
                action = new ActionOrder.Aim(true);
            }
            orders.put(unit.getId(), new UnitOrder(
                    unit.getPosition().getVelocity(zone.getNextCenter(), constants.getMaxUnitForwardSpeed()),
                    nearestEnemy != null
                            ? nearestEnemy.getPosition().sub(unit.getPosition())
                            : new Vec2(-unit.getDirection().getY(), unit.getDirection().getX()),
                    action
            ));
            if (nearestEnemy != null) {
                debugInterface.add(new DebugData.Segment(
                        unit.getPosition(), nearestEnemy.getPosition(), 0.25, new Color(0, 0, 1, 1))
                );
            }
        }

        return new Order(orders);
    }
}
