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
import xyz.pary.raic.coderoyal2022.model.Obstacle;
import xyz.pary.raic.coderoyal2022.model.Order;
import xyz.pary.raic.coderoyal2022.model.Unit;
import xyz.pary.raic.coderoyal2022.model.UnitOrder;
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.util.GeoUtil;

public class TestStrategy implements Strategy {

    private Map<Integer, Unit> res = new HashMap<>();

    @Override
    public Order getOrder(Game game, DebugInterface di) {
        System.out.println("tick: " + game.getCurrentTick());

        if (game.getProjectiles().length > 0) {
            System.out.println("st pos: " + game.getMyUnits().get(0).getPosition());
            Simulator sim = new Simulator(game.getCurrentTick(), game.getMyUnits(), Arrays.asList(game.getProjectiles()));
            for (int i = 0; i < 200; i++) {
                sim.tick(di);
            }
            res = sim.getRes();
        }

        if (res.get(game.getCurrentTick()) != null) {
            System.out.println("pos: " + game.getMyUnits().get(0).getPosition() + "  sim: " + res.get(game.getCurrentTick()).getPosition());
            System.out.println("vel: " + game.getMyUnits().get(0).getVelocity() + "  sim: " + res.get(game.getCurrentTick()).getVelocity());
            System.out.println("vel len: " + game.getMyUnits().get(0).getVelocity().length() + "  sim: " + res.get(game.getCurrentTick()).getVelocity().length());
            System.out.println("dir: " + game.getMyUnits().get(0).getDirection() + "  sim: " + res.get(game.getCurrentTick()).getDirection());
            System.out.println("aim: " + game.getMyUnits().get(0).getAim() + "  sim: " + res.get(game.getCurrentTick()).getAim());
            System.out.println("soot: " + game.getMyUnits().get(0).getNextShotTick() + "  sim: " + res.get(game.getCurrentTick()).getNextShotTick());
        }

        Map<Integer, UnitOrder> orders = new HashMap<>();
        for (Unit unit : game.getMyUnits()) {
            orders.put(unit.getId(), new UnitOrder(
                    //unit.getDirection().sub(unit.getPosition()).normalize().mul(100),
                    unit.getDirection().normalize().mul(100),
//                    unit.getDirection().rotate(180).mul(20),
                    //new Vec2(0, 0),
                    //unit.getDirection().mul(50).rotate(-20, true),
                    new Vec2(unit.getDirection().getY(), -unit.getDirection().getX()),
                    //                    new ActionOrder.Aim(true)
                    null
            ));
//            if (game.getCurrentTick() > 0 && game.getCurrentTick() <= res.size()) {
//                Unit u = res.get(game.getCurrentTick() - 1);
//                if (di != null) {
//                    di.add(new DebugData.Segment(
//                            u.getPosition(), u.getPosition().add(u.getDirection()), 0.25, new Color(0, 0, 0, 0.5))
//                    );
//                    di.add(new DebugData.Segment(
//                            u.getPosition(), u.getPosition().add(u.getVelocity()), 0.25, new Color(0, 1, 0, 0.5))
//                    );
//                    di.add(new DebugData.Segment(
//                            unit.getPosition(), unit.getPosition().add(unit.getVelocity()), 0.25, new Color(0, 0, 1, 0.5))
//                    );
//                    di.add(new DebugData.Circle(
//                            u.getPosition(), 1, new Color(1, 0, 0, 0.5))
//                    );
//                }
//            }
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
            //После расчета ограничений вперед/назад, вектор целевой скорости ограничивается кругом радиуса 
            //(max_unit_forward_speed + max_unit_backward_speed) / 2. 
            //Центр круга ограничения скорости лежит по направлению зрения юнита на расстоянии 
            //(max_unit_forward_speed - max_unit_backward_speed) / 2.
//            double r = (forwardSpeed - backwardSpeed) / 2;
//            Vec2 c = unit.getDirection().mul(r);
//            if (debugInterface != null) {
//                debugInterface.add(new DebugData.Ring(
//                        unit.getPosition().add(c), r, 0.1, new Color(0, 0, 0, 1))
//                );
//            }

        }

        return new Order(orders);
    }

}
