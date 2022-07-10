package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.StrictMath.sqrt;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;
import xyz.pary.raic.coderoyal2022.util.GeoUtil;

public class Vec2 implements Point {

    private double x;
    private double y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double value) {
        this.x = value;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double value) {
        this.y = value;
    }

    /**
     *
     * @return длина вектора
     */
    public double length() {
        return sqrt(this.squaredLength());
    }

    /**
     *
     * @return длина вектора в квадрате
     */
    public double squaredLength() {
        return this.x * this.x + this.y * this.y;
    }

    /**
     * Нормализация вектора (приведение длины к 1)
     *
     * @return нормализованный вектор
     */
    public Vec2 normalize() {
        double length = this.length();
        if (length != 0) {
            return new Vec2(this.x / length, this.y / length);
        }
        return new Vec2(0, 0);
    }

    /**
     * Сложение
     *
     * @param vector вектор который нужно прибавить
     * @return результирующий вектор
     */
    public Vec2 add(Vec2 vector) {
        return new Vec2(this.x + vector.x, this.y + vector.y);
    }

    /**
     * Вычитание
     *
     * @param vector вектор который нужно вычесть
     * @return результирующий вектор
     */
    public Vec2 sub(Vec2 vector) {
        return new Vec2(this.x - vector.x, this.y - vector.y);
    }

    /**
     * Умножение на число
     *
     * @param a число на которое нужно умножить
     * @return результирующий вектор
     */
    public Vec2 mul(double a) {
        return new Vec2(this.x * a, this.y * a);
    }

    /**
     * Скалярное произведение
     *
     * @param vector вектор на который нужно умножить
     * @return скалярное произведение
     */
    public double dotProduct(Vec2 vector) {
        return this.x * vector.x + this.y * vector.y;
    }

    public double squredDistanceTo(Vec2 target) {
        return GeoUtil.squaredDistance(this, target);
    }

    public double squredDistanceTo(double x, double y) {
        return GeoUtil.squaredDistance(this.x, this.y, x, y);
    }

    public double distanceTo(Vec2 target) {
        return GeoUtil.distance(this, target);
    }

    public double distanceTo(double x, double y) {
        return GeoUtil.distance(this.x, this.y, x, y);
    }

    public Vec2 getVelocity(Point target, double a) {
        Vec2 out = new Vec2(target.getX() - this.x, target.getY() - this.y);
        double length = out.length();
        if (length != 0) {
            out.x = out.x / length;
            out.y = out.y / length;
        } else {
            out.x = 0;
            out.y = 0;
        }
        out.x = out.x * a;
        out.y = out.y * a;
        return out;
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
