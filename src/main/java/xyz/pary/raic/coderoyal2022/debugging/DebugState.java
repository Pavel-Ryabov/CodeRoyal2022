package xyz.pary.raic.coderoyal2022.debugging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class DebugState {

    private String[] pressedKeys;

    public String[] getPressedKeys() {
        return pressedKeys;
    }

    public void setPressedKeys(String[] value) {
        this.pressedKeys = value;
    }

    private Vec2 cursorWorldPosition;

    public Vec2 getCursorWorldPosition() {
        return cursorWorldPosition;
    }

    public void setCursorWorldPosition(Vec2 value) {
        this.cursorWorldPosition = value;
    }

    private Integer lockedUnit;

    public Integer getLockedUnit() {
        return lockedUnit;
    }

    public void setLockedUnit(Integer value) {
        this.lockedUnit = value;
    }

    private Camera camera;

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera value) {
        this.camera = value;
    }

    public DebugState(String[] pressedKeys, Vec2 cursorWorldPosition, Integer lockedUnit, Camera camera) {
        this.pressedKeys = pressedKeys;
        this.cursorWorldPosition = cursorWorldPosition;
        this.lockedUnit = lockedUnit;
        this.camera = camera;
    }

    public static DebugState readFrom(InputStream stream) throws IOException {
        String[] pressedKeys;
        pressedKeys = new String[StreamUtil.readInt(stream)];
        for (int pressedKeysIndex = 0; pressedKeysIndex < pressedKeys.length; pressedKeysIndex++) {
            String pressedKeysElement;
            pressedKeysElement = StreamUtil.readString(stream);
            pressedKeys[pressedKeysIndex] = pressedKeysElement;
        }
        Vec2 cursorWorldPosition;
        cursorWorldPosition = Vec2.readFrom(stream);
        Integer lockedUnit;
        if (StreamUtil.readBoolean(stream)) {
            lockedUnit = StreamUtil.readInt(stream);
        } else {
            lockedUnit = null;
        }
        Camera camera;
        camera = Camera.readFrom(stream);
        return new DebugState(pressedKeys, cursorWorldPosition, lockedUnit, camera);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, pressedKeys.length);
        for (String pressedKeysElement : pressedKeys) {
            StreamUtil.writeString(stream, pressedKeysElement);
        }
        cursorWorldPosition.writeTo(stream);
        if (lockedUnit == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            StreamUtil.writeInt(stream, lockedUnit);
        }
        camera.writeTo(stream);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("DebugState { ");
        stringBuilder.append("pressedKeys: ");
        stringBuilder.append("[ ");
        for (int pressedKeysIndex = 0; pressedKeysIndex < pressedKeys.length; pressedKeysIndex++) {
            if (pressedKeysIndex != 0) {
                stringBuilder.append(", ");
            }
            String pressedKeysElement = pressedKeys[pressedKeysIndex];
            stringBuilder.append('"' + pressedKeysElement + '"');
        }
        stringBuilder.append(" ]");
        stringBuilder.append(", ");
        stringBuilder.append("cursorWorldPosition: ");
        stringBuilder.append(String.valueOf(cursorWorldPosition));
        stringBuilder.append(", ");
        stringBuilder.append("lockedUnit: ");
        stringBuilder.append(String.valueOf(lockedUnit));
        stringBuilder.append(", ");
        stringBuilder.append("camera: ");
        stringBuilder.append(String.valueOf(camera));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
