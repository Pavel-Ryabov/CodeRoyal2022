package xyz.pary.raic.coderoyal2022.codegame;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.model.Constants;
import xyz.pary.raic.coderoyal2022.model.Game;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public abstract class ServerMessage {

    public abstract void writeTo(OutputStream stream) throws IOException;

    public static ServerMessage readFrom(InputStream stream) throws IOException {
        switch (StreamUtil.readInt(stream)) {
            case UpdateConstants.TAG:
                return UpdateConstants.readFrom(stream);
            case GetOrder.TAG:
                return GetOrder.readFrom(stream);
            case Finish.TAG:
                return Finish.readFrom(stream);
            case DebugUpdate.TAG:
                return DebugUpdate.readFrom(stream);
            default:
                throw new IOException("Unexpected tag value");
        }
    }

    public static class UpdateConstants extends ServerMessage {

        public static final int TAG = 0;

        private Constants constants;

        public Constants getConstants() {
            return constants;
        }

        public void setConstants(Constants value) {
            this.constants = value;
        }

        public UpdateConstants(Constants constants) {
            this.constants = constants;
        }

        public static UpdateConstants readFrom(InputStream stream) throws IOException {
            Constants constants;
            constants = Constants.readFrom(stream);
            return new UpdateConstants(constants);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            constants.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("UpdateConstants { ");
            stringBuilder.append("constants: ");
            stringBuilder.append(String.valueOf(constants));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class GetOrder extends ServerMessage {

        public static final int TAG = 1;

        private Game playerView;

        public Game getPlayerView() {
            return playerView;
        }

        public void setPlayerView(Game value) {
            this.playerView = value;
        }

        private boolean debugAvailable;

        public boolean isDebugAvailable() {
            return debugAvailable;
        }

        public void setDebugAvailable(boolean value) {
            this.debugAvailable = value;
        }

        public GetOrder(Game playerView, boolean debugAvailable) {
            this.playerView = playerView;
            this.debugAvailable = debugAvailable;
        }

        public static GetOrder readFrom(InputStream stream) throws IOException {
            Game playerView;
            playerView = Game.readFrom(stream);
            boolean debugAvailable;
            debugAvailable = StreamUtil.readBoolean(stream);
            return new GetOrder(playerView, debugAvailable);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            playerView.writeTo(stream);
            StreamUtil.writeBoolean(stream, debugAvailable);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("GetOrder { ");
            stringBuilder.append("playerView: ");
            stringBuilder.append(String.valueOf(playerView));
            stringBuilder.append(", ");
            stringBuilder.append("debugAvailable: ");
            stringBuilder.append(String.valueOf(debugAvailable));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Finish extends ServerMessage {

        public static final int TAG = 2;

        public Finish() {
        }

        public static Finish readFrom(InputStream stream) throws IOException {
            return new Finish();
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Finish { ");
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class DebugUpdate extends ServerMessage {

        public static final int TAG = 3;

        public DebugUpdate() {
        }

        public static DebugUpdate readFrom(InputStream stream) throws IOException {
            return new DebugUpdate();
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("DebugUpdate { ");
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }
}
