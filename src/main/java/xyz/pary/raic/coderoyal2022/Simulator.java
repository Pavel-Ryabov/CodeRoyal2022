package xyz.pary.raic.coderoyal2022;

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
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.util.GeoUtil;

public class Simulator {

    private static final double DT = 1.0 / Game.CONSTANTS.getTicksPerSecond();
    private static final double ACC = Game.CONSTANTS.getUnitAcceleration() * DT;
    private static final double HALF_UNIT_RADIUS = Game.CONSTANTS.getUnitRadius() / 2;

    private final Unit u;
    private final List<Projectile> projectiles;

    private int tick;

    public Simulator(int tick, Unit u, List<Projectile> projectiles) {
        this.tick = tick;
        this.u = new Unit(u);
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

    private void rotate() {
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

    private void aim() {
        if (u.getRemainingSpawnTime() != null || u.getWeapon() == null || u.getAction() != null) {
            return;
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

    private void move() {
        if (u.isIntersectsWithObstacle()) {
            return;
        }
        moveUnit(u, u.getUnitOrder().getTargetVelocity());
        if (u.getRemainingSpawnTime() != null) {
            double respTime = u.getRemainingSpawnTime() - DT;
            u.setRemainingSpawnTime(respTime - Game.EPS <= 0 ? null : respTime);
        }
    }

    private void projectiles() {
        for (Iterator<Projectile> it = projectiles.iterator(); it.hasNext();) {
            Projectile p = it.next();
            Vec2 nextPos = p.getPosition().add(p.getVelocity().mul(DT));
            if (Runner.di.isAvailable()) {
                Runner.di.add(new DebugData.Circle(
                        p.getPosition(), 0.15, new Color(0, 0, 0, 0.5))
                );
                Runner.di.add(new DebugData.Circle(
                        nextPos, 0.25, new Color(1, 0, 0, 0.5))
                );
            }
            while (p.getTick() <= tick) {
                if (checkIntersection(p, nextPos)) {
                    it.remove();
                    break;
                } else {
                    p.setLifeTime(p.getLifeTime() - DT);
                    if (p.getLifeTime() - Game.EPS <= 0) {
                        it.remove();
                        break;
                    } else {
                        p.setPosition(nextPos);
                    }
                }
                p.setTick(p.getTick() + 1);
            }
        }
    }

    private boolean checkIntersection(Projectile p, Vec2 nextPos) {
        if (p.getShooterId() == u.getId()) {
            return false;
        }
        if (checkIntersectionWithUnit(p, nextPos, u)) {
            u.setShield(u.getShield() - Game.CONSTANTS.getWeapons().get(p.getWeaponType()).getProjectileDamage());
            if (u.getShield() < 0) {
                u.setHealth(u.getHealth() + u.getShield());
                u.setShield(0);
            }
            return true;
        }
        return checkIntersectionWithUObstacles(p, u);
    }

    public int getTick() {
        return tick;
    }

    public Unit getUnit() {
        return u;
    }

    public static void moveUnit(Unit u, Vec2 targetVel) {
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
//                    Vec2 c = GeoUtil.getIntersectionPoint(u.getPosition(), d, u.getPosition().add(u.getDirection()));
            Vec2 c = u.getPosition().add(u.getDirection().mul(d));
            Vec2[] ips = GeoUtil.getIntersectionPoints(u.getPosition(), u.getPosition().add(targetVel), c, r);
            if (ips.length == 2) {
                Vec2 rv = (ips[0].squredDistanceTo(u.getPosition().add(targetVel)) < ips[1].squredDistanceTo(u.getPosition().add(targetVel))
                        ? ips[0] : ips[1])
                        .sub(u.getPosition()).sub(u.getVelocity());;
                nextVel = u.getVelocity().add(rv.length() <= ACC ? rv : rv.normalize().mul(ACC));
            }
//                u.setC(c);
//                u.setR(r);
//                u.setIps(ips);
        }
        u.setPrevPosition(u.getPosition());
        u.setPosition(u.getPosition().add(nextVel.mul(DT)));
        u.setVelocity(nextVel);
        for (Obstacle o : Game.CONSTANTS.getObstacles()) {
            if (u.getRemainingSpawnTime() != null) {
                break;
            } else if (GeoUtil.isIntersect(u.getPosition(), Game.CONSTANTS.getUnitRadius(), o.getPosition(), o.getRadius())) {
                u.setIntersectsWithObstacle(true);
                break;
            }
        }
    }

    public static boolean moveProjectile(Projectile p, Unit u) {
        Vec2 nextPos = p.getPosition().add(p.getVelocity().mul(DT));
        if (checkIntersectionWithUnit(p, nextPos, u)) {
            p.setRemoved(true);
            return true;
        }
        if (checkIntersectionWithUObstacles(p, u)) {
            p.setRemoved(true);
        }
        return false;
    }

    private static boolean checkIntersectionWithUnit(Projectile p, Vec2 nextPos, Unit u) {
        return GeoUtil.isIntersect(u.getPrevPosition(), u.getPosition(), Game.CONSTANTS.getUnitRadius(), p.getPosition(), nextPos, 0.05);
    }

    private static boolean checkIntersectionWithUObstacles(Projectile p, Unit u) {
        for (Obstacle o : Game.CONSTANTS.getObstacles()) {
            double dToO = p.getPosition().squredDistanceTo(o.getPosition());
            double dToU = p.getPosition().squredDistanceTo(u.getPosition());
            double md = p.getVelocity().mul(p.getLifeTime()).squaredLength();
            if (!o.isCanShootThrough() && dToO < dToU && dToO < md) {
                if (GeoUtil.isIntersect(p.getPosition(), p.getPosition().add(p.getVelocity()), o.getPosition(), o.getRadius())) {
                    return true;
                }
            }
        }
        return false;
    }
}
