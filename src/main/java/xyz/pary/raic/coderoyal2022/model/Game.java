package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Game {

    private int myId;

    public int getMyId() {
        return myId;
    }

    public void setMyId(int value) {
        this.myId = value;
    }

    private Player[] players;

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] value) {
        this.players = value;
    }

    private int currentTick;

    public int getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(int value) {
        this.currentTick = value;
    }

    private Unit[] units;

    public Unit[] getUnits() {
        return units;
    }

    public void setUnits(Unit[] value) {
        this.units = value;
    }

    private Loot[] loot;

    public Loot[] getLoot() {
        return loot;
    }

    public void setLoot(Loot[] value) {
        this.loot = value;
    }

    private Projectile[] projectiles;

    public Projectile[] getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(Projectile[] value) {
        this.projectiles = value;
    }

    private Zone zone;

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone value) {
        this.zone = value;
    }

    private Sound[] sounds;

    public Sound[] getSounds() {
        return sounds;
    }

    public void setSounds(Sound[] value) {
        this.sounds = value;
    }

    public Game(int myId, Player[] players, int currentTick, Unit[] units, Loot[] loot, Projectile[] projectiles, Zone zone, Sound[] sounds) {
        this.myId = myId;
        this.players = players;
        this.currentTick = currentTick;
        this.units = units;
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
        Unit[] units;
        units = new Unit[StreamUtil.readInt(stream)];
        for (int unitsIndex = 0; unitsIndex < units.length; unitsIndex++) {
            Unit unitsElement;
            unitsElement = Unit.readFrom(stream);
            units[unitsIndex] = unitsElement;
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
        return new Game(myId, players, currentTick, units, loot, projectiles, zone, sounds);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, myId);
        StreamUtil.writeInt(stream, players.length);
        for (Player playersElement : players) {
            playersElement.writeTo(stream);
        }
        StreamUtil.writeInt(stream, currentTick);
        StreamUtil.writeInt(stream, units.length);
        for (Unit unitsElement : units) {
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
        stringBuilder.append("units: ");
        stringBuilder.append("[ ");
        for (int unitsIndex = 0; unitsIndex < units.length; unitsIndex++) {
            if (unitsIndex != 0) {
                stringBuilder.append(", ");
            }
            Unit unitsElement = units[unitsIndex];
            stringBuilder.append(String.valueOf(unitsElement));
        }
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
