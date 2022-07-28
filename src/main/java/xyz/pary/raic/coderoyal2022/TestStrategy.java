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
    private Map<Integer, UnitOrder> actions = new HashMap<>();
    private Map<Integer, Vec2> positions = new HashMap<>();

    @Override
    public Order getOrder(Game game, DebugInterface di) {
        System.out.println("tick: " + game.getCurrentTick());

        if (game.getCurrentTick() == 0) {
            System.out.println("st pos: " + game.getMyUnits().get(0).getPosition());
            Simulator sim = new Simulator(game.getCurrentTick(), game.getMyUnits(), Arrays.asList(game.getProjectiles()));
            for (int i = 0; i < 200; i++) {
                Unit u = sim.getUnits().get(0);
                u.setUnitOrder(new UnitOrder(
                        u.getDirection().sub(u.getPosition()),
                        //                        new Vec2(0, 0),
                        new Vec2(sim.getUnits().get(0).getDirection().getY(), -sim.getUnits().get(0).getDirection().getX()),
                        //                        null
                        new ActionOrder.Aim(true)
                ));
                res.put(sim.getTick(), new Unit(sim.getUnits().get(0)));
                sim.tick();

            }
        }

        if (res.get(game.getCurrentTick()) != null) {
            System.out.println("vel: " + game.getMyUnits().get(0).getVelocity() + "  sim: " + res.get(game.getCurrentTick()).getVelocity());
            System.out.println("vel len: " + game.getMyUnits().get(0).getVelocity().length() + "  sim: " + res.get(game.getCurrentTick()).getVelocity().length());
            System.out.println("pos: " + game.getMyUnits().get(0).getPosition() + "  sim: " + res.get(game.getCurrentTick()).getPosition());
            System.out.println("dir: " + game.getMyUnits().get(0).getDirection() + "  sim: " + res.get(game.getCurrentTick()).getDirection());
            System.out.println("aim: " + game.getMyUnits().get(0).getAim() + "  sim: " + res.get(game.getCurrentTick()).getAim());
            System.out.println("shot: " + game.getMyUnits().get(0).getNextShotTick() + "  sim: " + res.get(game.getCurrentTick()).getNextShotTick());
        }

        Map<Integer, UnitOrder> orders = new HashMap<>();
        for (Unit unit : game.getMyUnits()) {
            orders.put(unit.getId(), new UnitOrder(
                    unit.getDirection().sub(unit.getPosition()),
                    //                    new Vec2(0, 0),
                    new Vec2(unit.getDirection().getY(), -unit.getDirection().getX()),
                    //                    null
                    new ActionOrder.Aim(true)
            ));
            positions.put(game.getCurrentTick(), unit.getPosition());
            if (res.get(game.getCurrentTick()) != null) {
                Unit u = res.get(game.getCurrentTick());
                if (positions.get(game.getCurrentTick() - 1) != null && u.getPrevPosition() != null) {
                    System.out.println("dp: " + unit.getPosition().sub(positions.get(game.getCurrentTick() - 1)) + "  sim: " + u.getPosition().sub(u.getPrevPosition()));
                }
                if (di != null) {
                    if (u.getC() != null) {
                        di.add(new DebugData.Ring(
                                u.getC(), u.getR(), 0.25, new Color(0, 1, 0, 1))
                        );
                        di.add(new DebugData.Circle(
                                u.getC(), 0.25, new Color(0, 1, 0, 0.5))
                        );
                        di.add(new DebugData.Circle(
                                u.getIps()[0], 0.2, new Color(0, 0, 0, 0.5))
                        );
                        di.add(new DebugData.Circle(
                                u.getIps()[1], 0.2, new Color(0, 0, 0, 0.5))
                        );
                        di.add(new DebugData.Segment(
                                u.getIps()[0], u.getIps()[1], 0.1, new Color(0, 1, 0, 0.5))
                        );
                    }
                    if (u.getPrevPosition() != null) {
                        di.add(new DebugData.Segment(
                                u.getPrevPosition(), u.getPrevPosition().add(u.getUnitOrder().getTargetVelocity()), 0.1, new Color(0, 0, 1, 0.5))
                        );
                        di.add(new DebugData.Segment(
                                unit.getPosition(), unit.getPosition().add(orders.get(unit.getId()).getTargetVelocity()), 0.1, new Color(1, 0, 0, 0.5))
                        );
                        System.out.println("tv: " + orders.get(unit.getId()).getTargetVelocity() + "  sim: " + u.getUnitOrder().getTargetVelocity());
                    }
                    di.add(new DebugData.Segment(
                            u.getPosition(), u.getPosition().add(u.getDirection().mul(10)), 0.1, new Color(0, 0, 0, 0.5))
                    );
                    di.add(new DebugData.Segment(
                            u.getPosition(), u.getPosition().add(u.getVelocity()), 0.3, new Color(0, 1, 0, 0.5))
                    );
                    di.add(new DebugData.Segment(
                            unit.getPosition(), unit.getPosition().add(unit.getDirection().mul(15)), 0.1, new Color(1, 1, 0, 0.5))
                    );
                    di.add(new DebugData.Segment(
                            unit.getPosition(), unit.getPosition().add(unit.getVelocity()), 0.3, new Color(0, 0, 1, 0.5))
                    );
                    di.add(new DebugData.Circle(
                            u.getPosition(), 1, new Color(0, 0, 0, 0.5))
                    );
                }
            }
        }

        return new Order(orders);
    }

}
