package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class SoundProperties {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    private double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double value) {
        this.distance = value;
    }

    private double offset;

    public double getOffset() {
        return offset;
    }

    public void setOffset(double value) {
        this.offset = value;
    }

    public SoundProperties(String name, double distance, double offset) {
        this.name = name;
        this.distance = distance;
        this.offset = offset;
    }

    public static SoundProperties readFrom(InputStream stream) throws IOException {
        String name;
        name = StreamUtil.readString(stream);
        double distance;
        distance = StreamUtil.readDouble(stream);
        double offset;
        offset = StreamUtil.readDouble(stream);
        return new SoundProperties(name, distance, offset);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeString(stream, name);
        StreamUtil.writeDouble(stream, distance);
        StreamUtil.writeDouble(stream, offset);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("SoundProperties { ");
        stringBuilder.append("name: ");
        stringBuilder.append('"' + name + '"');
        stringBuilder.append(", ");
        stringBuilder.append("distance: ");
        stringBuilder.append(String.valueOf(distance));
        stringBuilder.append(", ");
        stringBuilder.append("offset: ");
        stringBuilder.append(String.valueOf(offset));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
