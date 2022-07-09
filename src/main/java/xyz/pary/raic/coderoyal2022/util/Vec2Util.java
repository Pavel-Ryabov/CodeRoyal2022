package xyz.pary.raic.coderoyal2022.util;

import xyz.pary.raic.coderoyal2022.model.Point;

public class Vec2Util {

    public static double squaredDistance(double x1, double y1, double x2, double y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public static double squaredDistance(Point a, Point b) {
        return squaredDistance(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return (int) Math.sqrt(squaredDistance(x1, y1, x2, y2));
    }

    public static double distance(Point a, Point b) {
        return distance(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public static <T extends Point> T getNearestTarget(Point v, Iterable<T> targets) {
        return getNearestTarget(v.getX(), v.getY(), targets);
    }

    public static <T extends Point> T getNearestTarget(double x, double y, Iterable<T> targets) {
        double minDist = Double.MAX_VALUE;
        T target = null;
        for (T t : targets) {
            double dist = squaredDistance(x, y, t.getX(), t.getY());
            if (dist < minDist) {
                minDist = dist;
                target = t;
            }
        }
        return target;
    }
}
