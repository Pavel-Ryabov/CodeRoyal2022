package xyz.pary.raic.coderoyal2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import xyz.pary.raic.coderoyal2022.debugging.Color;
import xyz.pary.raic.coderoyal2022.debugging.DebugData;
import xyz.pary.raic.coderoyal2022.model.ActionOrder;
import xyz.pary.raic.coderoyal2022.model.Game;
import xyz.pary.raic.coderoyal2022.model.ItemType;
import xyz.pary.raic.coderoyal2022.model.Loot;
import xyz.pary.raic.coderoyal2022.model.Loot.AmmoLoot;
import xyz.pary.raic.coderoyal2022.model.Loot.ShieldPotionsLoot;
import xyz.pary.raic.coderoyal2022.model.Loot.WeaponLoot;
import xyz.pary.raic.coderoyal2022.model.Obstacle;
import xyz.pary.raic.coderoyal2022.model.Order;
import xyz.pary.raic.coderoyal2022.model.Projectile;
import xyz.pary.raic.coderoyal2022.model.Sound;
import xyz.pary.raic.coderoyal2022.model.Unit;
import xyz.pary.raic.coderoyal2022.model.UnitOrder;
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.model.WeaponProperties;
import xyz.pary.raic.coderoyal2022.model.WeaponType;
import xyz.pary.raic.coderoyal2022.model.Zone;
import xyz.pary.raic.coderoyal2022.util.GeoUtil;

public class MyStrategy implements Strategy {

    public static final int SIM_TICKS = 30;
    public static final int SIM_DIRS = 8;

    private final List<Sound> sounds = new ArrayList<>();
    private final Map<Integer, Projectile> projectiles = new HashMap<>();

    public final DebugInterface di;

    private double DT;

    public MyStrategy(DebugInterface di) {
        this.di = di;
    }

