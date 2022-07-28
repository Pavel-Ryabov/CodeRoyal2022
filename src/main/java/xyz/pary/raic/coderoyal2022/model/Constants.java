package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Constants {

    private double ticksPerSecond;
    private int teamSize;
    private double initialZoneRadius;
    private double zoneSpeed;
    private double zoneDamagePerSecond;
    private double spawnTime;
    private double spawnCollisionDamagePerSecond;
    private double lootingTime;
    private int botPlayers;
    private double unitRadius;
    private double unitHealth;
    private double healthRegenerationPerSecond;
    private double healthRegenerationDelay;
    private double maxShield;
    private double spawnShield;
    private int extraLives;
    private double lastRespawnZoneRadius;
    private double fieldOfView;
    private double viewDistance;
    private boolean viewBlocking;
    private double rotationSpeed;
    private double spawnMovementSpeed;
    private double maxUnitForwardSpeed;
    private double maxUnitBackwardSpeed;
    private double unitAcceleration;
    private boolean friendlyFire;
    private double killScore;
    private double damageScoreMultiplier;
    private double scorePerPlace;
    private EnumMap<WeaponType, WeaponProperties> weapons;
    private WeaponType startingWeapon;
    private int startingWeaponAmmo;
    private int maxShieldPotionsInInventory;
    private double shieldPerPotion;
    private double shieldPotionUseTime;
    private EnumMap<SoundType, SoundProperties> sounds;
    private SoundType stepsSoundType;
    private double stepsSoundTravelDistance;
    private Set<Obstacle> obstacles;

    public Constants(double ticksPerSecond, int teamSize, double initialZoneRadius, double zoneSpeed, double zoneDamagePerSecond,
            double spawnTime, double spawnCollisionDamagePerSecond, double lootingTime, int botPlayers, double unitRadius,
            double unitHealth, double healthRegenerationPerSecond, double healthRegenerationDelay, double maxShield,
            double spawnShield, int extraLives, double lastRespawnZoneRadius, double fieldOfView, double viewDistance,
            boolean viewBlocking, double rotationSpeed, double spawnMovementSpeed, double maxUnitForwardSpeed,
            double maxUnitBackwardSpeed, double unitAcceleration, boolean friendlyFire, double killScore,
            double damageScoreMultiplier, double scorePerPlace, EnumMap<WeaponType, WeaponProperties> weapons, WeaponType startingWeapon,
            int startingWeaponAmmo, int maxShieldPotionsInInventory, double shieldPerPotion, double shieldPotionUseTime,
            EnumMap<SoundType, SoundProperties> sounds, SoundType stepsSoundType, double stepsSoundTravelDistance, Set<Obstacle> obstacles) {
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
        this.stepsSoundType = stepsSoundType;
        this.stepsSoundTravelDistance = stepsSoundTravelDistance;
        this.obstacles = obstacles;
    }

    public double getTicksPerSecond() {
        return ticksPerSecond;
    }

    public void setTicksPerSecond(double ticksPerSecond) {
        this.ticksPerSecond = ticksPerSecond;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public double getInitialZoneRadius() {
        return initialZoneRadius;
    }

    public void setInitialZoneRadius(double initialZoneRadius) {
        this.initialZoneRadius = initialZoneRadius;
    }

    public double getZoneSpeed() {
        return zoneSpeed;
    }

    public void setZoneSpeed(double zoneSpeed) {
        this.zoneSpeed = zoneSpeed;
    }

    public double getZoneDamagePerSecond() {
        return zoneDamagePerSecond;
    }

    public void setZoneDamagePerSecond(double zoneDamagePerSecond) {
        this.zoneDamagePerSecond = zoneDamagePerSecond;
    }

    public double getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(double spawnTime) {
        this.spawnTime = spawnTime;
    }

    public double getSpawnCollisionDamagePerSecond() {
        return spawnCollisionDamagePerSecond;
    }

    public void setSpawnCollisionDamagePerSecond(double spawnCollisionDamagePerSecond) {
        this.spawnCollisionDamagePerSecond = spawnCollisionDamagePerSecond;
    }

    public double getLootingTime() {
        return lootingTime;
    }

    public void setLootingTime(double lootingTime) {
        this.lootingTime = lootingTime;
    }

    public int getBotPlayers() {
        return botPlayers;
    }

    public void setBotPlayers(int botPlayers) {
        this.botPlayers = botPlayers;
    }

    public double getUnitRadius() {
        return unitRadius;
    }

    public void setUnitRadius(double unitRadius) {
        this.unitRadius = unitRadius;
    }

    public double getUnitHealth() {
        return unitHealth;
    }

    public void setUnitHealth(double unitHealth) {
        this.unitHealth = unitHealth;
    }

    public double getHealthRegenerationPerSecond() {
        return healthRegenerationPerSecond;
    }

    public void setHealthRegenerationPerSecond(double healthRegenerationPerSecond) {
        this.healthRegenerationPerSecond = healthRegenerationPerSecond;
    }

    public double getHealthRegenerationDelay() {
        return healthRegenerationDelay;
    }

    public void setHealthRegenerationDelay(double healthRegenerationDelay) {
        this.healthRegenerationDelay = healthRegenerationDelay;
    }

    public double getMaxShield() {
        return maxShield;
    }

    public void setMaxShield(double maxShield) {
        this.maxShield = maxShield;
    }

    public double getSpawnShield() {
        return spawnShield;
    }

    public void setSpawnShield(double spawnShield) {
        this.spawnShield = spawnShield;
    }

    public int getExtraLives() {
        return extraLives;
    }

    public void setExtraLives(int extraLives) {
        this.extraLives = extraLives;
    }

    public double getLastRespawnZoneRadius() {
        return lastRespawnZoneRadius;
    }

    public void setLastRespawnZoneRadius(double lastRespawnZoneRadius) {
        this.lastRespawnZoneRadius = lastRespawnZoneRadius;
    }

    public double getFieldOfView() {
        return fieldOfView;
    }

    public void setFieldOfView(double fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    public double getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(double viewDistance) {
        this.viewDistance = viewDistance;
    }

    public boolean isViewBlocking() {
        return viewBlocking;
    }

    public void setViewBlocking(boolean viewBlocking) {
        this.viewBlocking = viewBlocking;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public double getSpawnMovementSpeed() {
        return spawnMovementSpeed;
    }

    public void setSpawnMovementSpeed(double spawnMovementSpeed) {
        this.spawnMovementSpeed = spawnMovementSpeed;
    }

    public double getMaxUnitForwardSpeed() {
        return maxUnitForwardSpeed;
    }

    public void setMaxUnitForwardSpeed(double maxUnitForwardSpeed) {
        this.maxUnitForwardSpeed = maxUnitForwardSpeed;
    }

    public double getMaxUnitBackwardSpeed() {
        return maxUnitBackwardSpeed;
    }

    public void setMaxUnitBackwardSpeed(double maxUnitBackwardSpeed) {
        this.maxUnitBackwardSpeed = maxUnitBackwardSpeed;
    }

    public double getUnitAcceleration() {
        return unitAcceleration;
    }

    public void setUnitAcceleration(double unitAcceleration) {
        this.unitAcceleration = unitAcceleration;
    }

    public boolean isFriendlyFire() {
        return friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
    }

    public double getKillScore() {
        return killScore;
    }

    public void setKillScore(double killScore) {
        this.killScore = killScore;
    }

    public double getDamageScoreMultiplier() {
        return damageScoreMultiplier;
    }

    public void setDamageScoreMultiplier(double damageScoreMultiplier) {
        this.damageScoreMultiplier = damageScoreMultiplier;
    }

    public double getScorePerPlace() {
        return scorePerPlace;
    }

    public void setScorePerPlace(double scorePerPlace) {
        this.scorePerPlace = scorePerPlace;
    }

    public EnumMap<WeaponType, WeaponProperties> getWeapons() {
        return weapons;
    }

    public void setWeapons(EnumMap<WeaponType, WeaponProperties> weapons) {
        this.weapons = weapons;
    }

    public WeaponType getStartingWeapon() {
        return startingWeapon;
    }

    public void setStartingWeapon(WeaponType startingWeapon) {
        this.startingWeapon = startingWeapon;
    }

    public int getStartingWeaponAmmo() {
        return startingWeaponAmmo;
    }

    public void setStartingWeaponAmmo(int startingWeaponAmmo) {
        this.startingWeaponAmmo = startingWeaponAmmo;
    }

    public int getMaxShieldPotionsInInventory() {
        return maxShieldPotionsInInventory;
    }

    public void setMaxShieldPotionsInInventory(int maxShieldPotionsInInventory) {
        this.maxShieldPotionsInInventory = maxShieldPotionsInInventory;
    }

    public double getShieldPerPotion() {
        return shieldPerPotion;
    }

    public void setShieldPerPotion(double shieldPerPotion) {
        this.shieldPerPotion = shieldPerPotion;
    }

    public double getShieldPotionUseTime() {
        return shieldPotionUseTime;
    }

    public void setShieldPotionUseTime(double shieldPotionUseTime) {
        this.shieldPotionUseTime = shieldPotionUseTime;
    }

    public EnumMap<SoundType, SoundProperties> getSounds() {
        return sounds;
    }

    public void setSounds(EnumMap<SoundType, SoundProperties> sounds) {
        this.sounds = sounds;
    }

    public SoundType getStepsSoundType() {
        return stepsSoundType;
    }

    public void setStepsSoundType(SoundType stepsSoundType) {
        this.stepsSoundType = stepsSoundType;
    }

    public double getStepsSoundTravelDistance() {
        return stepsSoundTravelDistance;
    }

    public void setStepsSoundTravelDistance(double stepsSoundTravelDistance) {
        this.stepsSoundTravelDistance = stepsSoundTravelDistance;
    }

    public Set<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(Set<Obstacle> obstacles) {
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
        int weaponCount = StreamUtil.readInt(stream);
        EnumMap<WeaponType, WeaponProperties> weapons = new EnumMap<>(WeaponType.class);
        for (int weaponsIndex = 0; weaponsIndex < weaponCount; weaponsIndex++) {
            WeaponProperties weaponsElement = WeaponProperties.readFrom(stream);
            weapons.put(WeaponType.getByIndex(weaponsIndex), weaponsElement);
        }
        WeaponType startingWeapon;
        if (StreamUtil.readBoolean(stream)) {
            startingWeapon = WeaponType.getByIndex(StreamUtil.readInt(stream));
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
        int soundsCount = StreamUtil.readInt(stream);
        EnumMap<SoundType, SoundProperties> sounds = new EnumMap<>(SoundType.class);
        for (int soundsIndex = 0; soundsIndex < soundsCount; soundsIndex++) {
            SoundProperties soundsElement = SoundProperties.readFrom(stream);
            sounds.put(SoundType.getByIndex(soundsIndex), soundsElement);
        }
        SoundType stepsSoundType;
        if (StreamUtil.readBoolean(stream)) {
            stepsSoundType = SoundType.getByIndex(StreamUtil.readInt(stream));
        } else {
            stepsSoundType = null;
        }
        double stepsSoundTravelDistance;
        stepsSoundTravelDistance = StreamUtil.readDouble(stream);
        int obstacleCount = StreamUtil.readInt(stream);
        Set<Obstacle> obstacles = new HashSet<>((int) Math.ceil(obstacleCount / 0.75));
        for (int obstaclesIndex = 0; obstaclesIndex < obstacleCount; obstaclesIndex++) {
            Obstacle obstaclesElement = Obstacle.readFrom(stream);
            obstacles.add(obstaclesElement);
        }
        return new Constants(ticksPerSecond, teamSize, initialZoneRadius, zoneSpeed, zoneDamagePerSecond, spawnTime,
                spawnCollisionDamagePerSecond, lootingTime, botPlayers, unitRadius, unitHealth, healthRegenerationPerSecond,
                healthRegenerationDelay, maxShield, spawnShield, extraLives, lastRespawnZoneRadius, fieldOfView, viewDistance,
                viewBlocking, rotationSpeed, spawnMovementSpeed, maxUnitForwardSpeed, maxUnitBackwardSpeed, unitAcceleration,
                friendlyFire, killScore, damageScoreMultiplier, scorePerPlace, weapons, startingWeapon, startingWeaponAmmo,
                maxShieldPotionsInInventory, shieldPerPotion, shieldPotionUseTime, sounds, stepsSoundType,
                stepsSoundTravelDistance, obstacles);
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
        StreamUtil.writeInt(stream, weapons.size());
        for (WeaponProperties weaponsElement : weapons.values()) {
            weaponsElement.writeTo(stream);
        }
        if (startingWeapon == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            StreamUtil.writeInt(stream, startingWeapon.getIndex());
        }
        StreamUtil.writeInt(stream, startingWeaponAmmo);
        StreamUtil.writeInt(stream, maxShieldPotionsInInventory);
        StreamUtil.writeDouble(stream, shieldPerPotion);
        StreamUtil.writeDouble(stream, shieldPotionUseTime);
        StreamUtil.writeInt(stream, sounds.size());
        for (SoundProperties soundsElement : sounds.values()) {
            soundsElement.writeTo(stream);
        }
        if (stepsSoundType == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            StreamUtil.writeInt(stream, stepsSoundType.getIndex());
        }
        StreamUtil.writeDouble(stream, stepsSoundTravelDistance);
        StreamUtil.writeInt(stream, obstacles.size());
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
        for (int weaponsIndex = 0; weaponsIndex < weapons.size(); weaponsIndex++) {
            if (weaponsIndex != 0) {
                stringBuilder.append(", ");
            }
            WeaponProperties weaponsElement = weapons.get(WeaponType.getByIndex(weaponsIndex));
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
        for (int soundsIndex = 0; soundsIndex < sounds.size(); soundsIndex++) {
            if (soundsIndex != 0) {
                stringBuilder.append(", ");
            }
            SoundProperties soundsElement = sounds.get(SoundType.getByIndex(soundsIndex));
            stringBuilder.append(String.valueOf(soundsElement));
        }
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("stepsSoundType: ");
        stringBuilder.append(String.valueOf(stepsSoundType));
        stringBuilder.append(", ");
        stringBuilder.append("stepsSoundTravelDistance: ");
        stringBuilder.append(String.valueOf(stepsSoundTravelDistance));
        stringBuilder.append(", ");
        stringBuilder.append("obstacles: ");
        stringBuilder.append("[ ");
        stringBuilder.append(String.valueOf(obstacles));
        stringBuilder.append(" ]");
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
