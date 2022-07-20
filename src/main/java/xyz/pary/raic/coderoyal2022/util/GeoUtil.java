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

    public static double crossProduct(double x1, double y1, double x2, double y2) {
        return x1 * y2 - y1 * x2;
    }

    public static double crossProduct(Point p1, Point p2) {
        return crossProduct(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public static double dotProduct(double x1, double y1, double x2, double y2) {
        return x1 * x2 + y1 * y2;
    }

    public static double dotProduct(Point p1, Point p2) {
        return dotProduct(p1.getX(), p1.getY(), p2.getX(), p2.getY());
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

    public static double getAngle(Vec2 v1, Vec2 v2) {
        return Math.toDegrees(Math.acos(v1.dotProduct(v2) / (v1.length() * v2.length())));
    }

    public static double triangleArea(double x1, double y1, double x2, double y2, double x3, double y3) {
        double abx = x2 - x1;
        double aby = y2 - y1;
        double acx = x3 - x1;
        double acy = y3 - y1;
        return Math.abs(crossProduct(abx, aby, acx, acy)) / 2;
    }

    public static double triangleArea(Vec2 a, Vec2 b, Vec2 c) {
        return triangleArea(a.getX(), a.getY(), b.getX(), b.getY(), c.getX(), c.getY());
    }

    public static boolean isLineIntersectCircle(double x1, double y1, double x2, double y2, double xc, double yc, double r) {
        double minDist = 2 * triangleArea(x1, y1, x2, y2, xc, yc) / distance(x1, y1, x2, y2);
        return minDist <= r;
    }

    public static boolean isLineIntersectCircle(Point p1, Point p2, Point c, double r) {
        return isLineIntersectCircle(p1.getX(), p1.getY(), p2.getX(), p2.getY(), c.getX(), c.getY(), r);
    }

    public static boolean isIntersect(Vec2 point1, Vec2 point2, Vec2 circle, double r) {
        Vec2 d = point2.sub(point1);
        Vec2 f = point1.sub(circle);
        double a = dotProduct(d, d);
        double b = 2 * dotProduct(f, d);
        double c = dotProduct(f, f) - r * r;
        double discr = b * b - 4 * a * c;
        if (discr < 0) {
            return false;
        }
        discr = Math.sqrt(discr);
        double t1 = (-b - discr) / (2 * a);
        double t2 = (-b + discr) / (2 * a);
        return (t1 >= 0 && t1 <= 1) || (t2 >= 0 && t2 <= 1);
    }
}
