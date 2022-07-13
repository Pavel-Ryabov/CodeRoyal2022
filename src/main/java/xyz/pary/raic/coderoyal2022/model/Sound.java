package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Sound implements Point {

    private final int tick;

    private SoundType type;
    private int unitId;
    private Vec2 position;

    public Sound(int tick, int typeIndex, int unitId, Vec2 position) {
        this.tick = tick;
        this.type = SoundType.getByIndex(typeIndex);
        this.unitId = unitId;
        this.position = position;
    }

    public int getTick() {
        return tick;
    }

    public SoundType getType() {
        return type;
    }

    public void setType(SoundType type) {
        this.type = type;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 position) {
        this.position = position;
    }

    @Override
    public double getX() {
        return position.getX();
    }

    @Override
    public void setX(double value) {
        position.setX(value);
    }

    @Override
    public double getY() {
        return position.getY();
    }

    @Override
    public void setY(double value) {
        position.setY(value);
    }

    public static Sound readFrom(InputStream stream, int tick) throws IOException {
        int typeIndex;
        typeIndex = StreamUtil.readInt(stream);
        int unitId;
        unitId = StreamUtil.readInt(stream);
        Vec2 position;
        position = Vec2.readFrom(stream);
        return new Sound(tick, typeIndex, unitId, position);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, type.getIndex());
        StreamUtil.writeInt(stream, unitId);
        position.writeTo(stream);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Sound { ");
        stringBuilder.append("tick: ");
        stringBuilder.append(String.valueOf(tick));
        stringBuilder.append(", ");
        stringBuilder.append("typeIndex: ");
        stringBuilder.append(String.valueOf(type));
        stringBuilder.append(", ");
        stringBuilder.append("unitId: ");
        stringBuilder.append(String.valueOf(unitId));
        stringBuilder.append(", ");
        stringBuilder.append("position: ");
        stringBuilder.append(String.valueOf(position));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
