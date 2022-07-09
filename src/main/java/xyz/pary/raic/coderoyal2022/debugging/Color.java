package xyz.pary.raic.coderoyal2022.debugging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Color {

    private double r;

    public double getR() {
        return r;
    }

    public void setR(double value) {
        this.r = value;
    }

    private double g;

    public double getG() {
        return g;
    }

    public void setG(double value) {
        this.g = value;
    }

    private double b;

    public double getB() {
        return b;
    }

    public void setB(double value) {
        this.b = value;
    }

    private double a;

    public double getA() {
        return a;
    }

    public void setA(double value) {
        this.a = value;
    }

    public Color(double r, double g, double b, double a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static Color readFrom(InputStream stream) throws IOException {
        double r;
        r = StreamUtil.readDouble(stream);
        double g;
        g = StreamUtil.readDouble(stream);
        double b;
        b = StreamUtil.readDouble(stream);
        double a;
        a = StreamUtil.readDouble(stream);
        return new Color(r, g, b, a);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeDouble(stream, r);
        StreamUtil.writeDouble(stream, g);
        StreamUtil.writeDouble(stream, b);
        StreamUtil.writeDouble(stream, a);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Color { ");
        stringBuilder.append("r: ");
        stringBuilder.append(String.valueOf(r));
        stringBuilder.append(", ");
        stringBuilder.append("g: ");
        stringBuilder.append(String.valueOf(g));
        stringBuilder.append(", ");
        stringBuilder.append("b: ");
        stringBuilder.append(String.valueOf(b));
        stringBuilder.append(", ");
        stringBuilder.append("a: ");
        stringBuilder.append(String.valueOf(a));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
