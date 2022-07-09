package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Vec2 {

    private double x;

    public double getX() {
        return x;
    }

    public void setX(double value) {
        this.x = value;
    }

    private double y;

    public double getY() {
        return y;
    }

    public void setY(double value) {
        this.y = value;
    }

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vec2 readFrom(InputStream stream) throws IOException {
        double x;
        x = StreamUtil.readDouble(stream);
        double y;
        y = StreamUtil.readDouble(stream);
        return new Vec2(x, y);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeDouble(stream, x);
        StreamUtil.writeDouble(stream, y);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Vec2 { ");
        stringBuilder.append("x: ");
        stringBuilder.append(String.valueOf(x));
        stringBuilder.append(", ");
        stringBuilder.append("y: ");
        stringBuilder.append(String.valueOf(y));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
