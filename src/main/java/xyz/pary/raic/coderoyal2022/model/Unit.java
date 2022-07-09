package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private Integer weapon;
    private int nextShotTick;
    private int[] ammo;
    private int shieldPotions;

    public Unit(int id, int playerId, double health, double shield, int extraLives, Vec2 position, Double remainingSpawnTime,
            Vec2 velocity, Vec2 direction, double aim, Action action, int healthRegenerationStartTick, Integer weapon,
            int nextShotTick, int[] ammo, int shieldPotions) {
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

    public Integer getWeapon() {
        return weapon;
    }

    public void setWeapon(Integer value) {
        this.weapon = value;
    }

    public int getNextShotTick() {
        return nextShotTick;
    }

    public void setNextShotTick(int value) {
        this.nextShotTick = value;
    }

    public int[] getAmmo() {
        return ammo;
    }

    public void setAmmo(int[] value) {
        this.ammo = value;
    }

    public int getShieldPotions() {
        return shieldPotions;
    }

    public void setShieldPotions(int value) {
        this.shieldPotions = value;
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
        Integer weapon;
        if (StreamUtil.readBoolean(stream)) {
            weapon = StreamUtil.readInt(stream);
        } else {
            weapon = null;
        }
        int nextShotTick;
        nextShotTick = StreamUtil.readInt(stream);
        int[] ammo;
        ammo = new int[StreamUtil.readInt(stream)];
        for (int ammoIndex = 0; ammoIndex < ammo.length; ammoIndex++) {
            int ammoElement;
            ammoElement = StreamUtil.readInt(stream);
            ammo[ammoIndex] = ammoElement;
        }
        int shieldPotions;
        shieldPotions = StreamUtil.readInt(stream);
        return new Unit(id, playerId, health, shield, extraLives, position, remainingSpawnTime, velocity, direction, aim, action, healthRegenerationStartTick, weapon, nextShotTick, ammo, shieldPotions);
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
            StreamUtil.writeInt(stream, weapon);
        }
        StreamUtil.writeInt(stream, nextShotTick);
        StreamUtil.writeInt(stream, ammo.length);
        for (int ammoElement : ammo) {
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
        for (int ammoIndex = 0; ammoIndex < ammo.length; ammoIndex++) {
            if (ammoIndex != 0) {
                stringBuilder.append(", ");
            }
            int ammoElement = ammo[ammoIndex];
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
