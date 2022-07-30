package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Projectile implements Point {

    private int id;
    private WeaponType weaponType;
    private int shooterId;
    private int shooterPlayerId;
    private Vec2 position;
    private Vec2 velocity;
    private double lifeTime;

    private int tick;

    public Projectile(int id, int weaponTypeIndex, int shooterId, int shooterPlayerId, Vec2 position, Vec2 velocity, double lifeTime) {
        this.id = id;
        this.weaponType = WeaponType.getByIndex(weaponTypeIndex);
        this.shooterId = shooterId;
        this.shooterPlayerId = shooterPlayerId;
        this.position = position;
        this.velocity = velocity;
        this.lifeTime = lifeTime;
        this.tick = tick;
    }

    public Projectile(Projectile projectile) {
        this.id = projectile.id;
        this.weaponType = projectile.weaponType;
        this.shooterId = projectile.shooterId;
        this.shooterPlayerId = projectile.shooterPlayerId;
        this.position = new Vec2(projectile.position);
        this.velocity = new Vec2(projectile.velocity);
        this.lifeTime = projectile.lifeTime;
        this.tick = projectile.tick;
    }

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType value) {
        this.weaponType = value;
    }

    public int getShooterId() {
        return shooterId;
    }

    public void setShooterId(int value) {
        this.shooterId = value;
    }

    public int getShooterPlayerId() {
        return shooterPlayerId;
    }

    public void setShooterPlayerId(int value) {
        this.shooterPlayerId = value;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 value) {
        this.position = value;
    }

    public Vec2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec2 value) {
        this.velocity = value;
    }

    public double getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(double value) {
        this.lifeTime = value;
    }

    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
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

    public static Projectile readFrom(InputStream stream) throws IOException {
        int id;
        id = StreamUtil.readInt(stream);
        int weaponTypeIndex;
        weaponTypeIndex = StreamUtil.readInt(stream);
        int shooterId;
        shooterId = StreamUtil.readInt(stream);
        int shooterPlayerId;
        shooterPlayerId = StreamUtil.readInt(stream);
        Vec2 position;
        position = Vec2.readFrom(stream);
        Vec2 velocity;
        velocity = Vec2.readFrom(stream);
        double lifeTime;
        lifeTime = StreamUtil.readDouble(stream);
        return new Projectile(id, weaponTypeIndex, shooterId, shooterPlayerId, position, velocity, lifeTime);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, id);
        StreamUtil.writeInt(stream, weaponType.getIndex());
        StreamUtil.writeInt(stream, shooterId);
        StreamUtil.writeInt(stream, shooterPlayerId);
        position.writeTo(stream);
        velocity.writeTo(stream);
        StreamUtil.writeDouble(stream, lifeTime);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Projectile { ");
        stringBuilder.append("id: ");
        stringBuilder.append(String.valueOf(id));
        stringBuilder.append(", ");
        stringBuilder.append("weaponTypeIndex: ");
        stringBuilder.append(String.valueOf(weaponType));
        stringBuilder.append(", ");
        stringBuilder.append("shooterId: ");
        stringBuilder.append(String.valueOf(shooterId));
        stringBuilder.append(", ");
        stringBuilder.append("shooterPlayerId: ");
        stringBuilder.append(String.valueOf(shooterPlayerId));
        stringBuilder.append(", ");
        stringBuilder.append("position: ");
        stringBuilder.append(String.valueOf(position));
        stringBuilder.append(", ");
        stringBuilder.append("velocity: ");
        stringBuilder.append(String.valueOf(velocity));
        stringBuilder.append(", ");
        stringBuilder.append("lifeTime: ");
        stringBuilder.append(String.valueOf(lifeTime));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
