package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Player {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    private int kills;

    public int getKills() {
        return kills;
    }

    public void setKills(int value) {
        this.kills = value;
    }

    private double damage;

    public double getDamage() {
        return damage;
    }

    public void setDamage(double value) {
        this.damage = value;
    }

    private int place;

    public int getPlace() {
        return place;
    }

    public void setPlace(int value) {
        this.place = value;
    }

    private double score;

    public double getScore() {
        return score;
    }

    public void setScore(double value) {
        this.score = value;
    }

    public Player(int id, int kills, double damage, int place, double score) {
        this.id = id;
        this.kills = kills;
        this.damage = damage;
        this.place = place;
        this.score = score;
    }

    public static Player readFrom(InputStream stream) throws IOException {
        int id;
        id = StreamUtil.readInt(stream);
        int kills;
        kills = StreamUtil.readInt(stream);
        double damage;
        damage = StreamUtil.readDouble(stream);
        int place;
        place = StreamUtil.readInt(stream);
        double score;
        score = StreamUtil.readDouble(stream);
        return new Player(id, kills, damage, place, score);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, id);
        StreamUtil.writeInt(stream, kills);
        StreamUtil.writeDouble(stream, damage);
        StreamUtil.writeInt(stream, place);
        StreamUtil.writeDouble(stream, score);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Player { ");
        stringBuilder.append("id: ");
        stringBuilder.append(String.valueOf(id));
        stringBuilder.append(", ");
        stringBuilder.append("kills: ");
        stringBuilder.append(String.valueOf(kills));
        stringBuilder.append(", ");
        stringBuilder.append("damage: ");
        stringBuilder.append(String.valueOf(damage));
        stringBuilder.append(", ");
        stringBuilder.append("place: ");
        stringBuilder.append(String.valueOf(place));
        stringBuilder.append(", ");
        stringBuilder.append("score: ");
        stringBuilder.append(String.valueOf(score));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
