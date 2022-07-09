package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class UnitOrder {

    private Vec2 targetVelocity;

    public Vec2 getTargetVelocity() {
        return targetVelocity;
    }

    public void setTargetVelocity(Vec2 value) {
        this.targetVelocity = value;
    }

    private Vec2 targetDirection;

    public Vec2 getTargetDirection() {
        return targetDirection;
    }

    public void setTargetDirection(Vec2 value) {
        this.targetDirection = value;
    }

    private ActionOrder action;

    public ActionOrder getAction() {
        return action;
    }

    public void setAction(ActionOrder value) {
        this.action = value;
    }

    public UnitOrder(Vec2 targetVelocity, Vec2 targetDirection, ActionOrder action) {
        this.targetVelocity = targetVelocity;
        this.targetDirection = targetDirection;
        this.action = action;
    }

    public static UnitOrder readFrom(InputStream stream) throws IOException {
        Vec2 targetVelocity;
        targetVelocity = Vec2.readFrom(stream);
        Vec2 targetDirection;
        targetDirection = Vec2.readFrom(stream);
        ActionOrder action;
        if (StreamUtil.readBoolean(stream)) {
            action = ActionOrder.readFrom(stream);
        } else {
            action = null;
        }
        return new UnitOrder(targetVelocity, targetDirection, action);
    }

    public void writeTo(OutputStream stream) throws IOException {
        targetVelocity.writeTo(stream);
        targetDirection.writeTo(stream);
        if (action == null) {
            StreamUtil.writeBoolean(stream, false);
        } else {
            StreamUtil.writeBoolean(stream, true);
            action.writeTo(stream);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("UnitOrder { ");
        stringBuilder.append("targetVelocity: ");
        stringBuilder.append(String.valueOf(targetVelocity));
        stringBuilder.append(", ");
        stringBuilder.append("targetDirection: ");
        stringBuilder.append(String.valueOf(targetDirection));
        stringBuilder.append(", ");
        stringBuilder.append("action: ");
        stringBuilder.append(String.valueOf(action));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
