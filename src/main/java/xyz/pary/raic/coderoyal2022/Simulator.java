package xyz.pary.raic.coderoyal2022;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import xyz.pary.raic.coderoyal2022.debugging.Color;
import xyz.pary.raic.coderoyal2022.debugging.DebugData;
import xyz.pary.raic.coderoyal2022.model.Action;
import xyz.pary.raic.coderoyal2022.model.ActionOrder;
import xyz.pary.raic.coderoyal2022.model.ActionOrderType;
import xyz.pary.raic.coderoyal2022.model.ActionType;
import xyz.pary.raic.coderoyal2022.model.Game;
import xyz.pary.raic.coderoyal2022.model.Obstacle;
import xyz.pary.raic.coderoyal2022.model.Projectile;
import xyz.pary.raic.coderoyal2022.model.Unit;
import xyz.pary.raic.coderoyal2022.model.UnitOrder;
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.util.GeoUtil;

public class Simulator {

    private static final double DT = 1 / Game.CONSTANTS.getTicksPerSecond();
    private static final double ACC = Game.CONSTANTS.getUnitAcceleration() * DT;
    private static final double HALF_UNIT_RADIUS = Game.CONSTANTS.getUnitRadius() / 2;

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
        for (Unit unit : units) {
            unit.setUnitOrder(new UnitOrder(
                    //unit.getDirection().sub(unit.getPosition()).normalize().mul(100),
                    unit.getDirection().normalize().mul(100),
                    new Vec2(0, 0),
                    //unit.getDirection().mul(50).rotate(-20, true),
                    //new Vec2(unit.getDirection().getY(), -unit.getDirection().getX()),
                    //new ActionOrder.Aim(true)
                    null
            ));
        }
        act();
        rotate();
        aim();
        move(di);
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

    private void rotate() {
        for (Unit u : units) {
            if (u.getUnitOrder() != null && u.getUnitOrder().getTargetDirection() != null
                    && u.getUnitOrder().getTargetDirection().squaredLength() >= HALF_UNIT_RADIUS * HALF_UNIT_RADIUS) {
                double rotationSpeed = Game.CONSTANTS.getRotationSpeed();
                if (u.getWeapon() != null) {
                    double aimRotationSpeed = u.getWeaponProperties().getAimRotationSpeed();
                    if (u.getAim() > 0 && u.getAim() < 1) {
                        rotationSpeed = rotationSpeed - (rotationSpeed - aimRotationSpeed) * u.getAim();
                    } else if (u.getAim() == 1) {
                        rotationSpeed = aimRotationSpeed;
                    }
                }
                rotationSpeed *= DT;
                Vec2 dir = u.getDirection();
                Vec2 tDir = u.getUnitOrder().getTargetDirection();
                double angle = Math.toDegrees(Math.atan2(GeoUtil.crossProduct(dir, tDir), GeoUtil.dotProduct(dir, tDir)));
                u.setDirection(dir.rotate(Math.min(rotationSpeed, Math.abs(angle)), angle > 0));
            }
        }
    }

    private void aim() {
        for (Unit u : units) {
            if (u.getRemainingSpawnTime() != null || u.getWeapon() == null || u.getAction() != null) {
                continue;
            }
            if (u.getUnitOrder() == null
                    || u.getUnitOrder().getAction() == null
                    || u.getUnitOrder().getAction().getType() != ActionOrderType.AIM) {
                double aimTime = u.getAim() - 1 / u.getWeaponProperties().getAimTime() * DT;
                u.setAim(aimTime - Game.EPS <= 0 ? 0 : aimTime);
            } else if (u.getAim() < 1) {
                double aimTime = u.getAim() + 1 / u.getWeaponProperties().getAimTime() * DT;
                u.setAim(aimTime + Game.EPS >= 1 ? 1 : aimTime);
            }
            if (u.getAim() == 1 && u.getNextShotTick() <= tick && ((ActionOrder.Aim) u.getUnitOrder().getAction()).isShoot()) {
                u.setNextShotTick(tick + Game.timeToTicks(1 / Game.CONSTANTS.getWeapons().get(u.getWeapon()).getRoundsPerSecond()));
            }
        }
    }

