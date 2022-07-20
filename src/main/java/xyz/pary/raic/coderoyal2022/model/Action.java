package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Action {

    private int finishTick;
    private ActionType actionType;

    public Action(int finishTick, ActionType actionType) {
        this.finishTick = finishTick;
        this.actionType = actionType;
    }

    public Action(Action a) {
        this.finishTick = a.finishTick;
        this.actionType = a.actionType;
    }

    public int getFinishTick() {
        return finishTick;
    }

    public void setFinishTick(int value) {
        this.finishTick = value;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType value) {
        this.actionType = value;
    }

    public static Action readFrom(InputStream stream) throws IOException {
        int finishTick;
        finishTick = StreamUtil.readInt(stream);
        ActionType actionType;
        actionType = ActionType.readFrom(stream);
        return new Action(finishTick, actionType);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, finishTick);
        StreamUtil.writeInt(stream, actionType.tag);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Action { ");
        stringBuilder.append("finishTick: ");
        stringBuilder.append(String.valueOf(finishTick));
        stringBuilder.append(", ");
        stringBuilder.append("actionType: ");
        stringBuilder.append(String.valueOf(actionType));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