    @Override
    public Order getOrder(Game game) {
        int tick = game.getCurrentTick();
//        System.out.println("tick: " + tick);
        Map<Integer, UnitOrder> orders = new HashMap<>();
        Zone zone = game.getZone();
        for (Iterator<Sound> it = sounds.iterator(); it.hasNext();) {
            Sound next = it.next();
            if (next.getTick() + 6 < tick) {
                it.remove();
            }
        }
        sounds.addAll(game.getSounds());
        projectiles.entrySet().removeIf(e -> e.getValue().getTick() + Game.timeToTicks(e.getValue().getLifeTime()) < tick);
        for (Projectile p : game.getProjectiles()) {
            projectiles.put(p.getId(), p);
        }
        for (Unit unit : game.getMyUnits()) {
            Vec2 target = null;
            Vec2 direction = null;
            Unit enemy = GeoUtil.getNearestTarget(unit, game.getEnemyUnits());
            Sound sound = GeoUtil.getNearestTarget(unit, this.sounds);
            ActionOrder action = null;
            double distanceToEnemy = 0;
            if (enemy != null) {
                distanceToEnemy = unit.getPosition().distanceTo(enemy.getPosition());
            }
            if (unit.getWeapon() == null) {
                WeaponLoot weapon = GeoUtil.getNearestTarget(unit, game.getWeaponLoot().stream().
                        filter(a -> a.getItem().getType() == WeaponType.BOW).collect(Collectors.toList()));
                if (weapon == null) {
                    weapon = GeoUtil.getNearestTarget(unit, game.getWeaponLoot());
                }
                if (weapon != null) {
                    game.getWeaponLoot().remove(weapon);
                    target = weapon.getPosition();
                    direction = weapon.getPosition();
                    if (unit.getPosition().distanceTo(weapon.getPosition()) <= Game.CONSTANTS.getUnitRadius()) {
                        action = new ActionOrder.Pickup(weapon.getId());
                    }
                }
            } else if (unit.getShield() == 0 && unit.getShieldPotions() > 0) {
                action = new ActionOrder.UseShieldPotion();
                if (enemy != null) {
                    direction = enemy.getPosition();
                }
            } else if (unit.getAmmo().get(unit.getWeapon()) == 0) {
                AmmoLoot ammo = GeoUtil.getNearestTarget(unit, game.getAmmoLoot().stream().
                        filter(a -> a.getItem().getWeaponType() == unit.getWeapon()).collect(Collectors.toList()));
                if (ammo != null) {
                    game.getAmmoLoot().remove(ammo);
                    target = ammo.getPosition();
                    direction = ammo.getPosition();
                    if (unit.getPosition().distanceTo(ammo.getPosition()) <= Game.CONSTANTS.getUnitRadius()) {
                        action = new ActionOrder.Pickup(ammo.getId());
                    }
                }
            } else if (sound != null && enemy != null
                    && sound.getPosition().squredDistanceTo(unit.getPosition()) < enemy.getPosition().squredDistanceTo(unit.getPosition())) {
                double angle = GeoUtil.getAngle(sound.getPosition().sub(unit.getPosition()), unit.getDirection());
                if (angle != Double.NaN && angle > Game.CONSTANTS.getWeapons().get(unit.getWeapon()).getAimFieldOfView() / 2) {
                    target = sound.getPosition();
                    direction = sound.getPosition();
                } else {
                    action = new ActionOrder.Aim(true);
                    direction = enemy.getPosition();
                    target = getTargetToEnemy(unit, enemy);
                }
            } else if (enemy == null || distanceToEnemy >= getMaxDistanceToEnemy(unit)) {
                if (!unit.getLoot().isEmpty()) {
                    for (Loot l : unit.getLoot()) {
                        if (l.getItemType() == ItemType.WEAPON && WeaponType.BOW != unit.getWeapon()
                                && ((WeaponLoot) l).getItem().getType() == WeaponType.BOW) {
                            action = new ActionOrder.Pickup(l.getId());
                            break;
                        }
                        if (l.getItemType() == ItemType.AMMO && ((AmmoLoot) l).getItem().getWeaponType() == unit.getWeapon()
                                && unit.getAmmo().get(unit.getWeapon()) < Game.CONSTANTS.getWeapons().get(unit.getWeapon()).getMaxInventoryAmmo()) {
                            action = new ActionOrder.Pickup(l.getId());
                            break;
                        }
                        if (l.getItemType() == ItemType.SHIELD_POTION) {
                            action = new ActionOrder.Pickup(l.getId());
                            break;
                        }
                    }
                }
                if (enemy == null) {
                    WeaponLoot bow = null;
                    if (unit.getWeapon() != WeaponType.BOW) {
                        bow = GeoUtil.getNearestTarget(unit, game.getWeaponLoot().stream().
                                filter(a -> a.getItem().getType() == WeaponType.BOW).collect(Collectors.toList()));
                    }
                    if (bow != null) {
                        game.getWeaponLoot().remove(bow);
                        target = bow.getPosition();
                        direction = bow.getPosition();
                    } else if (unit.getShieldPotions() < Game.CONSTANTS.getMaxShieldPotionsInInventory()) {
                        ShieldPotionsLoot potion = GeoUtil.getNearestTarget(unit, game.getPotionLoot());
                        if (potion != null) {
                            game.getPotionLoot().remove(potion);
                            target = potion.getPosition();
                            direction = potion.getPosition();
                        }
                    } else if (unit.getAmmo().get(unit.getWeapon()) < Game.CONSTANTS.getWeapons().get(unit.getWeapon()).getMaxInventoryAmmo()) {
                        AmmoLoot ammo = GeoUtil.getNearestTarget(unit, game.getAmmoLoot().stream()
                                .filter(a -> a.getItem().getWeaponType() == unit.getWeapon()).collect(Collectors.toList()));
                        if (ammo != null) {
                            game.getAmmoLoot().remove(ammo);
                            target = ammo.getPosition();
                            direction = ammo.getPosition();
                        }
                    } else if (sound != null) {
                        target = sound.getPosition();
                        direction = sound.getPosition();
                    }
                    if (action == null && unit.getShield() <= Game.CONSTANTS.getMaxShield() - Game.CONSTANTS.getShieldPerPotion()) {
                        action = new ActionOrder.UseShieldPotion();
                    }
                } else if (unit.getShield() == 0 && unit.getShieldPotions() > 0) {
                    action = new ActionOrder.UseShieldPotion();
                } else if (action == null && target == null) {
                    action = new ActionOrder.Aim(true);
                    direction = enemy.getPosition();
                    target = getTargetToEnemy(unit, enemy);
                }
            } else {
                action = new ActionOrder.Aim(true);
                direction = enemy.getPosition();
                target = getTargetToEnemy(unit, enemy);
            }
//            System.out.println("enemy: " + enemy);
//            System.out.println("sound: " + sound);
//            System.out.println("target: " + target);
//            System.out.println("direction: " + direction);
//            System.out.println("action: " + action);
            if (target != null && !GeoUtil.isInsideCircle(target, game.getZone().getCurrentCenter(), game.getZone().getCurrentRadius())) {
                target = null;
                unit.setOutsideZone(true);
                if (enemy == null) {
                    direction = null;
                }
            }
            if (enemy != null && action instanceof ActionOrder.Aim) {
                double speed = Game.CONSTANTS.getWeapons().get(unit.getWeapon()).getProjectileSpeed();
                Vec2 startPos = unit.getPosition().add(unit.getDirection());
                Unit enemyCopy = new Unit(enemy);
                int lifeTime = Game.timeToTicks(Game.CONSTANTS.getWeapons().get(unit.getWeapon()).getProjectileLifeTime());
                int ticks = 0;
                while (ticks < Game.timeToTicks(startPos.distanceTo(enemyCopy.getPosition()) / speed) && ticks <= lifeTime) {
                    Simulator.moveUnit(enemyCopy, enemy.getVelocity().mul(100));
                    ticks++;
                }
                if (ticks <= lifeTime) {
                    direction = enemyCopy.getPosition();
                    if (di.isAvailable()) {
                        di.add(new DebugData.Circle(
                                enemyCopy.getPosition(), 1, new Color(1, 0, 0, 0.5))
                        );
                    }
                }
                if (enemy.getRemainingSpawnTime() != null || distanceToEnemy > getMaxDistanceToEnemy(unit)) {
                    ((ActionOrder.Aim) action).setShoot(false);
                } else {
                    for (Obstacle o : Game.CONSTANTS.getObstacles()) {
                        if (!o.isCanShootThrough() && unit.getPosition().squredDistanceTo(o.getPosition()) < distanceToEnemy * distanceToEnemy) {
                            if (GeoUtil.isIntersect(unit.getPosition(), direction, o.getPosition(), o.getRadius())) {
                                ((ActionOrder.Aim) action).setShoot(false);
                                if (di.isAvailable()) {
                                    di.add(new DebugData.Ring(
                                            unit.getPosition(), Game.CONSTANTS.getUnitRadius(), 0.25, new Color(0, 0, 1, 0.5))
                                    );
                                    di.add(new DebugData.Ring(
                                            direction, Game.CONSTANTS.getUnitRadius(), 0.25, new Color(0, 0, 1, 0.5))
                                    );
                                    di.add(new DebugData.Ring(
                                            o.getPosition(), o.getRadius(), 0.25, new Color(1, 0, 0, 0.5))
                                    );
                                }
                                break;
                            }
                        }
                    }
                }
            }
//            System.out.println("target: " + target);
//            System.out.println("direction: " + direction);
//            System.out.println("action: " + action);
            UnitOrder unitOrder = new UnitOrder(
                    target != null
                            ? unit.getPosition().getVelocity(target, Game.CONSTANTS.getMaxUnitForwardSpeed())
                            : unit.getPosition().getVelocity(zone.getNextCenter(), Game.CONSTANTS.getMaxUnitForwardSpeed()),
                    direction != null
                            ? direction.sub(unit.getPosition())
                            : new Vec2(-unit.getDirection().getY(), unit.getDirection().getX()),
                    action
            );

            if (di.isAvailable() && target != null) {
                di.add(new DebugData.Segment(
                        unit.getPosition(), target, 0.25, new Color(0, 0, 1, 0.5))
                );
            }
            if (di.isAvailable() && enemy != null) {
                di.add(new DebugData.Ring(
                        enemy.getPosition(), getMaxDistanceToEnemy(unit), 0.25, new Color(0, 1, 0, 1))
                );
//                di.add(new DebugData.Segment(
//                        enemy.getPosition(), GeoUtil.getIntersectionPoint(enemy.getPosition(),
//                        getMaxDistanceToEnemy(unit), unit.getPosition()), 0.25, new Color(0, 1, 0, 1))
//                );
//                di.add(new DebugData.Segment(
//                        enemy.getPosition(), GeoUtil.getIntersectionPoint(enemy.getPosition(),
//                        getMaxDistanceToEnemy(unit), unit.getPosition().sub(enemy.getPosition()).normalize().mul(getMaxDistanceToEnemy(unit)).rotate15()),
//                        0.25, new Color(1, 0, 0, 1))
//                );
                Vec2 intersectionPoint = GeoUtil.getIntersectionPoint(enemy.getPosition(), getMaxDistanceToEnemy(unit), unit.getPosition());
                di.add(new DebugData.Segment(enemy.getPosition(), intersectionPoint, 0.1, new Color(0, 1, 0, 1)));
                di.add(new DebugData.Segment(enemy.getPosition(), intersectionPoint.sub(enemy.getPosition()).rotate15().add(enemy.getPosition()), 0.1, new Color(1, 0, 0, 1)));
            }
//            if (di.isAvailable() && enemy != null) {
//                di.add(new DebugData.Segment(
//                        enemy.getPosition(), enemy.getPosition().add(enemy.getVelocity().normalize()
//                        .mul(Game.CONSTANTS.getMaxUnitForwardSpeed() / Game.CONSTANTS.getTicksPerSecond())),
//                        0.1, new Color(1, 0, 0, 1))
//                );
//            }
//            if (di.isAvailable() && sound != null) {
//                di.add(new DebugData.Segment(
//                        sound.getPosition(), unit.getPosition(), 0.25, new Color(0, 0, 0, 1))
//                );
//            }
            if (di.isAvailable() && enemy != null) {
                di.add(new DebugData.GradientSegment(
                        unit.getPosition(), new Color(0, 0, 1, 1), unit.getPosition().add(unit.getDirection().mul(30)), new Color(1, 0, 0, 0.5), 0.25)
                );
            }

            if (unit.getRemainingSpawnTime() == null && !unit.isOutsideZone() && !game.getProjectiles().isEmpty()) {
                unitOrder = dodge(tick, unit, game.getProjectiles(), unitOrder);
                if (di.isAvailable()) {
                    di.add(new DebugData.Segment(
                            unit.getPosition(), unit.getPosition().add(unitOrder.getTargetVelocity()), 0.25, new Color(0, 0, 0, 1))
                    );
                }
            }

            orders.put(unit.getId(), unitOrder);
        }

        return new Order(orders);
    }

