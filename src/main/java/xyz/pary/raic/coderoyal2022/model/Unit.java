package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Unit implements Point {

    private int id;
    private int playerId;
    private double health;
    private double shield;
    private int extraLives;
    private Vec2 position;
    private Double remainingSpawnTime;
    private Vec2 velocity;
    private Vec2 direction;
    private double aim;
    private Action action;
    private int healthRegenerationStartTick;
    private WeaponType weapon;
    private int nextShotTick;
    private EnumMap<WeaponType, Integer> ammo;
    private int shieldPotions;
    private List<Loot> loot;
    private List<Projectile> projectiles;
    private UnitOrder unitOrder;

    private Vec2 prevPosition;
    private boolean intersectsWithObstacle;
    
    private Vec2 c ;
    private double r;
    private Vec2[] ips;

    public Unit(int id, int playerId, double health, double shield, int extraLives, Vec2 position, Double remainingSpawnTime,
            Vec2 velocity, Vec2 direction, double aim, Action action, int healthRegenerationStartTick, WeaponType weapon,
            int nextShotTick, EnumMap<WeaponType, Integer> ammo, int shieldPotions) {
        this.id = id;
        this.playerId = playerId;
        this.health = health;
        this.shield = shield;
        this.extraLives = extraLives;
        this.position = position;
        this.remainingSpawnTime = remainingSpawnTime;
        this.velocity = velocity;
        this.direction = direction;
        this.aim = aim;
        this.action = action;
        this.healthRegenerationStartTick = healthRegenerationStartTick;
        this.weapon = weapon;
        this.nextShotTick = nextShotTick;
        this.ammo = ammo;
        this.shieldPotions = shieldPotions;
    }

    public Unit(Unit unit) {
        this.id = unit.id;
        this.playerId = unit.playerId;
        this.health = unit.health;
        this.shield = unit.shield;
        this.extraLives = unit.extraLives;
        this.position = new Vec2(unit.position);
        this.remainingSpawnTime = unit.remainingSpawnTime;
        this.velocity = new Vec2(unit.velocity);
        this.direction = unit.direction;
        this.aim = unit.aim;
        this.action = unit.action != null ? new Action(unit.action) : null;
        this.healthRegenerationStartTick = unit.healthRegenerationStartTick;
        this.weapon = unit.weapon;
        this.nextShotTick = unit.nextShotTick;
        this.ammo = unit.ammo.clone();
        this.shieldPotions = unit.shieldPotions;
        this.unitOrder = unit.unitOrder;
        this.prevPosition = unit.prevPosition;
        
        this.c = unit.c;
        this.r = unit.r;
        this.ips = unit.ips;
    }

    public Vec2 getC() {
        return c;
    }

    public void setC(Vec2 c) {
        this.c = c;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public Vec2[] getIps() {
        return ips;
    }

    public void setIps(Vec2[] ips) {
        this.ips = ips;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int value) {
        this.playerId = value;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double value) {
        this.health = value;
    }

    public double getShield() {
        return shield;
    }

    public void setShield(double value) {
        this.shield = value;
    }

    public int getExtraLives() {
        return extraLives;
    }

    public void setExtraLives(int value) {
        this.extraLives = value;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 value) {
        this.position = value;
    }

    public Double getRemainingSpawnTime() {
        return remainingSpawnTime;
    }

    public void setRemainingSpawnTime(Double value) {
        this.remainingSpawnTime = value;
    }

    public Vec2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec2 value) {
        this.velocity = value;
    }

    public Vec2 getDirection() {
        return direction;
    }

    public void setDirection(Vec2 value) {
        this.direction = value;
    }

    public double getAim() {
        return aim;
    }

    public void setAim(double value) {
        this.aim = value;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action value) {
        this.action = value;
    }

    public int getHealthRegenerationStartTick() {
        return healthRegenerationStartTick;
    }

    public void setHealthRegenerationStartTick(int value) {
        this.healthRegenerationStartTick = value;
    }

    public WeaponType getWeapon() {
        return weapon;
    }

    public void setWeapon(WeaponType value) {
        this.weapon = value;
    }

    public int getNextShotTick() {
        return nextShotTick;
    }

    public void setNextShotTick(int value) {
        this.nextShotTick = value;
    }

    public EnumMap<WeaponType, Integer> getAmmo() {
        return ammo;
    }

    public void setAmmo(EnumMap<WeaponType, Integer> value) {
        this.ammo = value;
    }

    public int getShieldPotions() {
        return shieldPotions;
    }

    public void setShieldPotions(int value) {
        this.shieldPotions = value;
    }

    public List<Loot> getLoot() {
        return loot == null ? Collections.emptyList() : loot;
    }

    public void setLoot(List<Loot> loot) {
        this.loot = loot;
    }

    public void addLoot(Loot loot) {
        if (this.loot == null) {
            this.loot = new ArrayList<>();
        }
        this.loot.add(loot);
    }

    public List<Projectile> getProjectiles() {
        return projectiles == null ? Collections.emptyList() : projectiles;
    }

    public void setProjectiles(List<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public void addProjectile(Projectile projectile) {
        if (this.projectiles == null) {
            this.projectiles = new ArrayList<>();
        }
        this.projectiles.add(projectile);
    }

    public UnitOrder getUnitOrder() {
        return unitOrder;
    }

    public void setUnitOrder(UnitOrder unitOrder) {
        this.unitOrder = unitOrder;
    }

    public WeaponProperties getWeaponProperties() {
        return Game.CONSTANTS.getWeapons().get(this.getWeapon());
    }

    public Vec2 getPrevPosition() {
        return prevPosition;
    }

    public void setPrevPosition(Vec2 prevPosition) {
        this.prevPosition = prevPosition;
    }

    public boolean isIntersectsWithObstacle() {
        return intersectsWithObstacle;
    }

    public void setIntersectsWithObstacle(boolean intersectsWithObstacle) {
        this.intersectsWithObstacle = intersectsWithObstacle;
    }

    @Override
    public double getX() {
        return position.getX();
    }

    @Override
    public void setX(double value) {
        position.setX(value);
    }

    @Override
    public double getY() {
        return position.getY();
    }

    @Override
    public void setY(double value) {
        position.setY(value);
    }

    public static Unit readFrom(InputStream stream) throws IOException {
        int id;
        id = StreamUtil.readInt(stream);
        int playerId;
        playerId = StreamUtil.readInt(stream);
        double health;
        health = StreamUtil.readDouble(stream);
        double shield;
        shield = StreamUtil.readDouble(stream);
        int extraLives;
        extraLives = StreamUtil.readInt(stream);
        Vec2 position;
        position = Vec2.readFrom(stream);
        Double remainingSpawnTime;
        if (StreamUtil.readBoolean(stream)) {
            remainingSpawnTime = StreamUtil.readDouble(stream);
        } else {
            remainingSpawnTime = null;
        }
        Vec2 velocity;
        velocity = Vec2.readFrom(stream);
        Vec2 direction;
        direction = Vec2.readFrom(stream);
        double aim;
        aim = StreamUtil.readDouble(stream);
        Action action;
        if (StreamUtil.readBoolean(stream)) {
            action = Action.readFrom(stream);
        } else {
            action = null;
        }
        int healthRegenerationStartTick;
        healthRegenerationStartTick = StreamUtil.readInt(stream);
        WeaponType weapon;
        if (StreamUtil.readBoolean(stream)) {
            weapon = WeaponType.getByIndex(StreamUtil.readInt(stream));
        } else {
            weapon = null;
        }
        int nextShotTick;
        nextShotTick = StreamUtil.readInt(stream);
        int ammoCount = StreamUtil.readInt(stream);
        EnumMap<WeaponType, Integer> ammo = new EnumMap<>(WeaponType.class);
        for (int ammoIndex = 0; ammoIndex < ammoCount; ammoIndex++) {
            int ammoElement = StreamUtil.readInt(stream);
            ammo.put(WeaponType.getByIndex(ammoIndex), ammoElement);
        }
        int shieldPotions;
        shieldPotions = StreamUtil.readInt(stream);
        return new Unit(id, playerId, health, shield, extraLives, position, remainingSpawnTime, velocity, direction, aim,
                action, healthRegenerationStartTick, weapon, nextShotTick, ammo, shieldPotions);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, id);
        StreamUtil.writeInt(stream, playerId);
        StreamUtil.writeDouble(stream, health);
        StreamUtil.writeDouble(stream, shield);
        StreamUtil.writeInt(stream, extraLives);
        position.writeTo(stream);
        if (remainingSpawnTime == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            StreamUtil.writeDouble(stream, remainingSpawnTime);
        }
        velocity.writeTo(stream);
        direction.writeTo(stream);
        StreamUtil.writeDouble(stream, aim);
        if (action == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            action.writeTo(stream);
        }
        StreamUtil.writeInt(stream, healthRegenerationStartTick);
        if (weapon == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            StreamUtil.writeInt(stream, weapon.getIndex());
        }
        StreamUtil.writeInt(stream, nextShotTick);
        StreamUtil.writeInt(stream, ammo.size());
        for (int ammoElement : ammo.values()) {
            StreamUtil.writeInt(stream, ammoElement);
        }
        StreamUtil.writeInt(stream, shieldPotions);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Unit { ");
        stringBuilder.append("id: ");
        stringBuilder.append(String.valueOf(id));
        stringBuilder.append(", ");
        stringBuilder.append("playerId: ");
        stringBuilder.append(String.valueOf(playerId));
        stringBuilder.append(", ");
        stringBuilder.append("health: ");
        stringBuilder.append(String.valueOf(health));
        stringBuilder.append(", ");
        stringBuilder.append("shield: ");
        stringBuilder.append(String.valueOf(shield));
        stringBuilder.append(", ");
        stringBuilder.append("extraLives: ");
        stringBuilder.append(String.valueOf(extraLives));
        stringBuilder.append(", ");
        stringBuilder.append("position: ");
        stringBuilder.append(String.valueOf(position));
        stringBuilder.append(", ");
        stringBuilder.append("remainingSpawnTime: ");
        stringBuilder.append(String.valueOf(remainingSpawnTime));
        stringBuilder.append(", ");
        stringBuilder.append("velocity: ");
        stringBuilder.append(String.valueOf(velocity));
        stringBuilder.append(", ");
        stringBuilder.append("direction: ");
        stringBuilder.append(String.valueOf(direction));
        stringBuilder.append(", ");
        stringBuilder.append("aim: ");
        stringBuilder.append(String.valueOf(aim));
        stringBuilder.append(", ");
        stringBuilder.append("action: ");
        stringBuilder.append(String.valueOf(action));
        stringBuilder.append(", ");
        stringBuilder.append("healthRegenerationStartTick: ");
        stringBuilder.append(String.valueOf(healthRegenerationStartTick));
        stringBuilder.append(", ");
        stringBuilder.append("weapon: ");
        stringBuilder.append(String.valueOf(weapon));
        stringBuilder.append(", ");
        stringBuilder.append("nextShotTick: ");
        stringBuilder.append(String.valueOf(nextShotTick));
        stringBuilder.append(", ");
        stringBuilder.append("ammo: ");
        stringBuilder.append("[ ");
        for (int ammoIndex = 0; ammoIndex < ammo.size(); ammoIndex++) {
            if (ammoIndex != 0) {
                stringBuilder.append(", ");
            }
            int ammoElement = ammo.get(WeaponType.getByIndex(ammoIndex));
            stringBuilder.append(String.valueOf(ammoElement));
        }
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("shieldPotions: ");
        stringBuilder.append(String.valueOf(shieldPotions));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
