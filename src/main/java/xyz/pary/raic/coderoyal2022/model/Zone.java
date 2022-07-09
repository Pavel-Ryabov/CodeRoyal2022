package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Zone {

    private Vec2 currentCenter;

    public Vec2 getCurrentCenter() {
        return currentCenter;
    }

    public void setCurrentCenter(Vec2 value) {
        this.currentCenter = value;
    }

    private double currentRadius;

    public double getCurrentRadius() {
        return currentRadius;
    }

    public void setCurrentRadius(double value) {
        this.currentRadius = value;
    }

    private Vec2 nextCenter;

    public Vec2 getNextCenter() {
        return nextCenter;
    }

    public void setNextCenter(Vec2 value) {
        this.nextCenter = value;
    }

    private double nextRadius;

    public double getNextRadius() {
        return nextRadius;
    }

    public void setNextRadius(double value) {
        this.nextRadius = value;
    }

    public Zone(Vec2 currentCenter, double currentRadius, Vec2 nextCenter, double nextRadius) {
        this.currentCenter = currentCenter;
        this.currentRadius = currentRadius;
        this.nextCenter = nextCenter;
        this.nextRadius = nextRadius;
    }

    public static Zone readFrom(InputStream stream) throws IOException {
        Vec2 currentCenter;
        currentCenter = Vec2.readFrom(stream);
        double currentRadius;
        currentRadius = StreamUtil.readDouble(stream);
        Vec2 nextCenter;
        nextCenter = Vec2.readFrom(stream);
        double nextRadius;
        nextRadius = StreamUtil.readDouble(stream);
        return new Zone(currentCenter, currentRadius, nextCenter, nextRadius);
    }

    public void writeTo(OutputStream stream) throws IOException {
        currentCenter.writeTo(stream);
        StreamUtil.writeDouble(stream, currentRadius);
        nextCenter.writeTo(stream);
        StreamUtil.writeDouble(stream, nextRadius);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Zone { ");
        stringBuilder.append("currentCenter: ");
        stringBuilder.append(String.valueOf(currentCenter));
        stringBuilder.append(", ");
        stringBuilder.append("currentRadius: ");
        stringBuilder.append(String.valueOf(currentRadius));
        stringBuilder.append(", ");
        stringBuilder.append("nextCenter: ");
        stringBuilder.append(String.valueOf(nextCenter));
        stringBuilder.append(", ");
        stringBuilder.append("nextRadius: ");
        stringBuilder.append(String.valueOf(nextRadius));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