    private UnitOrder dodge(int tick, Unit unit, List<Projectile> projectiles, UnitOrder order) {
        if (projectiles.isEmpty()) {
            return order;
        }
        double startHp = unit.getHealth() + unit.getShield();
        double maxHp = 0;
        Simulator s = new Simulator(tick, unit, projectiles);
        s.getUnit().setUnitOrder(order);
        maxHp = sim(s);
        if (maxHp == startHp) {
            return order;
        }
        double angle = 360 / SIM_DIRS;
        for (int i = 0; i < SIM_DIRS / 2; i++) {
            s = new Simulator(tick, unit, projectiles);
            Unit u = s.getUnit();
            UnitOrder newOrder = new UnitOrder(order.getTargetDirection().rotate(i * angle), order.getTargetDirection(), order.getAction());
            u.setUnitOrder(newOrder);
            double hp = sim(s);
            if (hp > maxHp) {
                maxHp = hp;
                order = newOrder;
            }
        }
        for (int i = 1; i <= SIM_DIRS / 2; i++) {
            s = new Simulator(tick, unit, projectiles);
            Unit u = s.getUnit();
            UnitOrder newOrder = new UnitOrder(order.getTargetDirection().rotate(i * angle, false), order.getTargetDirection(), order.getAction());
            u.setUnitOrder(newOrder);
            double hp = sim(s);
            if (hp > maxHp) {
                maxHp = hp;
                order = newOrder;
            }
        }
        return order;
    }

