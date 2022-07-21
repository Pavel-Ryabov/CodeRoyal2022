package xyz.pary.raic.coderoyal2022;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import xyz.pary.raic.coderoyal2022.debugging.Color;
import xyz.pary.raic.coderoyal2022.debugging.DebugData;
import xyz.pary.raic.coderoyal2022.model.Action;
import xyz.pary.raic.coderoyal2022.model.ActionOrder;
import xyz.pary.raic.coderoyal2022.model.ActionOrderType;
import xyz.pary.raic.coderoyal2022.model.ActionType;
import xyz.pary.raic.coderoyal2022.model.Game;
import xyz.pary.raic.coderoyal2022.model.Projectile;
import xyz.pary.raic.coderoyal2022.model.Unit;
import xyz.pary.raic.coderoyal2022.model.UnitOrder;
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.util.GeoUtil;
import xyz.pary.raic.coderoyal2022.util.TrigonometryUtil;

public class Simulator {

    private List<Unit> res = new ArrayList<>();

    private final List<Unit> units;
    private final List<Projectile> projectiles;

    private int tick;

    public Simulator(int tick, List<Unit> units, List<Projectile> projectiles) {
        this.tick = tick;
        this.units = units.stream().map(u -> new Unit(u)).collect(Collectors.toList());
        this.projectiles = projectiles.stream().map(p -> new Projectile(p)).collect(Collectors.toList());
    }

    public void tick(DebugInterface di) {
//        if (tick == 0) {
//            tick++;
//            return;
//        }
        double dt = 1 / Game.CONSTANTS.getTicksPerSecond();
        for (Unit unit : units) {
            unit.setUnitOrder(new UnitOrder(
                    unit.getDirection().sub(unit.getPosition()).normalize().mul(100),
                    new Vec2(0, 0),
                    //unit.getDirection().mul(50).rotate(-20, true),
                    //new Vec2(unit.getDirection().getY(), -unit.getDirection().getX()),
                    new ActionOrder.Aim(true)
            ));
        }
        act();
        //rotate(dt);
        aim(dt);
        move(dt, di);
        res.add(new Unit(units.get(0)));
        tick++;
    }

    private void act() {
        for (Unit u : units) {
            if (u.getRemainingSpawnTime() == null && u.getAim() == 0) {
                if (u.getAction() != null) {
                    if (u.getAction().getFinishTick() == tick) {
                        u.setAction(null);
                    }
                } else if (u.getUnitOrder() != null && u.getUnitOrder().getAction() != null) {
                    if (u.getUnitOrder().getAction().getType() == ActionOrderType.USE_SHIELD_POTION) {
                        u.setAction(new Action(tick + Game.timeToTicks(Game.CONSTANTS.getShieldPotionUseTime()), ActionType.USE_SHIELD_POTION));
                    } else if (u.getUnitOrder().getAction().getType() == ActionOrderType.PICKUP) {
                        u.setAction(new Action(tick + Game.timeToTicks(Game.CONSTANTS.getLootingTime()), ActionType.LOOTING));
                    }
                }
            }
        }
    }

    private void rotate(double dt) {
        for (Unit u : units) {
            if (u.getUnitOrder() != null && u.getUnitOrder().getTargetDirection() != null) {
                double rotationSpeed = Game.CONSTANTS.getRotationSpeed();
                if (u.getWeapon() != null) {
                    double aimRotationSpeed = u.getWeaponProperties().getAimRotationSpeed();
                    if (u.getAim() > 0 && u.getAim() < 1) {
                        rotationSpeed = rotationSpeed - (rotationSpeed - aimRotationSpeed) * u.getAim();
                    } else if (u.getAim() == 1) {
                        rotationSpeed = aimRotationSpeed;
                    }
                }
                rotationSpeed *= dt;
                Vec2 dir = u.getDirection();
                Vec2 tDir = u.getUnitOrder().getTargetDirection();
                double angle = Math.toDegrees(Math.atan2(GeoUtil.crossProduct(dir, tDir), GeoUtil.dotProduct(dir, tDir)));
                u.setDirection(dir.rotate(Math.min(rotationSpeed, Math.abs(angle)), angle > 0));
            }
        }
    }

