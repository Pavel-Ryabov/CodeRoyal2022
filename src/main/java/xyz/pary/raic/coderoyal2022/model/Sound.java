package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Sound {

    private int typeIndex;

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int value) {
        this.typeIndex = value;
    }

    private int unitId;

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int value) {
        this.unitId = value;
    }

    private Vec2 position;

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 value) {
        this.position = value;
    }

    public Sound(int typeIndex, int unitId, Vec2 position) {
        this.typeIndex = typeIndex;
        this.unitId = unitId;
        this.position = position;
    }

    public static Sound readFrom(InputStream stream) throws IOException {
        int typeIndex;
        typeIndex = StreamUtil.readInt(stream);
        int unitId;
        unitId = StreamUtil.readInt(stream);
        Vec2 position;
        position = Vec2.readFrom(stream);
        return new Sound(typeIndex, unitId, position);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, typeIndex);
        StreamUtil.writeInt(stream, unitId);
        position.writeTo(stream);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Sound { ");
        stringBuilder.append("typeIndex: ");
        stringBuilder.append(String.valueOf(typeIndex));
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
