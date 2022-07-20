package xyz.pary.raic.coderoyal2022.util;

import java.util.HashMap;
import java.util.Map;

public class TrigonometryUtil {

    private static final Map<Double, Double> SINUSES = new HashMap<>();
    private static final Map<Double, Double> COSINES = new HashMap<>();
    private static final Map<Double, Double> ARC_TANGENTS = new HashMap<>();

    public static double sin(double radians) {
        Double sin = SINUSES.get(radians);
        if (sin != null) {
            return sin;
        }
        sin = Math.sin(radians);
        SINUSES.put(radians, sin);
        return sin;
    }

    public static double cos(double radians) {
        Double cos = COSINES.get(radians);
        if (cos != null) {
            return cos;
        }
        cos = Math.cos(radians);
        COSINES.put(radians, cos);
        return cos;
    }

    public static double atan(double val) {
        Double atan = ARC_TANGENTS.get(val);
        if (atan != null) {
            return atan;
        }
        atan = Math.toDegrees(Math.atan(val));
        ARC_TANGENTS.put(val, atan);
        return atan;
    }
}
