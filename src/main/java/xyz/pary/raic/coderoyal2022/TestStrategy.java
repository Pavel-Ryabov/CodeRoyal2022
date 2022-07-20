package xyz.pary.raic.coderoyal2022;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import xyz.pary.raic.coderoyal2022.debugging.Color;
import xyz.pary.raic.coderoyal2022.debugging.DebugData;
import xyz.pary.raic.coderoyal2022.model.ActionOrder;
import xyz.pary.raic.coderoyal2022.model.Game;
import xyz.pary.raic.coderoyal2022.model.Order;
import xyz.pary.raic.coderoyal2022.model.Unit;
import xyz.pary.raic.coderoyal2022.model.UnitOrder;
import xyz.pary.raic.coderoyal2022.model.Vec2;

public class TestStrategy implements Strategy {

    private List<Unit> res = new ArrayList<>();

    @Override
    public Order getOrder(Game game, DebugInterface debugInterface) {
        System.out.println("tick: " + game.getCurrentTick());
        System.out.println("acc t: " + (Game.CONSTANTS.getUnitAcceleration() * (1.0 / 30)));

        if (game.getCurrentTick() == 0) {
            Simulator sim = new Simulator(game.getCurrentTick(), game.getMyUnits(), Arrays.asList(game.getProjectiles()));
            for (int i = 0; i < 90; i++) {
                sim.tick();
            }
            res = sim.getRes();
        }
        if (game.getCurrentTick() > 0 && game.getCurrentTick() <= res.size()) {
            System.out.println("vel: " + game.getMyUnits().get(0).getVelocity() + "  sim: " + res.get(game.getCurrentTick() - 1).getVelocity());
            System.out.println("vel len: " + game.getMyUnits().get(0).getVelocity().length() + "  sim: " + res.get(game.getCurrentTick() - 1).getVelocity().length());
            System.out.println("dir: " + game.getMyUnits().get(0).getDirection() + "  sim: " + res.get(game.getCurrentTick() - 1).getDirection());
            System.out.println("aim: " + game.getMyUnits().get(0).getAim() + "  sim: " + res.get(game.getCurrentTick() - 1).getAim());
            System.out.println("soot: " + game.getMyUnits().get(0).getNextShotTick() + "  sim: " + res.get(game.getCurrentTick() - 1).getNextShotTick());
        }
        Map<Integer, UnitOrder> orders = new HashMap<>();
        for (Unit unit : game.getMyUnits()) {
            orders.put(unit.getId(), new UnitOrder(
                    unit.getDirection().sub(unit.getPosition()).normalize().mul(100),
                    new Vec2(0, 0),
                    //unit.getDirection().mul(50).rotate(-20, true),
                    //new Vec2(unit.getDirection().getY(), -unit.getDirection().getX()),
                    new ActionOrder.Aim(true)
            ));
            if (game.getCurrentTick() > 0 && game.getCurrentTick() <= res.size()) {
                if (debugInterface != null) {
                    debugInterface.add(new DebugData.Segment(
                            unit.getPosition(), unit.getPosition().add(unit.getDirection().mul(20)), 0.25, new Color(1, 0, 0, 0.5))
                    );
                }
                if (debugInterface != null) {
                    debugInterface.add(new DebugData.Segment(
                            unit.getPosition(), unit.getPosition().add(res.get(game.getCurrentTick() - 1).getDirection().mul(20)), 0.25, new Color(0, 0, 1, 0.5))
                    );
                }
            }
            double forwardSpeed = Game.CONSTANTS.getMaxUnitForwardSpeed();
            double backwardSpeed = Game.CONSTANTS.getMaxUnitBackwardSpeed();
            if (unit.getAim() > 0 && unit.getAim() < 1) {
                double speedModifier = unit.getWeaponProperties().getAimMovementSpeedModifier();
                forwardSpeed = forwardSpeed * (1 - (1 - speedModifier) * unit.getAim());
                backwardSpeed = backwardSpeed * (1 - (1 - speedModifier) * unit.getAim());
            } else if (unit.getAim() == 1) {
                double speedModifier = unit.getWeaponProperties().getAimMovementSpeedModifier();
                forwardSpeed *= speedModifier;
                backwardSpeed *= speedModifier;
            }
            System.out.println("forwardSpeed: " + forwardSpeed);
            System.out.println("backwardSpeed: " + backwardSpeed);
            System.out.println("(forwardSpeed - backwardSpeed) / 2: " + (forwardSpeed - backwardSpeed) / 2);
            //После расчета ограничений вперед/назад, вектор целевой скорости ограничивается кругом радиуса 
            //(max_unit_forward_speed + max_unit_backward_speed) / 2. 
            //Центр круга ограничения скорости лежит по направлению зрения юнита на расстоянии 
            //(max_unit_forward_speed - max_unit_backward_speed) / 2.
            double r = (forwardSpeed - backwardSpeed) / 2;
            Vec2 c = unit.getDirection().mul(r);
            if (debugInterface != null) {
                debugInterface.add(new DebugData.Ring(
                        unit.getPosition().add(c), r, 0.1, new Color(0, 0, 0, 1))
                );
            }

        }

        return new Order(orders);
    }

}
