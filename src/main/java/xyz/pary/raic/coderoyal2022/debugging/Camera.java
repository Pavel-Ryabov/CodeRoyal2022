package xyz.pary.raic.coderoyal2022.debugging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Camera {

    private Vec2 center;

    public Vec2 getCenter() {
        return center;
    }

    public void setCenter(Vec2 value) {
        this.center = value;
    }

    private double rotation;

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double value) {
        this.rotation = value;
    }

    private double attack;

    public double getAttack() {
        return attack;
    }

    public void setAttack(double value) {
        this.attack = value;
    }

    private double fov;

    public double getFov() {
        return fov;
    }

    public void setFov(double value) {
        this.fov = value;
    }

    public Camera(Vec2 center, double rotation, double attack, double fov) {
        this.center = center;
        this.rotation = rotation;
        this.attack = attack;
        this.fov = fov;
    }

    public static Camera readFrom(InputStream stream) throws IOException {
        Vec2 center;
        center = Vec2.readFrom(stream);
        double rotation;
        rotation = StreamUtil.readDouble(stream);
        double attack;
        attack = StreamUtil.readDouble(stream);
        double fov;
        fov = StreamUtil.readDouble(stream);
        return new Camera(center, rotation, attack, fov);
    }

    public void writeTo(OutputStream stream) throws IOException {
        center.writeTo(stream);
        StreamUtil.writeDouble(stream, rotation);
        StreamUtil.writeDouble(stream, attack);
        StreamUtil.writeDouble(stream, fov);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Camera { ");
        stringBuilder.append("center: ");
        stringBuilder.append(String.valueOf(center));
        stringBuilder.append(", ");
        stringBuilder.append("rotation: ");
        stringBuilder.append(String.valueOf(rotation));
        stringBuilder.append(", ");
        stringBuilder.append("attack: ");
        stringBuilder.append(String.valueOf(attack));
        stringBuilder.append(", ");
        stringBuilder.append("fov: ");
        stringBuilder.append(String.valueOf(fov));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
