package xyz.pary.raic.coderoyal2022.debugging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public abstract class DebugCommand {

    public abstract void writeTo(OutputStream stream) throws IOException;

    public static DebugCommand readFrom(InputStream stream) throws IOException {
        switch (StreamUtil.readInt(stream)) {
            case Add.TAG:
                return Add.readFrom(stream);
            case Clear.TAG:
                return Clear.readFrom(stream);
            case SetAutoFlush.TAG:
                return SetAutoFlush.readFrom(stream);
            case Flush.TAG:
                return Flush.readFrom(stream);
            default:
                throw new IOException("Unexpected tag value");
        }
    }

    public static class Add extends DebugCommand {

        public static final int TAG = 0;

        private DebugData debugData;

        public DebugData getDebugData() {
            return debugData;
        }

        public void setDebugData(DebugData value) {
            this.debugData = value;
        }

        public Add(DebugData debugData) {
            this.debugData = debugData;
        }

        public static Add readFrom(InputStream stream) throws IOException {
            DebugData debugData;
            debugData = DebugData.readFrom(stream);
            return new Add(debugData);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            debugData.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Add { ");
            stringBuilder.append("debugData: ");
            stringBuilder.append(String.valueOf(debugData));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Clear extends DebugCommand {

        public static final int TAG = 1;

        public Clear() {
        }

        public static Clear readFrom(InputStream stream) throws IOException {
            return new Clear();
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Clear { ");
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class SetAutoFlush extends DebugCommand {

        public static final int TAG = 2;

        private boolean enable;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean value) {
            this.enable = value;
        }

        public SetAutoFlush(boolean enable) {
            this.enable = enable;
        }

        public static SetAutoFlush readFrom(InputStream stream) throws IOException {
            boolean enable;
            enable = StreamUtil.readBoolean(stream);
            return new SetAutoFlush(enable);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeBoolean(stream, enable);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("SetAutoFlush { ");
            stringBuilder.append("enable: ");
            stringBuilder.append(String.valueOf(enable));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Flush extends DebugCommand {

        public static final int TAG = 3;

        public Flush() {
        }

        public static Flush readFrom(InputStream stream) throws IOException {
            return new Flush();
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Flush { ");
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }
}