    private void move(DebugInterface di) {
        for (Unit u : units) {
            if (u.isIntersectsWithObstacle()) {
                continue;
            }
            Vec2 targetVel = u.getUnitOrder().getTargetVelocity();
            Vec2 nextVel;
            if (u.getRemainingSpawnTime() != null) {
                //nextVel = normVel.mul(Game.CONSTANTS.getSpawnMovementSpeed());
                double length = targetVel.length();
                nextVel = u.getVelocity().add(length <= ACC ? targetVel : targetVel.normalize(length).mul(ACC));
                length = nextVel.length();
                if (length > Game.CONSTANTS.getSpawnMovementSpeed()) {
                    nextVel.normalize(length).mul(Game.CONSTANTS.getSpawnMovementSpeed());
                }
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
                //v = v0 + at
                //s = v0t + (at2 / 2)
                double r = (forwardSpeed + backwardSpeed) / 2;
                double d = (forwardSpeed - backwardSpeed) / 2;
                Vec2 c = GeoUtil.getIntersect(u.getPosition(), d, u.getPosition().add(u.getDirection()));
                Vec2[] ips = GeoUtil.getIntersectionPoints(u.getPosition(), u.getPosition().add(targetVel), c, r);
                double length = targetVel.length();
                nextVel = u.getVelocity().add(length <= ACC ? targetVel : targetVel.normalize(length).mul(ACC));
                length = nextVel.length();
                if (ips.length == 2) {
                    Vec2 rv = (ips[0].sub(u.getPosition()).squredDistanceTo(nextVel)
                            < ips[1].sub(u.getPosition()).squredDistanceTo(nextVel) ? ips[0] : ips[1])
                            .sub(u.getPosition());
                    double rvLength = rv.length();
                    double rv1Length = ips[0].sub(u.getPosition()).normalize().mul(r).length();
                    double rv2Length = ips[1].sub(u.getPosition()).normalize().mul(r).length();
                    double d1Length = ips[0].squredDistanceTo(nextVel);
                    double d2Length = ips[1].squredDistanceTo(nextVel);
                    nextVel = rv.length() < length + Game.EPS ? rv : nextVel;
                    if (di != null) {
                        di.add(new DebugData.Segment(
                                u.getPosition(), u.getPosition().add(nextVel), 0.25, new Color(0, 0, 0, 0.5))
                        );
                        di.add(new DebugData.Ring(
                                c, r, 0.1, new Color(0, 0, 0, 0.5))
                        );
                        di.add(new DebugData.Circle(
                                u.getPosition(), 1, new Color(1, 0, 0, 0.5))
                        );
                        di.add(new DebugData.Circle(
                                (ips[0].squredDistanceTo(nextVel) < ips[1].squredDistanceTo(nextVel) ? ips[0] : ips[1]).sub(u.getPosition()), 0.1, new Color(0, 0, 1, 0.5))
                        );
                        di.add(new DebugData.Circle(
                                ips[0], 0.25, new Color(1, 0, 0, 0.5))
                        );
                        di.add(new DebugData.Circle(
                                ips[1], 0.25, new Color(1, 0, 0, 0.5))
                        );
                        di.clear();
                    }
                }
            }
            u.setPrevPosition(u.getPosition());
            u.setPosition(u.getPosition().add(nextVel.mul(DT)));
            u.setVelocity(nextVel);

        }
        List<Unit> utc = new ArrayList<>(units);
        for (Obstacle o : Game.CONSTANTS.getObstacles()) {
            for (Iterator<Unit> it = utc.iterator(); it.hasNext();) {
                Unit u = it.next();
                if (u.getRemainingSpawnTime() != null) {
                    it.remove();
                } else if (GeoUtil.isIntersect(u.getPosition(), Game.CONSTANTS.getUnitRadius(), o.getPosition(), o.getRadius())) {
                    u.setIntersectsWithObstacle(true);
                    it.remove();
                }
            }
            if (utc.isEmpty()) {
                break;
            }
        }
        for (Unit u : units) {
            if (u.getRemainingSpawnTime() != null) {
                double respTime = u.getRemainingSpawnTime() - DT;
                u.setRemainingSpawnTime(respTime - Game.EPS <= 0 ? null : respTime);
            }
        }
    }
    
    public void projectiles() {
        for (Iterator<Projectile> it = projectiles.iterator(); it.hasNext();) {
            Projectile p = it.next();
            
        }
        for (Unit u : units) {
            if (u.getRemainingSpawnTime() != null) {
                double respTime = u.getRemainingSpawnTime() - DT;
                u.setRemainingSpawnTime(respTime - Game.EPS <= 0 ? null : respTime);
            }
        }
    }

    public List<Unit> getRes() {
        return res;
    }

}
