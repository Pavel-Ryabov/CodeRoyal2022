package xyz.pary.raic.coderoyal2022.util;

import xyz.pary.raic.coderoyal2022.model.Point;
import xyz.pary.raic.coderoyal2022.model.Vec2;

public class GeoUtil {

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

    public static boolean isInsideCircle(double x, double y, double xc, double yc, double r) {
        return (x - xc) * (x - xc) + (y - yc) * (y - yc) <= r * r;
    }

    public static boolean isInsideCircle(Point p, Point c, double r) {
        return isInsideCircle(p.getX(), p.getY(), c.getX(), c.getY(), r);
    }

    public static boolean isInsideCircle(double xs, double ys, double rs, double xb, double yb, double rb) {
        return (xb - xs) * (xb - xs) + (yb - ys) * (yb - ys) + rs * rs <= rb * rb;
    }

    public static boolean isInsideCircle(Point sc, double rs, Point bc, double rb) {
        return isInsideCircle(sc.getX(), sc.getY(), rs, bc.getX(), bc.getY(), rb);
    }

    public static Vec2 getIntersect(Vec2 circle, double radius, Vec2 outerPoint) {
        return circle.add(circle.getVelocity(outerPoint, radius));
    }
}