    private void aim(double dt) {
        for (Unit u : units) {
            if (u.getRemainingSpawnTime() != null || u.getWeapon() == null || u.getAction() != null) {
                continue;
            }
            if (u.getUnitOrder() == null
                    || u.getUnitOrder().getAction() == null
                    || u.getUnitOrder().getAction().getType() != ActionOrderType.AIM) {
                double aimTime = u.getAim() - 1 / u.getWeaponProperties().getAimTime() * dt;
                u.setAim(aimTime - Game.EPS <= 0 ? 0 : aimTime);
            } else if (u.getAim() < 1) {
                double aimTime = u.getAim() + 1 / u.getWeaponProperties().getAimTime() * dt;
                u.setAim(aimTime + Game.EPS >= 1 ? 1 : aimTime);
            }
            if (u.getAim() == 1 && u.getNextShotTick() <= tick && ((ActionOrder.Aim) u.getUnitOrder().getAction()).isShoot()) {
                u.setNextShotTick(tick + Game.timeToTicks(1 / Game.CONSTANTS.getWeapons().get(u.getWeapon()).getRoundsPerSecond()));
            }
        }
    }

    private void move(double dt, DebugInterface di) {
        for (Unit u : units) {
            Vec2 normVel = u.getUnitOrder().getTargetVelocity().normalize();
            Vec2 nextVel;
            if (u.getRemainingSpawnTime() != null) {
                nextVel = normVel.mul(Game.CONSTANTS.getSpawnMovementSpeed());
            } else {
                double forwardSpeed = Game.CONSTANTS.getMaxUnitForwardSpeed();
                double backwardSpeed = Game.CONSTANTS.getMaxUnitBackwardSpeed();
                if (u.getAim() > 0 && u.getAim() < 1) {
                    double speedModifier = u.getWeaponProperties().getAimMovementSpeedModifier();
                    forwardSpeed = forwardSpeed * (1 - (1 - speedModifier) * u.getAim());
                    backwardSpeed = backwardSpeed * (1 - (1 - speedModifier) * u.getAim());
                } else if (u.getAim() == 1) {
                    double speedModifier = u.getWeaponProperties().getAimMovementSpeedModifier();
                    forwardSpeed *= speedModifier;
                    backwardSpeed *= speedModifier;
                }
                //После расчета ограничений вперед/назад, вектор целевой скорости ограничивается кругом радиуса 
                //(max_unit_forward_speed + max_unit_backward_speed) / 2. 
                //Центр круга ограничения скорости лежит по направлению зрения юнита на расстоянии 
                //(max_unit_forward_speed - max_unit_backward_speed) / 2.
                double r = (forwardSpeed - backwardSpeed) / 2;
                Vec2 c = GeoUtil.getIntersect(u.getPosition(), r, u.getPosition().add(u.getDirection()));

//                Vec2 c = u.getDirection().mul(r);
                nextVel = u.getUnitOrder().getTargetVelocity();
                Vec2[] ips = GeoUtil.getIntersectionPoints(u.getPosition(), c, c, r);
                if (ips.length == 1) {
                    nextVel = ips[0].sub(u.getPosition());
                }
                if (ips.length == 2) {
                    nextVel = (ips[0].squredDistanceTo(nextVel) < ips[1].squredDistanceTo(nextVel) ? ips[0] : ips[1]).sub(u.getPosition());
//                    if (di != null) {
//                        di.add(new DebugData.Segment(
//                                u.getPosition(), u.getPosition().add(u.getDirection()), 0.25, new Color(0, 0, 0, 0.5))
//                        );
//                        di.add(new DebugData.Ring(
//                                u.getPosition(), r, 0.1, new Color(0, 0, 0, 0.5))
//                        );
//                        di.add(new DebugData.Circle(
//                                u.getPosition(), 1, new Color(1, 0, 0, 0.5))
//                        );
//                        di.add(new DebugData.Circle(
//                                ips[0].squredDistanceTo(nextVel) < ips[1].squredDistanceTo(nextVel) ? ips[0] : ips[1], 0.1, new Color(0, 0, 1, 0.5))
//                        );
//                        di.clear();
//                    }
                }
                u.setC(c);
                u.setR(r);
                u.setIps(ips[0].squredDistanceTo(nextVel) < ips[1].squredDistanceTo(nextVel) ? ips[0] : ips[1]);
            }
            double acc = Game.CONSTANTS.getUnitAcceleration() * dt;
            double length = nextVel.length();
            nextVel = length <= acc ? nextVel : nextVel.normalize(length).mul(acc);
            u.setPosition(u.getPosition().add(nextVel));
            nextVel = u.getVelocity().add(nextVel);
            u.setVelocity(nextVel);

            if (u.getRemainingSpawnTime() != null) {
                double respTime = u.getRemainingSpawnTime() - dt;
                u.setRemainingSpawnTime(respTime - Game.EPS <= 0 ? null : respTime);
            }
        }
    }

    public List<Unit> getRes() {
        return res;
    }

}
