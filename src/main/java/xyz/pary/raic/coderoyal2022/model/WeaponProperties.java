package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class WeaponProperties {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    private double roundsPerSecond;

    public double getRoundsPerSecond() {
        return roundsPerSecond;
    }

    public void setRoundsPerSecond(double value) {
        this.roundsPerSecond = value;
    }

    private double spread;

    public double getSpread() {
        return spread;
    }

    public void setSpread(double value) {
        this.spread = value;
    }

    private double aimTime;

    public double getAimTime() {
        return aimTime;
    }

    public void setAimTime(double value) {
        this.aimTime = value;
    }

    private double aimFieldOfView;

    public double getAimFieldOfView() {
        return aimFieldOfView;
    }

    public void setAimFieldOfView(double value) {
        this.aimFieldOfView = value;
    }

    private double aimRotationSpeed;

    public double getAimRotationSpeed() {
        return aimRotationSpeed;
    }

    public void setAimRotationSpeed(double value) {
        this.aimRotationSpeed = value;
    }

    private double aimMovementSpeedModifier;

    public double getAimMovementSpeedModifier() {
        return aimMovementSpeedModifier;
    }

    public void setAimMovementSpeedModifier(double value) {
        this.aimMovementSpeedModifier = value;
    }

    private double projectileSpeed;

    public double getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(double value) {
        this.projectileSpeed = value;
    }

    private double projectileDamage;

    public double getProjectileDamage() {
        return projectileDamage;
    }

    public void setProjectileDamage(double value) {
        this.projectileDamage = value;
    }

    private double projectileLifeTime;

    public double getProjectileLifeTime() {
        return projectileLifeTime;
    }

    public void setProjectileLifeTime(double value) {
        this.projectileLifeTime = value;
    }

    private Integer shotSoundTypeIndex;

    public Integer getShotSoundTypeIndex() {
        return shotSoundTypeIndex;
    }

    public void setShotSoundTypeIndex(Integer value) {
        this.shotSoundTypeIndex = value;
    }

    private Integer projectileHitSoundTypeIndex;

    public Integer getProjectileHitSoundTypeIndex() {
        return projectileHitSoundTypeIndex;
    }

    public void setProjectileHitSoundTypeIndex(Integer value) {
        this.projectileHitSoundTypeIndex = value;
    }

    private int maxInventoryAmmo;

    public int getMaxInventoryAmmo() {
        return maxInventoryAmmo;
    }

    public void setMaxInventoryAmmo(int value) {
        this.maxInventoryAmmo = value;
    }

    public WeaponProperties(String name, double roundsPerSecond, double spread, double aimTime, double aimFieldOfView, double aimRotationSpeed, double aimMovementSpeedModifier, double projectileSpeed, double projectileDamage, double projectileLifeTime, Integer shotSoundTypeIndex, Integer projectileHitSoundTypeIndex, int maxInventoryAmmo) {
        this.name = name;
        this.roundsPerSecond = roundsPerSecond;
        this.spread = spread;
        this.aimTime = aimTime;
        this.aimFieldOfView = aimFieldOfView;
        this.aimRotationSpeed = aimRotationSpeed;
        this.aimMovementSpeedModifier = aimMovementSpeedModifier;
        this.projectileSpeed = projectileSpeed;
        this.projectileDamage = projectileDamage;
        this.projectileLifeTime = projectileLifeTime;
        this.shotSoundTypeIndex = shotSoundTypeIndex;
        this.projectileHitSoundTypeIndex = projectileHitSoundTypeIndex;
        this.maxInventoryAmmo = maxInventoryAmmo;
    }

    public static WeaponProperties readFrom(InputStream stream) throws IOException {
        String name;
        name = StreamUtil.readString(stream);
        double roundsPerSecond;
        roundsPerSecond = StreamUtil.readDouble(stream);
        double spread;
        spread = StreamUtil.readDouble(stream);
        double aimTime;
        aimTime = StreamUtil.readDouble(stream);
        double aimFieldOfView;
        aimFieldOfView = StreamUtil.readDouble(stream);
        double aimRotationSpeed;
        aimRotationSpeed = StreamUtil.readDouble(stream);
        double aimMovementSpeedModifier;
        aimMovementSpeedModifier = StreamUtil.readDouble(stream);
        double projectileSpeed;
        projectileSpeed = StreamUtil.readDouble(stream);
        double projectileDamage;
        projectileDamage = StreamUtil.readDouble(stream);
        double projectileLifeTime;
        projectileLifeTime = StreamUtil.readDouble(stream);
        Integer shotSoundTypeIndex;
        if (StreamUtil.readBoolean(stream)) {
            shotSoundTypeIndex = StreamUtil.readInt(stream);
        } else {
            shotSoundTypeIndex = null;
        }
        Integer projectileHitSoundTypeIndex;
        if (StreamUtil.readBoolean(stream)) {
            projectileHitSoundTypeIndex = StreamUtil.readInt(stream);
        } else {
            projectileHitSoundTypeIndex = null;
        }
        int maxInventoryAmmo;
        maxInventoryAmmo = StreamUtil.readInt(stream);
        return new WeaponProperties(name, roundsPerSecond, spread, aimTime, aimFieldOfView, aimRotationSpeed, aimMovementSpeedModifier, projectileSpeed, projectileDamage, projectileLifeTime, shotSoundTypeIndex, projectileHitSoundTypeIndex, maxInventoryAmmo);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeString(stream, name);
        StreamUtil.writeDouble(stream, roundsPerSecond);
        StreamUtil.writeDouble(stream, spread);
        StreamUtil.writeDouble(stream, aimTime);
        StreamUtil.writeDouble(stream, aimFieldOfView);
        StreamUtil.writeDouble(stream, aimRotationSpeed);
        StreamUtil.writeDouble(stream, aimMovementSpeedModifier);
        StreamUtil.writeDouble(stream, projectileSpeed);
        StreamUtil.writeDouble(stream, projectileDamage);
        StreamUtil.writeDouble(stream, projectileLifeTime);
        if (shotSoundTypeIndex == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            StreamUtil.writeInt(stream, shotSoundTypeIndex);
        }
        if (projectileHitSoundTypeIndex == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            StreamUtil.writeInt(stream, projectileHitSoundTypeIndex);
        }
        StreamUtil.writeInt(stream, maxInventoryAmmo);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("WeaponProperties { ");
        stringBuilder.append("name: ");
        stringBuilder.append('"' + name + '"');
        stringBuilder.append(", ");
        stringBuilder.append("roundsPerSecond: ");
        stringBuilder.append(String.valueOf(roundsPerSecond));
        stringBuilder.append(", ");
        stringBuilder.append("spread: ");
        stringBuilder.append(String.valueOf(spread));
        stringBuilder.append(", ");
        stringBuilder.append("aimTime: ");
        stringBuilder.append(String.valueOf(aimTime));
        stringBuilder.append(", ");
        stringBuilder.append("aimFieldOfView: ");
        stringBuilder.append(String.valueOf(aimFieldOfView));
        stringBuilder.append(", ");
        stringBuilder.append("aimRotationSpeed: ");
        stringBuilder.append(String.valueOf(aimRotationSpeed));
        stringBuilder.append(", ");
        stringBuilder.append("aimMovementSpeedModifier: ");
        stringBuilder.append(String.valueOf(aimMovementSpeedModifier));
        stringBuilder.append(", ");
        stringBuilder.append("projectileSpeed: ");
        stringBuilder.append(String.valueOf(projectileSpeed));
        stringBuilder.append(", ");
        stringBuilder.append("projectileDamage: ");
        stringBuilder.append(String.valueOf(projectileDamage));
        stringBuilder.append(", ");
        stringBuilder.append("projectileLifeTime: ");
        stringBuilder.append(String.valueOf(projectileLifeTime));
        stringBuilder.append(", ");
        stringBuilder.append("shotSoundTypeIndex: ");
        stringBuilder.append(String.valueOf(shotSoundTypeIndex));
        stringBuilder.append(", ");
        stringBuilder.append("projectileHitSoundTypeIndex: ");
        stringBuilder.append(String.valueOf(projectileHitSoundTypeIndex));
        stringBuilder.append(", ");
        stringBuilder.append("maxInventoryAmmo: ");
        stringBuilder.append(String.valueOf(maxInventoryAmmo));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