    private double sim(Simulator s) {
        s.tick();
        for (int i = 0; i < SIM_TICKS; i++) {
            Unit u = s.getUnit();
            u.setUnitOrder(new UnitOrder(u.getUnitOrder().getTargetVelocity().mul(10), u.getDirection(), u.getUnitOrder().getAction()));
            s.tick();
        }
        double hp = 0;
        Unit u = s.getUnit();
        hp += u.getHealth() + u.getShield();
        return hp;
    }

    private Vec2 getTargetToEnemy(Unit unit, Unit enemy) {
        Vec2 intersectionPoint = GeoUtil.getIntersectionPoint(enemy.getPosition(), getMaxDistanceToEnemy(unit), unit.getPosition());
        return intersectionPoint.sub(enemy.getPosition()).rotate15().add(enemy.getPosition());
    }

    private double getMaxDistanceToEnemy(Unit unit) {
        if (unit.getWeapon() != null) {
            WeaponProperties wp = Game.CONSTANTS.getWeapons().get(unit.getWeapon());
            return wp.getProjectileSpeed() * wp.getProjectileLifeTime() * 0.75;
        }
        return Game.CONSTANTS.getViewDistance() * 0.60;
    }

    @Override
    public void setDT(double DT) {
        this.DT = DT;
    }
}
