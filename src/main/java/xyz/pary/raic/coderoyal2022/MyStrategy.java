package xyz.pary.raic.coderoyal2022;

import java.util.HashMap;
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
import xyz.pary.raic.coderoyal2022.model.Order;
import xyz.pary.raic.coderoyal2022.model.Sound;
import xyz.pary.raic.coderoyal2022.model.Unit;
import xyz.pary.raic.coderoyal2022.model.UnitOrder;
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.model.WeaponType;
import xyz.pary.raic.coderoyal2022.model.Zone;
import xyz.pary.raic.coderoyal2022.util.GeoUtil;

public class MyStrategy implements Strategy {

    @Override
    public Order getOrder(Game game, DebugInterface debugInterface) {
        Map<Integer, UnitOrder> orders = new HashMap<>();
        Zone zone = game.getZone();
        for (Unit unit : game.getMyUnits()) {
            Vec2 target = null;
            Vec2 direction = null;
            Unit enemy = GeoUtil.getNearestTarget(unit, game.getEnemyUnits());
            Sound sound = GeoUtil.getNearestTarget(unit, game.getSounds());
            ActionOrder action = null;
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
                target = sound.getPosition();
                direction = sound.getPosition();
            } else if (enemy == null || unit.getPosition().distanceTo(enemy.getPosition()) >= Game.CONSTANTS.getViewDistance() * 0.60) {
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
                    target = GeoUtil.getIntersect(enemy.getPosition(), Game.CONSTANTS.getViewDistance() * 0.60,
                            enemy.getPosition().sub(unit.getPosition()).rotate15());
                }
            } else {
                action = new ActionOrder.Aim(true);
                direction = enemy.getPosition();
                target = GeoUtil.getIntersect(enemy.getPosition(),
                        Game.CONSTANTS.getViewDistance() * 0.60, enemy.getPosition().sub(unit.getPosition()).rotate15());
            }
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
                        enemy.getPosition(), Game.CONSTANTS.getViewDistance() * 0.60, 0.25, new Color(0, 1, 0, 1))
                );
                debugInterface.add(new DebugData.Segment(
                        enemy.getPosition(), GeoUtil.getIntersect(enemy.getPosition(), Game.CONSTANTS.getViewDistance() * 0.60, unit.getPosition()), 0.25, new Color(0, 1, 0, 1))
                );
            }
            if (debugInterface != null && enemy != null) {
                debugInterface.add(new DebugData.Segment(
                        enemy.getPosition(), enemy.getPosition().add(enemy.getVelocity().normalize()
                        .mul(Game.CONSTANTS.getMaxUnitForwardSpeed() / Game.CONSTANTS.getTicksPerSecond())),
                        0.1, new Color(1, 0, 0, 1))
                );
            }
        }

        return new Order(orders);
    }
}
