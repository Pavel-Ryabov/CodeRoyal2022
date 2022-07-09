package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public enum ActionType {

    LOOTING(0),
    USE_SHIELD_POTION(1);

    public int tag;

    ActionType(int tag) {
        this.tag = tag;
    }

    public static ActionType readFrom(InputStream stream) throws IOException {
        switch (StreamUtil.readInt(stream)) {
            case 0:
                return LOOTING;
            case 1:
                return USE_SHIELD_POTION;
            default:
                throw new IOException("Unexpected tag value");
        }
    }
}
