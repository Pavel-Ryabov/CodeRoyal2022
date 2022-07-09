package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Constants {

    private double ticksPerSecond;

    public double getTicksPerSecond() {
        return ticksPerSecond;
    }

    public void setTicksPerSecond(double value) {
        this.ticksPerSecond = value;
    }

    private int teamSize;

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int value) {
        this.teamSize = value;
    }

    private double initialZoneRadius;

    public double getInitialZoneRadius() {
        return initialZoneRadius;
    }

    public void setInitialZoneRadius(double value) {
        this.initialZoneRadius = value;
    }

    private double zoneSpeed;

    public double getZoneSpeed() {
        return zoneSpeed;
    }

    public void setZoneSpeed(double value) {
        this.zoneSpeed = value;
    }

    private double zoneDamagePerSecond;

    public double getZoneDamagePerSecond() {
        return zoneDamagePerSecond;
    }

    public void setZoneDamagePerSecond(double value) {
        this.zoneDamagePerSecond = value;
    }

    private double spawnTime;

    public double getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(double value) {
        this.spawnTime = value;
    }

    private double spawnCollisionDamagePerSecond;

    public double getSpawnCollisionDamagePerSecond() {
        return spawnCollisionDamagePerSecond;
    }

    public void setSpawnCollisionDamagePerSecond(double value) {
        this.spawnCollisionDamagePerSecond = value;
    }

    private double lootingTime;

    public double getLootingTime() {
        return lootingTime;
    }

    public void setLootingTime(double value) {
        this.lootingTime = value;
    }

    private int botPlayers;

    public int getBotPlayers() {
        return botPlayers;
    }

    public void setBotPlayers(int value) {
        this.botPlayers = value;
    }

    private double unitRadius;

    public double getUnitRadius() {
        return unitRadius;
    }

    public void setUnitRadius(double value) {
        this.unitRadius = value;
    }

    private double unitHealth;

    public double getUnitHealth() {
        return unitHealth;
    }

    public void setUnitHealth(double value) {
        this.unitHealth = value;
    }

    private double healthRegenerationPerSecond;

    public double getHealthRegenerationPerSecond() {
        return healthRegenerationPerSecond;
    }

    public void setHealthRegenerationPerSecond(double value) {
        this.healthRegenerationPerSecond = value;
    }

    private double healthRegenerationDelay;

    public double getHealthRegenerationDelay() {
        return healthRegenerationDelay;
    }

    public void setHealthRegenerationDelay(double value) {
        this.healthRegenerationDelay = value;
    }

    private double maxShield;

    public double getMaxShield() {
        return maxShield;
    }

    public void setMaxShield(double value) {
        this.maxShield = value;
    }

    private double spawnShield;

    public double getSpawnShield() {
        return spawnShield;
    }

    public void setSpawnShield(double value) {
        this.spawnShield = value;
    }

    private int extraLives;

    public int getExtraLives() {
        return extraLives;
    }

    public void setExtraLives(int value) {
        this.extraLives = value;
    }

    private double lastRespawnZoneRadius;

    public double getLastRespawnZoneRadius() {
        return lastRespawnZoneRadius;
    }

    public void setLastRespawnZoneRadius(double value) {
        this.lastRespawnZoneRadius = value;
    }

    private double fieldOfView;

    public double getFieldOfView() {
        return fieldOfView;
    }

    public void setFieldOfView(double value) {
        this.fieldOfView = value;
    }

    private double viewDistance;

    public double getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(double value) {
        this.viewDistance = value;
    }

    private boolean viewBlocking;

    public boolean isViewBlocking() {
        return viewBlocking;
    }

    public void setViewBlocking(boolean value) {
        this.viewBlocking = value;
    }

    private double rotationSpeed;

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double value) {
        this.rotationSpeed = value;
    }

    private double spawnMovementSpeed;

    public double getSpawnMovementSpeed() {
        return spawnMovementSpeed;
    }

    public void setSpawnMovementSpeed(double value) {
        this.spawnMovementSpeed = value;
    }

    private double maxUnitForwardSpeed;

    public double getMaxUnitForwardSpeed() {
        return maxUnitForwardSpeed;
    }

    public void setMaxUnitForwardSpeed(double value) {
        this.maxUnitForwardSpeed = value;
    }

    private double maxUnitBackwardSpeed;

    public double getMaxUnitBackwardSpeed() {
        return maxUnitBackwardSpeed;
    }

    public void setMaxUnitBackwardSpeed(double value) {
        this.maxUnitBackwardSpeed = value;
    }

    private double unitAcceleration;

    public double getUnitAcceleration() {
        return unitAcceleration;
    }

    public void setUnitAcceleration(double value) {
        this.unitAcceleration = value;
    }

    private boolean friendlyFire;

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean value) {
        this.friendlyFire = value;
    }

    private double killScore;

    public double getKillScore() {
        return killScore;
    }

    public void setKillScore(double value) {
        this.killScore = value;
    }

    private double damageScoreMultiplier;

    public double getDamageScoreMultiplier() {
        return damageScoreMultiplier;
    }

    public void setDamageScoreMultiplier(double value) {
        this.damageScoreMultiplier = value;
    }

    private double scorePerPlace;

    public double getScorePerPlace() {
        return scorePerPlace;
    }

    public void setScorePerPlace(double value) {
        this.scorePerPlace = value;
    }

    private WeaponProperties[] weapons;

    public WeaponProperties[] getWeapons() {
        return weapons;
    }

    public void setWeapons(WeaponProperties[] value) {
        this.weapons = value;
    }

    private Integer startingWeapon;

    public Integer getStartingWeapon() {
        return startingWeapon;
    }

    public void setStartingWeapon(Integer value) {
        this.startingWeapon = value;
    }

    private int startingWeaponAmmo;

    public int getStartingWeaponAmmo() {
        return startingWeaponAmmo;
    }

    public void setStartingWeaponAmmo(int value) {
        this.startingWeaponAmmo = value;
    }

    private int maxShieldPotionsInInventory;

    public int getMaxShieldPotionsInInventory() {
        return maxShieldPotionsInInventory;
    }

    public void setMaxShieldPotionsInInventory(int value) {
        this.maxShieldPotionsInInventory = value;
    }

    private double shieldPerPotion;

    public double getShieldPerPotion() {
        return shieldPerPotion;
    }

    public void setShieldPerPotion(double value) {
        this.shieldPerPotion = value;
    }

    private double shieldPotionUseTime;

    public double getShieldPotionUseTime() {
        return shieldPotionUseTime;
    }

    public void setShieldPotionUseTime(double value) {
        this.shieldPotionUseTime = value;
    }

    private SoundProperties[] sounds;

    public SoundProperties[] getSounds() {
        return sounds;
    }

    public void setSounds(SoundProperties[] value) {
        this.sounds = value;
    }

    private Integer stepsSoundTypeIndex;

    public Integer getStepsSoundTypeIndex() {
        return stepsSoundTypeIndex;
    }

    public void setStepsSoundTypeIndex(Integer value) {
        this.stepsSoundTypeIndex = value;
    }

    private double stepsSoundTravelDistance;

    public double getStepsSoundTravelDistance() {
        return stepsSoundTravelDistance;
    }

    public void setStepsSoundTravelDistance(double value) {
        this.stepsSoundTravelDistance = value;
    }

    private Obstacle[] obstacles;

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public void setObstacles(Obstacle[] value) {
        this.obstacles = value;
    }

    public Constants(double ticksPerSecond, int teamSize, double initialZoneRadius, double zoneSpeed, double zoneDamagePerSecond, double spawnTime, double spawnCollisionDamagePerSecond, double lootingTime, int botPlayers, double unitRadius, double unitHealth, double healthRegenerationPerSecond, double healthRegenerationDelay, double maxShield, double spawnShield, int extraLives, double lastRespawnZoneRadius, double fieldOfView, double viewDistance, boolean viewBlocking, double rotationSpeed, double spawnMovementSpeed, double maxUnitForwardSpeed, double maxUnitBackwardSpeed, double unitAcceleration, boolean friendlyFire, double killScore, double damageScoreMultiplier, double scorePerPlace, WeaponProperties[] weapons, Integer startingWeapon, int startingWeaponAmmo, int maxShieldPotionsInInventory, double shieldPerPotion, double shieldPotionUseTime, SoundProperties[] sounds, Integer stepsSoundTypeIndex, double stepsSoundTravelDistance, Obstacle[] obstacles) {
        this.ticksPerSecond = ticksPerSecond;
        this.teamSize = teamSize;
        this.initialZoneRadius = initialZoneRadius;
        this.zoneSpeed = zoneSpeed;
        this.zoneDamagePerSecond = zoneDamagePerSecond;
        this.spawnTime = spawnTime;
        this.spawnCollisionDamagePerSecond = spawnCollisionDamagePerSecond;
        this.lootingTime = lootingTime;
        this.botPlayers = botPlayers;
        this.unitRadius = unitRadius;
        this.unitHealth = unitHealth;
        this.healthRegenerationPerSecond = healthRegenerationPerSecond;
        this.healthRegenerationDelay = healthRegenerationDelay;
        this.maxShield = maxShield;
        this.spawnShield = spawnShield;
        this.extraLives = extraLives;
        this.lastRespawnZoneRadius = lastRespawnZoneRadius;
        this.fieldOfView = fieldOfView;
        this.viewDistance = viewDistance;
        this.viewBlocking = viewBlocking;
        this.rotationSpeed = rotationSpeed;
        this.spawnMovementSpeed = spawnMovementSpeed;
        this.maxUnitForwardSpeed = maxUnitForwardSpeed;
        this.maxUnitBackwardSpeed = maxUnitBackwardSpeed;
        this.unitAcceleration = unitAcceleration;
        this.friendlyFire = friendlyFire;
        this.killScore = killScore;
        this.damageScoreMultiplier = damageScoreMultiplier;
        this.scorePerPlace = scorePerPlace;
        this.weapons = weapons;
        this.startingWeapon = startingWeapon;
        this.startingWeaponAmmo = startingWeaponAmmo;
        this.maxShieldPotionsInInventory = maxShieldPotionsInInventory;
        this.shieldPerPotion = shieldPerPotion;
        this.shieldPotionUseTime = shieldPotionUseTime;
        this.sounds = sounds;
        this.stepsSoundTypeIndex = stepsSoundTypeIndex;
        this.stepsSoundTravelDistance = stepsSoundTravelDistance;
        this.obstacles = obstacles;
    }

    public static Constants readFrom(InputStream stream) throws IOException {
        double ticksPerSecond;
        ticksPerSecond = StreamUtil.readDouble(stream);
        int teamSize;
        teamSize = StreamUtil.readInt(stream);
        double initialZoneRadius;
        initialZoneRadius = StreamUtil.readDouble(stream);
        double zoneSpeed;
        zoneSpeed = StreamUtil.readDouble(stream);
        double zoneDamagePerSecond;
        zoneDamagePerSecond = StreamUtil.readDouble(stream);
        double spawnTime;
        spawnTime = StreamUtil.readDouble(stream);
        double spawnCollisionDamagePerSecond;
        spawnCollisionDamagePerSecond = StreamUtil.readDouble(stream);
        double lootingTime;
        lootingTime = StreamUtil.readDouble(stream);
        int botPlayers;
        botPlayers = StreamUtil.readInt(stream);
        double unitRadius;
        unitRadius = StreamUtil.readDouble(stream);
        double unitHealth;
        unitHealth = StreamUtil.readDouble(stream);
        double healthRegenerationPerSecond;
        healthRegenerationPerSecond = StreamUtil.readDouble(stream);
        double healthRegenerationDelay;
        healthRegenerationDelay = StreamUtil.readDouble(stream);
        double maxShield;
        maxShield = StreamUtil.readDouble(stream);
        double spawnShield;
        spawnShield = StreamUtil.readDouble(stream);
        int extraLives;
        extraLives = StreamUtil.readInt(stream);
        double lastRespawnZoneRadius;
        lastRespawnZoneRadius = StreamUtil.readDouble(stream);
        double fieldOfView;
        fieldOfView = StreamUtil.readDouble(stream);
        double viewDistance;
        viewDistance = StreamUtil.readDouble(stream);
        boolean viewBlocking;
        viewBlocking = StreamUtil.readBoolean(stream);
        double rotationSpeed;
        rotationSpeed = StreamUtil.readDouble(stream);
        double spawnMovementSpeed;
        spawnMovementSpeed = StreamUtil.readDouble(stream);
        double maxUnitForwardSpeed;
        maxUnitForwardSpeed = StreamUtil.readDouble(stream);
        double maxUnitBackwardSpeed;
        maxUnitBackwardSpeed = StreamUtil.readDouble(stream);
        double unitAcceleration;
        unitAcceleration = StreamUtil.readDouble(stream);
        boolean friendlyFire;
        friendlyFire = StreamUtil.readBoolean(stream);
        double killScore;
        killScore = StreamUtil.readDouble(stream);
        double damageScoreMultiplier;
        damageScoreMultiplier = StreamUtil.readDouble(stream);
        double scorePerPlace;
        scorePerPlace = StreamUtil.readDouble(stream);
        WeaponProperties[] weapons;
        weapons = new WeaponProperties[StreamUtil.readInt(stream)];
        for (int weaponsIndex = 0; weaponsIndex < weapons.length; weaponsIndex++) {
            WeaponProperties weaponsElement;
            weaponsElement = WeaponProperties.readFrom(stream);
            weapons[weaponsIndex] = weaponsElement;
        }
        Integer startingWeapon;
        if (StreamUtil.readBoolean(stream)) {
            startingWeapon = StreamUtil.readInt(stream);
        } else {
            startingWeapon = null;
        }
        int startingWeaponAmmo;
        startingWeaponAmmo = StreamUtil.readInt(stream);
        int maxShieldPotionsInInventory;
        maxShieldPotionsInInventory = StreamUtil.readInt(stream);
        double shieldPerPotion;
        shieldPerPotion = StreamUtil.readDouble(stream);
        double shieldPotionUseTime;
        shieldPotionUseTime = StreamUtil.readDouble(stream);
        SoundProperties[] sounds;
        sounds = new SoundProperties[StreamUtil.readInt(stream)];
        for (int soundsIndex = 0; soundsIndex < sounds.length; soundsIndex++) {
            SoundProperties soundsElement;
            soundsElement = SoundProperties.readFrom(stream);
            sounds[soundsIndex] = soundsElement;
        }
        Integer stepsSoundTypeIndex;
        if (StreamUtil.readBoolean(stream)) {
            stepsSoundTypeIndex = StreamUtil.readInt(stream);
        } else {
            stepsSoundTypeIndex = null;
        }
        double stepsSoundTravelDistance;
        stepsSoundTravelDistance = StreamUtil.readDouble(stream);
        Obstacle[] obstacles;
        obstacles = new Obstacle[StreamUtil.readInt(stream)];
        for (int obstaclesIndex = 0; obstaclesIndex < obstacles.length; obstaclesIndex++) {
            Obstacle obstaclesElement;
            obstaclesElement = Obstacle.readFrom(stream);
            obstacles[obstaclesIndex] = obstaclesElement;
        }
        return new Constants(ticksPerSecond, teamSize, initialZoneRadius, zoneSpeed, zoneDamagePerSecond, spawnTime, spawnCollisionDamagePerSecond, lootingTime, botPlayers, unitRadius, unitHealth, healthRegenerationPerSecond, healthRegenerationDelay, maxShield, spawnShield, extraLives, lastRespawnZoneRadius, fieldOfView, viewDistance, viewBlocking, rotationSpeed, spawnMovementSpeed, maxUnitForwardSpeed, maxUnitBackwardSpeed, unitAcceleration, friendlyFire, killScore, damageScoreMultiplier, scorePerPlace, weapons, startingWeapon, startingWeaponAmmo, maxShieldPotionsInInventory, shieldPerPotion, shieldPotionUseTime, sounds, stepsSoundTypeIndex, stepsSoundTravelDistance, obstacles);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeDouble(stream, ticksPerSecond);
        StreamUtil.writeInt(stream, teamSize);
        StreamUtil.writeDouble(stream, initialZoneRadius);
        StreamUtil.writeDouble(stream, zoneSpeed);
        StreamUtil.writeDouble(stream, zoneDamagePerSecond);
        StreamUtil.writeDouble(stream, spawnTime);
        StreamUtil.writeDouble(stream, spawnCollisionDamagePerSecond);
        StreamUtil.writeDouble(stream, lootingTime);
        StreamUtil.writeInt(stream, botPlayers);
        StreamUtil.writeDouble(stream, unitRadius);
        StreamUtil.writeDouble(stream, unitHealth);
        StreamUtil.writeDouble(stream, healthRegenerationPerSecond);
        StreamUtil.writeDouble(stream, healthRegenerationDelay);
        StreamUtil.writeDouble(stream, maxShield);
        StreamUtil.writeDouble(stream, spawnShield);
        StreamUtil.writeInt(stream, extraLives);
        StreamUtil.writeDouble(stream, lastRespawnZoneRadius);
        StreamUtil.writeDouble(stream, fieldOfView);
        StreamUtil.writeDouble(stream, viewDistance);
        StreamUtil.writeBoolean(stream, viewBlocking);
        StreamUtil.writeDouble(stream, rotationSpeed);
        StreamUtil.writeDouble(stream, spawnMovementSpeed);
        StreamUtil.writeDouble(stream, maxUnitForwardSpeed);
        StreamUtil.writeDouble(stream, maxUnitBackwardSpeed);
        StreamUtil.writeDouble(stream, unitAcceleration);
        StreamUtil.writeBoolean(stream, friendlyFire);
        StreamUtil.writeDouble(stream, killScore);
        StreamUtil.writeDouble(stream, damageScoreMultiplier);
        StreamUtil.writeDouble(stream, scorePerPlace);
        StreamUtil.writeInt(stream, weapons.length);
        for (WeaponProperties weaponsElement : weapons) {
            weaponsElement.writeTo(stream);
        }
        if (startingWeapon == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            StreamUtil.writeInt(stream, startingWeapon);
        }
        StreamUtil.writeInt(stream, startingWeaponAmmo);
        StreamUtil.writeInt(stream, maxShieldPotionsInInventory);
        StreamUtil.writeDouble(stream, shieldPerPotion);
        StreamUtil.writeDouble(stream, shieldPotionUseTime);
        StreamUtil.writeInt(stream, sounds.length);
        for (SoundProperties soundsElement : sounds) {
            soundsElement.writeTo(stream);
        }
        if (stepsSoundTypeIndex == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            StreamUtil.writeInt(stream, stepsSoundTypeIndex);
        }
        StreamUtil.writeDouble(stream, stepsSoundTravelDistance);
        StreamUtil.writeInt(stream, obstacles.length);
        for (Obstacle obstaclesElement : obstacles) {
            obstaclesElement.writeTo(stream);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Constants { ");
        stringBuilder.append("ticksPerSecond: ");
        stringBuilder.append(String.valueOf(ticksPerSecond));
        stringBuilder.append(", ");
        stringBuilder.append("teamSize: ");
        stringBuilder.append(String.valueOf(teamSize));
        stringBuilder.append(", ");
        stringBuilder.append("initialZoneRadius: ");
        stringBuilder.append(String.valueOf(initialZoneRadius));
        stringBuilder.append(", ");
        stringBuilder.append("zoneSpeed: ");
        stringBuilder.append(String.valueOf(zoneSpeed));
        stringBuilder.append(", ");
        stringBuilder.append("zoneDamagePerSecond: ");
        stringBuilder.append(String.valueOf(zoneDamagePerSecond));
        stringBuilder.append(", ");
        stringBuilder.append("spawnTime: ");
        stringBuilder.append(String.valueOf(spawnTime));
        stringBuilder.append(", ");
        stringBuilder.append("spawnCollisionDamagePerSecond: ");
        stringBuilder.append(String.valueOf(spawnCollisionDamagePerSecond));
        stringBuilder.append(", ");
        stringBuilder.append("lootingTime: ");
        stringBuilder.append(String.valueOf(lootingTime));
        stringBuilder.append(", ");
        stringBuilder.append("botPlayers: ");
        stringBuilder.append(String.valueOf(botPlayers));
        stringBuilder.append(", ");
        stringBuilder.append("unitRadius: ");
        stringBuilder.append(String.valueOf(unitRadius));
        stringBuilder.append(", ");
        stringBuilder.append("unitHealth: ");
        stringBuilder.append(String.valueOf(unitHealth));
        stringBuilder.append(", ");
        stringBuilder.append("healthRegenerationPerSecond: ");
        stringBuilder.append(String.valueOf(healthRegenerationPerSecond));
        stringBuilder.append(", ");
        stringBuilder.append("healthRegenerationDelay: ");
        stringBuilder.append(String.valueOf(healthRegenerationDelay));
        stringBuilder.append(", ");
        stringBuilder.append("maxShield: ");
        stringBuilder.append(String.valueOf(maxShield));
        stringBuilder.append(", ");
        stringBuilder.append("spawnShield: ");
        stringBuilder.append(String.valueOf(spawnShield));
        stringBuilder.append(", ");
        stringBuilder.append("extraLives: ");
        stringBuilder.append(String.valueOf(extraLives));
        stringBuilder.append(", ");
        stringBuilder.append("lastRespawnZoneRadius: ");
        stringBuilder.append(String.valueOf(lastRespawnZoneRadius));
        stringBuilder.append(", ");
        stringBuilder.append("fieldOfView: ");
        stringBuilder.append(String.valueOf(fieldOfView));
        stringBuilder.append(", ");
        stringBuilder.append("viewDistance: ");
        stringBuilder.append(String.valueOf(viewDistance));
        stringBuilder.append(", ");
        stringBuilder.append("viewBlocking: ");
        stringBuilder.append(String.valueOf(viewBlocking));
        stringBuilder.append(", ");
        stringBuilder.append("rotationSpeed: ");
        stringBuilder.append(String.valueOf(rotationSpeed));
        stringBuilder.append(", ");
        stringBuilder.append("spawnMovementSpeed: ");
        stringBuilder.append(String.valueOf(spawnMovementSpeed));
        stringBuilder.append(", ");
        stringBuilder.append("maxUnitForwardSpeed: ");
        stringBuilder.append(String.valueOf(maxUnitForwardSpeed));
        stringBuilder.append(", ");
        stringBuilder.append("maxUnitBackwardSpeed: ");
        stringBuilder.append(String.valueOf(maxUnitBackwardSpeed));
        stringBuilder.append(", ");
        stringBuilder.append("unitAcceleration: ");
        stringBuilder.append(String.valueOf(unitAcceleration));
        stringBuilder.append(", ");
        stringBuilder.append("friendlyFire: ");
        stringBuilder.append(String.valueOf(friendlyFire));
        stringBuilder.append(", ");
        stringBuilder.append("killScore: ");
        stringBuilder.append(String.valueOf(killScore));
        stringBuilder.append(", ");
        stringBuilder.append("damageScoreMultiplier: ");
        stringBuilder.append(String.valueOf(damageScoreMultiplier));
        stringBuilder.append(", ");
        stringBuilder.append("scorePerPlace: ");
        stringBuilder.append(String.valueOf(scorePerPlace));
        stringBuilder.append(", ");
        stringBuilder.append("weapons: ");
        stringBuilder.append("[ ");
        for (int weaponsIndex = 0; weaponsIndex < weapons.length; weaponsIndex++) {
            if (weaponsIndex != 0) {
                stringBuilder.append(", ");
            }
            WeaponProperties weaponsElement = weapons[weaponsIndex];
            stringBuilder.append(String.valueOf(weaponsElement));
        }
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("startingWeapon: ");
        stringBuilder.append(String.valueOf(startingWeapon));
        stringBuilder.append(", ");
        stringBuilder.append("startingWeaponAmmo: ");
        stringBuilder.append(String.valueOf(startingWeaponAmmo));
        stringBuilder.append(", ");
        stringBuilder.append("maxShieldPotionsInInventory: ");
        stringBuilder.append(String.valueOf(maxShieldPotionsInInventory));
        stringBuilder.append(", ");
        stringBuilder.append("shieldPerPotion: ");
        stringBuilder.append(String.valueOf(shieldPerPotion));
        stringBuilder.append(", ");
        stringBuilder.append("shieldPotionUseTime: ");
        stringBuilder.append(String.valueOf(shieldPotionUseTime));
        stringBuilder.append(", ");
        stringBuilder.append("sounds: ");
        stringBuilder.append("[ ");
        for (int soundsIndex = 0; soundsIndex < sounds.length; soundsIndex++) {
            if (soundsIndex != 0) {
                stringBuilder.append(", ");
            }
            SoundProperties soundsElement = sounds[soundsIndex];
            stringBuilder.append(String.valueOf(soundsElement));
        }
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("stepsSoundTypeIndex: ");
        stringBuilder.append(String.valueOf(stepsSoundTypeIndex));
        stringBuilder.append(", ");
        stringBuilder.append("stepsSoundTravelDistance: ");
        stringBuilder.append(String.valueOf(stepsSoundTravelDistance));
        stringBuilder.append(", ");
        stringBuilder.append("obstacles: ");
        stringBuilder.append("[ ");
        for (int obstaclesIndex = 0; obstaclesIndex < obstacles.length; obstaclesIndex++) {
            if (obstaclesIndex != 0) {
                stringBuilder.append(", ");
            }
            Obstacle obstaclesElement = obstacles[obstaclesIndex];
            stringBuilder.append(String.valueOf(obstaclesElement));
        }
        stringBuilder.append(" ]");
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
