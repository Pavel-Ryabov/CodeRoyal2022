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
import xyz.pary.raic.coderoyal2022.model.Loot.WeaponLoot;
import xyz.pary.raic.coderoyal2022.model.Obstacle;
import xyz.pary.raic.coderoyal2022.model.Order;
import xyz.pary.raic.coderoyal2022.model.Sound;
import xyz.pary.raic.coderoyal2022.model.Unit;
import xyz.pary.raic.coderoyal2022.model.UnitOrder;
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.model.WeaponProperties;
import xyz.pary.raic.coderoyal2022.model.WeaponType;
import xyz.pary.raic.coderoyal2022.model.Zone;
import xyz.pary.raic.coderoyal2022.util.GeoUtil;

public class MyStrategy implements Strategy {

    private final List<Sound> sounds = new ArrayList<>();

    @Override
    public Order getOrder(Game game, DebugInterface debugInterface) {
        System.out.println("tick: " + game.getCurrentTick());
        Map<Integer, UnitOrder> orders = new HashMap<>();
        Zone zone = game.getZone();
        for (Iterator<Sound> it = sounds.iterator(); it.hasNext();) {
            Sound next = it.next();
            if (next.getTick() + 6 < game.getCurrentTick()) {
                it.remove();
            }
        }
        sounds.addAll(game.getSounds());
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
            if (unit.getShield() == 0 && unit.getShieldPotions() > 0) {
                action = new ActionOrder.UseShieldPotion();
            } else if (unit.getAmmo().get(unit.getWeapon()) == 0) {
                Loot ammo = GeoUtil.getNearestTarget(unit, game.getAmmoLoot().stream().
                        filter(a -> a.getItem().getWeaponType() == unit.getWeapon()).collect(Collectors.toList()));
                if (ammo != null) {
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
                    if (unit.getShieldPotions() < Game.CONSTANTS.getMaxShieldPotionsInInventory()) {
                        Loot potion = GeoUtil.getNearestTarget(unit, game.getPotionLoot());
                        if (potion != null) {
                            target = potion.getPosition();
                            direction = potion.getPosition();
                        }
                    } else if (unit.getAmmo().get(unit.getWeapon()) < Game.CONSTANTS.getWeapons().get(unit.getWeapon()).getMaxInventoryAmmo()) {
                        Loot ammo = GeoUtil.getNearestTarget(unit, game.getAmmoLoot().stream()
                                .filter(a -> a.getItem().getWeaponType() == unit.getWeapon()).collect(Collectors.toList()));
                        if (ammo != null) {
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
            System.out.println("enemy: " + enemy);
            System.out.println("sound: " + sound);
            System.out.println("target: " + target);
            System.out.println("direction: " + direction);
            System.out.println("action: " + action);
            if (target != null && !GeoUtil.isInsideCircle(target, game.getZone().getCurrentCenter(), game.getZone().getCurrentRadius())) {
                target = null;
                if (enemy == null) {
                    direction = null;
                }
            }
            if (enemy != null && action instanceof ActionOrder.Aim) {
                for (Obstacle o : Game.CONSTANTS.getObstacles()) {
                    if (!o.isCanShootThrough() && unit.getPosition().distanceTo(o.getPosition()) < distanceToEnemy) {
                        if (GeoUtil.isIntersect(unit.getPosition(), enemy.getPosition(), o.getPosition(), o.getRadius())) {
                            ((ActionOrder.Aim) action).setShoot(false);
                            if (debugInterface != null) {
                                debugInterface.add(new DebugData.Ring(
                                        unit.getPosition(), Game.CONSTANTS.getUnitRadius(), 0.25, new Color(0, 0, 1, 1))
                                );
                                debugInterface.add(new DebugData.Ring(
                                        enemy.getPosition(), Game.CONSTANTS.getUnitRadius(), 0.25, new Color(0, 0, 1, 1))
                                );
                                debugInterface.add(new DebugData.Ring(
                                        o.getPosition(), o.getRadius(), 0.25, new Color(1, 0, 0, 1))
                                );
                            }
                            break;
                        }
                    }
                }
            }
            System.out.println("target: " + target);
            System.out.println("direction: " + direction);
            System.out.println("action: " + action);
            orders.put(unit.getId(), new UnitOrder(
                    target != null
                            ? unit.getPosition().getVelocity(target, Game.CONSTANTS.getMaxUnitForwardSpeed())
                            : unit.getPosition().getVelocity(zone.getNextCenter(), Game.CONSTANTS.getMaxUnitForwardSpeed()),
                    direction != null
                            ? direction.sub(unit.getPosition())
                            : new Vec2(-unit.getDirection().getY(), unit.getDirection().getX()),
                    action
            ));
            if (debugInterface != null && target != null) {
                debugInterface.add(new DebugData.Segment(
                        unit.getPosition(), target, 0.25, new Color(0, 0, 1, 1))
                );
            }
            if (debugInterface != null && enemy != null) {
                debugInterface.add(new DebugData.Ring(
                        enemy.getPosition(), getMaxDistanceToEnemy(unit), 0.25, new Color(0, 1, 0, 1))
                );
//                debugInterface.add(new DebugData.Segment(
//                        enemy.getPosition(), GeoUtil.getIntersectionPoint(enemy.getPosition(),
//                        getMaxDistanceToEnemy(unit), unit.getPosition()), 0.25, new Color(0, 1, 0, 1))
//                );
//                debugInterface.add(new DebugData.Segment(
//                        enemy.getPosition(), GeoUtil.getIntersectionPoint(enemy.getPosition(),
//                        getMaxDistanceToEnemy(unit), unit.getPosition().sub(enemy.getPosition()).normalize().mul(getMaxDistanceToEnemy(unit)).rotate15()),
//                        0.25, new Color(1, 0, 0, 1))
//                );
                Vec2 intersectionPoint = GeoUtil.getIntersectionPoint(enemy.getPosition(), getMaxDistanceToEnemy(unit), unit.getPosition());
                debugInterface.add(new DebugData.Segment(enemy.getPosition(), intersectionPoint, 0.1, new Color(0, 1, 0, 1)));
                debugInterface.add(new DebugData.Segment(enemy.getPosition(), intersectionPoint.sub(enemy.getPosition()).rotate15().add(enemy.getPosition()), 0.1, new Color(1, 0, 0, 1)));
            }
//            if (debugInterface != null && enemy != null) {
//                debugInterface.add(new DebugData.Segment(
//                        enemy.getPosition(), enemy.getPosition().add(enemy.getVelocity().normalize()
//                        .mul(Game.CONSTANTS.getMaxUnitForwardSpeed() / Game.CONSTANTS.getTicksPerSecond())),
//                        0.1, new Color(1, 0, 0, 1))
//                );
//            }
//            if (debugInterface != null && sound != null) {
//                debugInterface.add(new DebugData.Segment(
//                        sound.getPosition(), unit.getPosition(), 0.25, new Color(0, 0, 0, 1))
//                );
//            }
//            if (debugInterface != null && enemy != null) {
//                debugInterface.add(new DebugData.GradientSegment(
//                        enemy.getPosition(), new Color(0, 0, 1, 1), unit.getPosition().add(unit.getDirection().mul(20)), new Color(1, 0, 0, 1), 0.25)
//                );
//            }
        }

        return new Order(orders);
    }

    private Vec2 getTargetToEnemy(Unit unit, Unit enemy) {
        Vec2 intersectionPoint = GeoUtil.getIntersectionPoint(enemy.getPosition(), getMaxDistanceToEnemy(unit), unit.getPosition());
        return intersectionPoint.sub(enemy.getPosition()).rotate15().add(enemy.getPosition());
    }

    private double getMaxDistanceToEnemy(Unit unit) {
        if (unit.getWeapon() != null) {
            WeaponProperties wp = Game.CONSTANTS.getWeapons().get(unit.getWeapon());
            return wp.getProjectileSpeed() * wp.getProjectileLifeTime() * 0.8;
        }
        return Game.CONSTANTS.getViewDistance() * 0.60;
    }
}
