package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Game {

    private int myId;
    private Player[] players;
    private int currentTick;
    private List<Unit> myUnits;
    private List<Unit> enemyUnits;
    private Loot[] loot;
    private Projectile[] projectiles;
    private Zone zone;
    private Sound[] sounds;

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

    public Loot[] getLoot() {
        return loot;
    }

    public void setLoot(Loot[] loot) {
        this.loot = loot;
    }

    public Projectile[] getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(Projectile[] projectiles) {
        this.projectiles = projectiles;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Sound[] getSounds() {
        return sounds;
    }

    public void setSounds(Sound[] sounds) {
        this.sounds = sounds;
    }

    public Game(int myId, Player[] players, int currentTick, List<Unit> myUnits, List<Unit> enemyUnits, Loot[] loot,
            Projectile[] projectiles, Zone zone, Sound[] sounds) {
        this.myId = myId;
        this.players = players;
        this.currentTick = currentTick;
        this.myUnits = myUnits;
        this.enemyUnits = enemyUnits;
        this.loot = loot;
        this.projectiles = projectiles;
        this.zone = zone;
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
        int units = StreamUtil.readInt(stream);
        List<Unit> myUnits = new ArrayList<>();
        List<Unit> enemyUnits = new ArrayList<>(units);
        for (int unitsIndex = 0; unitsIndex < units; unitsIndex++) {
            Unit unitsElement = Unit.readFrom(stream);
            if (unitsElement.getPlayerId() == myId) {
                myUnits.add(unitsElement);
            } else {
                enemyUnits.add(unitsElement);
            }
        }
        Loot[] loot;
        loot = new Loot[StreamUtil.readInt(stream)];
        for (int lootIndex = 0; lootIndex < loot.length; lootIndex++) {
            Loot lootElement;
            lootElement = Loot.readFrom(stream);
            loot[lootIndex] = lootElement;
        }
        Projectile[] projectiles;
        projectiles = new Projectile[StreamUtil.readInt(stream)];
        for (int projectilesIndex = 0; projectilesIndex < projectiles.length; projectilesIndex++) {
            Projectile projectilesElement;
            projectilesElement = Projectile.readFrom(stream);
            projectiles[projectilesIndex] = projectilesElement;
        }
        Zone zone;
        zone = Zone.readFrom(stream);
        Sound[] sounds;
        sounds = new Sound[StreamUtil.readInt(stream)];
        for (int soundsIndex = 0; soundsIndex < sounds.length; soundsIndex++) {
            Sound soundsElement;
            soundsElement = Sound.readFrom(stream);
            sounds[soundsIndex] = soundsElement;
        }
        return new Game(myId, players, currentTick, myUnits, enemyUnits, loot, projectiles, zone, sounds);
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
        StreamUtil.writeInt(stream, loot.length);
        for (Loot lootElement : loot) {
            lootElement.writeTo(stream);
        }
        StreamUtil.writeInt(stream, projectiles.length);
        for (Projectile projectilesElement : projectiles) {
            projectilesElement.writeTo(stream);
        }
        zone.writeTo(stream);
        StreamUtil.writeInt(stream, sounds.length);
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
        stringBuilder.append("loot: ");
        stringBuilder.append("[ ");
        for (int lootIndex = 0; lootIndex < loot.length; lootIndex++) {
            if (lootIndex != 0) {
                stringBuilder.append(", ");
            }
            Loot lootElement = loot[lootIndex];
            stringBuilder.append(String.valueOf(lootElement));
        }
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("projectiles: ");
        stringBuilder.append("[ ");
        for (int projectilesIndex = 0; projectilesIndex < projectiles.length; projectilesIndex++) {
            if (projectilesIndex != 0) {
                stringBuilder.append(", ");
            }
            Projectile projectilesElement = projectiles[projectilesIndex];
            stringBuilder.append(String.valueOf(projectilesElement));
        }
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("zone: ");
        stringBuilder.append(String.valueOf(zone));
        stringBuilder.append(", ");
        stringBuilder.append("sounds: ");
        stringBuilder.append("[ ");
        for (int soundsIndex = 0; soundsIndex < sounds.length; soundsIndex++) {
            if (soundsIndex != 0) {
                stringBuilder.append(", ");
            }
            Sound soundsElement = sounds[soundsIndex];
            stringBuilder.append(String.valueOf(soundsElement));
        }
        stringBuilder.append(" ]");
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
