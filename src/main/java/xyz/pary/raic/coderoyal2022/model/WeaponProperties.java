package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class WeaponProperties {

    private String name;
    private double roundsPerSecond;
    private double spread;
    private double aimTime;
    private double aimFieldOfView;
    private double aimRotationSpeed;
    private double aimMovementSpeedModifier;
    private double projectileSpeed;
    private double projectileDamage;
    private double projectileLifeTime;
    private SoundType shotSoundType;
    private SoundType projectileHitSoundType;
    private int maxInventoryAmmo;

    public WeaponProperties(String name, double roundsPerSecond, double spread, double aimTime, double aimFieldOfView,
            double aimRotationSpeed, double aimMovementSpeedModifier, double projectileSpeed, double projectileDamage,
            double projectileLifeTime, SoundType shotSoundType, SoundType projectileHitSoundType, int maxInventoryAmmo) {
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
        this.shotSoundType = shotSoundType;
        this.projectileHitSoundType = projectileHitSoundType;
        this.maxInventoryAmmo = maxInventoryAmmo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRoundsPerSecond() {
        return roundsPerSecond;
    }

    public void setRoundsPerSecond(double roundsPerSecond) {
        this.roundsPerSecond = roundsPerSecond;
    }

    public double getSpread() {
        return spread;
    }

    public void setSpread(double spread) {
        this.spread = spread;
    }

    public double getAimTime() {
        return aimTime;
    }

    public void setAimTime(double aimTime) {
        this.aimTime = aimTime;
    }

    public double getAimFieldOfView() {
        return aimFieldOfView;
    }

    public void setAimFieldOfView(double aimFieldOfView) {
        this.aimFieldOfView = aimFieldOfView;
    }

    public double getAimRotationSpeed() {
        return aimRotationSpeed;
    }

    public void setAimRotationSpeed(double aimRotationSpeed) {
        this.aimRotationSpeed = aimRotationSpeed;
    }

    public double getAimMovementSpeedModifier() {
        return aimMovementSpeedModifier;
    }

    public void setAimMovementSpeedModifier(double aimMovementSpeedModifier) {
        this.aimMovementSpeedModifier = aimMovementSpeedModifier;
    }

    public double getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(double projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public double getProjectileDamage() {
        return projectileDamage;
    }

    public void setProjectileDamage(double projectileDamage) {
        this.projectileDamage = projectileDamage;
    }

    public double getProjectileLifeTime() {
        return projectileLifeTime;
    }

    public void setProjectileLifeTime(double projectileLifeTime) {
        this.projectileLifeTime = projectileLifeTime;
    }

    public SoundType getShotSoundType() {
        return shotSoundType;
    }

    public void setShotSoundType(SoundType shotSoundType) {
        this.shotSoundType = shotSoundType;
    }

    public SoundType getProjectileHitSoundType() {
        return projectileHitSoundType;
    }

    public void setProjectileHitSoundType(SoundType projectileHitSoundType) {
        this.projectileHitSoundType = projectileHitSoundType;
    }

    public int getMaxInventoryAmmo() {
        return maxInventoryAmmo;
    }

    public void setMaxInventoryAmmo(int maxInventoryAmmo) {
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
        SoundType shotSoundType;
        if (StreamUtil.readBoolean(stream)) {
            shotSoundType = SoundType.getByIndex(StreamUtil.readInt(stream));
        } else {
            shotSoundType = null;
        }
        SoundType projectileHitSoundType;
        if (StreamUtil.readBoolean(stream)) {
            projectileHitSoundType = SoundType.getByIndex(StreamUtil.readInt(stream));
        } else {
            projectileHitSoundType = null;
        }
        int maxInventoryAmmo;
        maxInventoryAmmo = StreamUtil.readInt(stream);
        return new WeaponProperties(name, roundsPerSecond, spread, aimTime, aimFieldOfView, aimRotationSpeed, aimMovementSpeedModifier,
                projectileSpeed, projectileDamage, projectileLifeTime, shotSoundType, projectileHitSoundType, maxInventoryAmmo);
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
        if (shotSoundType == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            StreamUtil.writeInt(stream, shotSoundType.getIndex());
        }
        if (projectileHitSoundType == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            StreamUtil.writeInt(stream, projectileHitSoundType.getIndex());
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
        stringBuilder.append("shotSoundType: ");
        stringBuilder.append(String.valueOf(shotSoundType));
        stringBuilder.append(", ");
        stringBuilder.append("projectileHitSoundType: ");
        stringBuilder.append(String.valueOf(projectileHitSoundType));
        stringBuilder.append(", ");
        stringBuilder.append("maxInventoryAmmo: ");
        stringBuilder.append(String.valueOf(maxInventoryAmmo));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
