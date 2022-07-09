package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Obstacle {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    private Vec2 position;

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 value) {
        this.position = value;
    }

    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double value) {
        this.radius = value;
    }

    private boolean canSeeThrough;

    public boolean isCanSeeThrough() {
        return canSeeThrough;
    }

    public void setCanSeeThrough(boolean value) {
        this.canSeeThrough = value;
    }

    private boolean canShootThrough;

    public boolean isCanShootThrough() {
        return canShootThrough;
    }

    public void setCanShootThrough(boolean value) {
        this.canShootThrough = value;
    }

    public Obstacle(int id, Vec2 position, double radius, boolean canSeeThrough, boolean canShootThrough) {
        this.id = id;
        this.position = position;
        this.radius = radius;
        this.canSeeThrough = canSeeThrough;
        this.canShootThrough = canShootThrough;
    }

    public static Obstacle readFrom(InputStream stream) throws IOException {
        int id;
        id = StreamUtil.readInt(stream);
        Vec2 position;
        position = Vec2.readFrom(stream);
        double radius;
        radius = StreamUtil.readDouble(stream);
        boolean canSeeThrough;
        canSeeThrough = StreamUtil.readBoolean(stream);
        boolean canShootThrough;
        canShootThrough = StreamUtil.readBoolean(stream);
        return new Obstacle(id, position, radius, canSeeThrough, canShootThrough);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, id);
        position.writeTo(stream);
        StreamUtil.writeDouble(stream, radius);
        StreamUtil.writeBoolean(stream, canSeeThrough);
        StreamUtil.writeBoolean(stream, canShootThrough);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Obstacle { ");
        stringBuilder.append("id: ");
        stringBuilder.append(String.valueOf(id));
        stringBuilder.append(", ");
        stringBuilder.append("position: ");
        stringBuilder.append(String.valueOf(position));
        stringBuilder.append(", ");
        stringBuilder.append("radius: ");
        stringBuilder.append(String.valueOf(radius));
        stringBuilder.append(", ");
        stringBuilder.append("canSeeThrough: ");
        stringBuilder.append(String.valueOf(canSeeThrough));
        stringBuilder.append(", ");
        stringBuilder.append("canShootThrough: ");
        stringBuilder.append(String.valueOf(canShootThrough));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
