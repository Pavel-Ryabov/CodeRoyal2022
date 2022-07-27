package xyz.pary.raic.coderoyal2022;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import xyz.pary.raic.coderoyal2022.model.Action;
import xyz.pary.raic.coderoyal2022.model.ActionOrder;
import xyz.pary.raic.coderoyal2022.model.ActionOrderType;
import xyz.pary.raic.coderoyal2022.model.ActionType;
import xyz.pary.raic.coderoyal2022.model.Game;
import xyz.pary.raic.coderoyal2022.model.Obstacle;
import xyz.pary.raic.coderoyal2022.model.Projectile;
import xyz.pary.raic.coderoyal2022.model.Unit;
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.util.GeoUtil;

public class Simulator {

    private static final double DT = 1 / Game.CONSTANTS.getTicksPerSecond();
    private static final double ACC = Game.CONSTANTS.getUnitAcceleration() * DT;
    private static final double HALF_UNIT_RADIUS = Game.CONSTANTS.getUnitRadius() / 2;

    private final List<Unit> units;
    private final List<Projectile> projectiles;

    private int tick;

    public Simulator(int tick, List<Unit> units, List<Projectile> projectiles) {
        this.tick = tick;
        this.units = units.stream().map(u -> new Unit(u)).collect(Collectors.toList());
        this.projectiles = projectiles.stream().map(p -> new Projectile(p)).collect(Collectors.toList());
    }

    public void tick() {
        act();
        rotate();
        aim();
        move();
        projectiles();
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

    private void move() {
        for (Unit u : units) {
            if (u.isIntersectsWithObstacle()) {
                continue;
            }
            Vec2 targetVel = u.getUnitOrder().getTargetVelocity();
            Vec2 dv = targetVel.sub(u.getVelocity());
            Vec2 nextVel = dv;
            if (u.getRemainingSpawnTime() != null) {
                double length = nextVel.length();
                nextVel = u.getVelocity().add(length <= ACC ? nextVel : nextVel.normalize(length).mul(ACC));
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
//                Vec2 c = u.getPosition().add(u.getDirection().mul(d));
                double length = nextVel.length();
                nextVel = u.getVelocity().add(length <= ACC ? nextVel : nextVel.normalize(length).mul(ACC));
//                nextVel = u.getVelocity().add(targetVel.sub(u.getVelocity()));
                Vec2[] ips = GeoUtil.getIntersectionPoints(u.getPosition(), u.getPosition().add(nextVel), c, r);
                length = nextVel.length();
                if (ips.length == 2) {
                    Vec2 rv = (ips[0].sub(u.getVelocity()).squredDistanceTo(nextVel)
                            < ips[1].sub(u.getVelocity()).squredDistanceTo(nextVel) ? ips[0] : ips[1])
                            .sub(u.getVelocity());
                    nextVel = rv.length() < length + Game.EPS ? rv : nextVel;
                }
                u.setC(c);
                u.setR(r);
                u.setIps(ips);
            }
            u.setPrevPosition(u.getPosition());
            u.setPosition(u.getPosition().add(
                    nextVel.mul(1.0 / Math.sqrt((dv.getX() * dv.getX() + dv.getY() * dv.getY())))
            ));
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

    private void projectiles() {
        for (Iterator<Projectile> it = projectiles.iterator(); it.hasNext();) {
            Projectile p = it.next();
            Vec2 nextPos = p.getPosition().add(p.getVelocity().mul(DT));
            if (checkIntersection(p, nextPos)) {
                it.remove();
            } else {
                p.setLifeTime(p.getLifeTime() - DT);
                if (p.getLifeTime() - Game.EPS <= 0) {
                    it.remove();
                } else {
                    p.setPosition(nextPos);
                }
            }
        }
    }

    private boolean checkIntersection(Projectile p, Vec2 nextPos) {
        for (Unit u : units) {
            if (GeoUtil.isIntersect(u.getPrevPosition(), u.getPosition(), Game.CONSTANTS.getUnitRadius(), p.getPosition(), nextPos)) {
                u.setShield(u.getShield() - Game.CONSTANTS.getWeapons().get(p.getWeaponType()).getProjectileDamage());
                if (u.getShield() < 0) {
                    u.setHealth(u.getHealth() + u.getShield());
                    u.setShield(0);
                }
                return true;
            }
            for (Obstacle o : Game.CONSTANTS.getObstacles()) {
                if (!o.isCanShootThrough() && p.getPosition().squredDistanceTo(o.getPosition())
                        < p.getPosition().squredDistanceTo(u.getPosition())) {
                    if (GeoUtil.isIntersect(p.getPosition(), u.getPosition(), o.getPosition(), o.getRadius())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getTick() {
        return tick;
    }

    public List<Unit> getUnits() {
        return units;
    }

}
