package xyz.pary.raic.coderoyal2022.codegame;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.debugging.DebugCommand;
import xyz.pary.raic.coderoyal2022.model.Order;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public abstract class ClientMessage {

    public abstract void writeTo(OutputStream stream) throws IOException;

    public static ClientMessage readFrom(InputStream stream) throws IOException {
        switch (StreamUtil.readInt(stream)) {
            case DebugMessage.TAG:
                return DebugMessage.readFrom(stream);
            case OrderMessage.TAG:
                return OrderMessage.readFrom(stream);
            case DebugUpdateDone.TAG:
                return DebugUpdateDone.readFrom(stream);
            case RequestDebugState.TAG:
                return RequestDebugState.readFrom(stream);
            default:
                throw new IOException("Unexpected tag value");
        }
    }

    public static class DebugMessage extends ClientMessage {

        public static final int TAG = 0;

        private DebugCommand command;

        public DebugCommand getCommand() {
            return command;
        }

        public void setCommand(DebugCommand value) {
            this.command = value;
        }

        public DebugMessage(DebugCommand command) {
            this.command = command;
        }

        public static DebugMessage readFrom(InputStream stream) throws IOException {
            DebugCommand command;
            command = DebugCommand.readFrom(stream);
            return new DebugMessage(command);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            command.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("DebugMessage { ");
            stringBuilder.append("command: ");
            stringBuilder.append(String.valueOf(command));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class OrderMessage extends ClientMessage {

        public static final int TAG = 1;

        private Order order;

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order value) {
            this.order = value;
        }

        public OrderMessage(Order order) {
            this.order = order;
        }

        public static OrderMessage readFrom(InputStream stream) throws IOException {
            Order order;
            order = Order.readFrom(stream);
            return new OrderMessage(order);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            order.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("OrderMessage { ");
            stringBuilder.append("order: ");
            stringBuilder.append(String.valueOf(order));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class DebugUpdateDone extends ClientMessage {

        public static final int TAG = 2;

        public DebugUpdateDone() {
        }

        public static DebugUpdateDone readFrom(InputStream stream) throws IOException {
            return new DebugUpdateDone();
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("DebugUpdateDone { ");
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class RequestDebugState extends ClientMessage {

        public static final int TAG = 3;

        public RequestDebugState() {
        }

        public static RequestDebugState readFrom(InputStream stream) throws IOException {
            return new RequestDebugState();
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("RequestDebugState { ");
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }
}
