package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import xyz.pary.raic.coderoyal2022.util.GeoUtil;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Game {

    public static final double EPS = 1e-6;

    public static Constants CONSTANTS;

    public static int timeToTicks(double t) {
        return (int) Math.ceil(t * CONSTANTS.getTicksPerSecond());
    }

    private int myId;
    private Player[] players;
    private int currentTick;
    private List<Unit> myUnits;
    private List<Unit> enemyUnits;
    private List<Loot.AmmoLoot> ammoLoot;
    private List<Loot.ShieldPotionsLoot> potionLoot;
    private List<Loot.WeaponLoot> weaponLoot;
    private List<Projectile> projectiles;
    private Zone zone;
    private List<Sound> sounds;

    public Game(int myId, Player[] players, int currentTick, List<Unit> myUnits, List<Unit> enemyUnits,
            List<Loot.AmmoLoot> ammoLoot, List<Loot.ShieldPotionsLoot> potionLoot, List<Loot.WeaponLoot> weaponLoot,
            List<Projectile> projectiles, Zone zone, List<Sound> sounds) {
        this.myId = myId;
        this.players = players;
        this.currentTick = currentTick;
        this.myUnits = myUnits;
        this.enemyUnits = enemyUnits;
        this.ammoLoot = ammoLoot;
        this.potionLoot = potionLoot;
        this.weaponLoot = weaponLoot;
        this.projectiles = projectiles;
        this.zone = zone;
        this.sounds = sounds;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }

    public List<Unit> getMyUnits() {
        return myUnits;
    }

    public void setMyUnits(List<Unit> myUnits) {
        this.myUnits = myUnits;
    }

    public List<Unit> getEnemyUnits() {
        return enemyUnits;
    }

    public void setEnemyUnits(List<Unit> enemyUnits) {
        this.enemyUnits = enemyUnits;
    }

    public List<Loot.AmmoLoot> getAmmoLoot() {
        return ammoLoot;
    }

    public void setAmmoLoot(List<Loot.AmmoLoot> ammoLoot) {
        this.ammoLoot = ammoLoot;
    }

    public List<Loot.ShieldPotionsLoot> getPotionLoot() {
        return potionLoot;
    }

    public void setPotionLoot(List<Loot.ShieldPotionsLoot> potionLoot) {
        this.potionLoot = potionLoot;
    }

    public List<Loot.WeaponLoot> getWeaponLoot() {
        return weaponLoot;
    }

    public void setWeaponLoot(List<Loot.WeaponLoot> weaponLoot) {
        this.weaponLoot = weaponLoot;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(List<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public List<Sound> getSounds() {
        return sounds;
    }

    public void setSounds(List<Sound> sounds) {
        this.sounds = sounds;
    }

    public static Game readFrom(InputStream stream) throws IOException {
        int myId;
        myId = StreamUtil.readInt(stream);
        Player[] players;
        players = new Player[StreamUtil.readInt(stream)];
        for (int playersIndex = 0; playersIndex < players.length; playersIndex++) {
            Player playersElement;
            playersElement = Player.readFrom(stream);
            players[playersIndex] = playersElement;
        }
        int currentTick;
        currentTick = StreamUtil.readInt(stream);
        int unitCount = StreamUtil.readInt(stream);
        List<Unit> myUnits = new ArrayList<>();
        List<Unit> enemyUnits = new ArrayList<>(unitCount);
        for (int unitsIndex = 0; unitsIndex < unitCount; unitsIndex++) {
            Unit unitsElement = Unit.readFrom(stream);
            if (unitsElement.getPlayerId() == myId) {
                myUnits.add(unitsElement);
            } else {
                enemyUnits.add(unitsElement);
            }
        }
        int lootCount = StreamUtil.readInt(stream);
        List<Loot.AmmoLoot> ammoLoot = new ArrayList<>();
        List<Loot.ShieldPotionsLoot> potionsLoot = new ArrayList<>();
        List<Loot.WeaponLoot> weaponLoot = new ArrayList<>();
        for (int lootIndex = 0; lootIndex < lootCount; lootIndex++) {
            Loot lootElement = Loot.readFrom(stream);
            switch (lootElement.getItemType()) {
                case AMMO:
                    ammoLoot.add((Loot.AmmoLoot) lootElement);
                    break;
                case SHIELD_POTION:
                    potionsLoot.add((Loot.ShieldPotionsLoot) lootElement);
                    break;
                case WEAPON:
                    weaponLoot.add((Loot.WeaponLoot) lootElement);
                    break;
            }
            for (Unit u : myUnits) {
                if (GeoUtil.isInsideCircle(lootElement.getPosition(), u.getPosition(), Game.CONSTANTS.getUnitRadius())) {
                    u.addLoot(lootElement);
                }
            }
        }
        int projectileCount = StreamUtil.readInt(stream);
        List<Projectile> projectiles = new ArrayList<>(projectileCount);
        for (int projectilesIndex = 0; projectilesIndex < projectileCount; projectilesIndex++) {
            Projectile projectilesElement = Projectile.readFrom(stream);
            if (projectilesElement.getShooterPlayerId() != myId || Game.CONSTANTS.isFriendlyFire()) {
                double minDist = Double.MAX_VALUE;
                double dist;
                for (Unit u : myUnits) {
                    dist = u.getPosition().squredDistanceTo(projectilesElement.getPosition());
                    if (dist < minDist) {
                        minDist = dist;
                    }
                }
                dist = projectilesElement.getLifeTime() * Game.CONSTANTS.getWeapons().get(projectilesElement.getWeaponType()).getProjectileSpeed();
                if (minDist <= dist * dist) {
                    projectilesElement.setTick(currentTick);
                    projectiles.add(projectilesElement);
                }
            }
        }
        Zone zone;
        zone = Zone.readFrom(stream);
        for (Iterator<Obstacle> it = Game.CONSTANTS.getObstacles().iterator(); it.hasNext();) {
            Obstacle o = it.next();
            if (GeoUtil.squaredDistance(o.getPosition(), zone.getCurrentCenter())
                    //(o.getRadius() + zone.getCurrentRadius()) * (o.getRadius() + zone.getCurrentRadius())) {
                    > zone.getCurrentRadius() * zone.getCurrentRadius()) {
                it.remove();
            }
        }
        int soundCount = StreamUtil.readInt(stream);
        List<Sound> sounds = new ArrayList<>(soundCount);
        for (int soundsIndex = 0; soundsIndex < soundCount; soundsIndex++) {
            Sound soundsElement = Sound.readFrom(stream, currentTick);
            sounds.add(soundsElement);
        }
        return new Game(myId, players, currentTick, myUnits, enemyUnits, ammoLoot, potionsLoot, weaponLoot, projectiles, zone, sounds);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, myId);
        StreamUtil.writeInt(stream, players.length);
        for (Player playersElement : players) {
            playersElement.writeTo(stream);
        }
        StreamUtil.writeInt(stream, currentTick);
        StreamUtil.writeInt(stream, myUnits.size() + enemyUnits.size());
        for (Unit unitsElement : myUnits) {
            unitsElement.writeTo(stream);
        }
        for (Unit unitsElement : enemyUnits) {
            unitsElement.writeTo(stream);
        }
        StreamUtil.writeInt(stream, ammoLoot.size() + potionLoot.size() + weaponLoot.size());
        for (Loot lootElement : ammoLoot) {
            lootElement.writeTo(stream);
        }
        for (Loot lootElement : potionLoot) {
            lootElement.writeTo(stream);
        }
        for (Loot lootElement : weaponLoot) {
            lootElement.writeTo(stream);
        }
        StreamUtil.writeInt(stream, projectiles.size());
        for (Projectile projectilesElement : projectiles) {
            projectilesElement.writeTo(stream);
        }
        zone.writeTo(stream);
        StreamUtil.writeInt(stream, sounds.size());
        for (Sound soundsElement : sounds) {
            soundsElement.writeTo(stream);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Game { ");
        stringBuilder.append("myId: ");
        stringBuilder.append(String.valueOf(myId));
        stringBuilder.append(", ");
        stringBuilder.append("players: ");
        stringBuilder.append("[ ");
        for (int playersIndex = 0; playersIndex < players.length; playersIndex++) {
            if (playersIndex != 0) {
                stringBuilder.append(", ");
            }
            Player playersElement = players[playersIndex];
            stringBuilder.append(String.valueOf(playersElement));
        }
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("currentTick: ");
        stringBuilder.append(String.valueOf(currentTick));
        stringBuilder.append(", ");
        stringBuilder.append("myUnits: ");
        stringBuilder.append("[ ");
        stringBuilder.append(myUnits.stream().map(u -> u.toString()).collect(Collectors.joining(", ")));
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("enemyUnits: ");
        stringBuilder.append("[ ");
        stringBuilder.append(enemyUnits.stream().map(u -> u.toString()).collect(Collectors.joining(", ")));
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("ammoLoot: ");
        stringBuilder.append("[ ");
        stringBuilder.append(ammoLoot.stream().map(u -> u.toString()).collect(Collectors.joining(", ")));
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("potionLoot: ");
        stringBuilder.append("[ ");
        stringBuilder.append(potionLoot.stream().map(u -> u.toString()).collect(Collectors.joining(", ")));
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("weaponLoot: ");
        stringBuilder.append("[ ");
        stringBuilder.append(weaponLoot.stream().map(u -> u.toString()).collect(Collectors.joining(", ")));
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("projectiles: ");
        stringBuilder.append("[ ");
        stringBuilder.append(projectiles.stream().map(u -> u.toString()).collect(Collectors.joining(", ")));
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("zone: ");
        stringBuilder.append(String.valueOf(zone));
        stringBuilder.append(", ");
        stringBuilder.append("sounds: ");
        stringBuilder.append("[ ");
        stringBuilder.append(sounds.stream().map(u -> u.toString()).collect(Collectors.joining(", ")));
        stringBuilder.append(" ]");
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
