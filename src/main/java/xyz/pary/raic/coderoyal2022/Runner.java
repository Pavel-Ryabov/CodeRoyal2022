package xyz.pary.raic.coderoyal2022;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.io.InputStream;
import java.io.BufferedOutputStream;
import xyz.pary.raic.coderoyal2022.codegame.ClientMessage;
import xyz.pary.raic.coderoyal2022.codegame.ServerMessage;
import xyz.pary.raic.coderoyal2022.model.Game;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Runner {

    private final InputStream inputStream;
    private final OutputStream outputStream;

    Runner(String host, int port, String token) throws IOException {
        Socket socket = new Socket(host, port);
        socket.setTcpNoDelay(true);
        inputStream = new BufferedInputStream(socket.getInputStream());
        outputStream = new BufferedOutputStream(socket.getOutputStream());
        StreamUtil.writeString(outputStream, token);
        StreamUtil.writeInt(outputStream, 1);
        StreamUtil.writeInt(outputStream, 1);
        StreamUtil.writeInt(outputStream, 0);
        outputStream.flush();
    }

    void run() throws IOException {
        DebugInterface debugInterface = new DebugInterface(inputStream, outputStream);
        Strategy myStrategy = "test".equals(System.getenv("strategy")) ? new TestStrategy(debugInterface) : new MyStrategy(debugInterface);
        while (true) {
            ServerMessage message = ServerMessage.readFrom(inputStream);
            if (message instanceof ServerMessage.UpdateConstants) {
                ServerMessage.UpdateConstants updateConstantsMessage = (ServerMessage.UpdateConstants) message;
                Game.CONSTANTS = updateConstantsMessage.getConstants();
            } else if (message instanceof ServerMessage.GetOrder) {
                ServerMessage.GetOrder getOrderMessage = (ServerMessage.GetOrder) message;
                debugInterface.setAvailable(getOrderMessage.isDebugAvailable());
                new ClientMessage.OrderMessage(myStrategy.getOrder(getOrderMessage.getPlayerView())).writeTo(outputStream);
                outputStream.flush();
            } else if (message instanceof ServerMessage.Finish) {
                myStrategy.finish();
                break;
            } else if (message instanceof ServerMessage.DebugUpdate) {
                ServerMessage.DebugUpdate debugUpdateMessage = (ServerMessage.DebugUpdate) message;
                myStrategy.debugUpdate(debugUpdateMessage.getDisplayedTick(), debugInterface);
                new ClientMessage.DebugUpdateDone().writeTo(outputStream);
                outputStream.flush();
            } else {
                throw new IOException("Unexpected server message");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String host = args.length < 1 ? "127.0.0.1" : args[0];
        int port = args.length < 2 ? 31001 : Integer.parseInt(args[1]);
        String token = args.length < 3 ? "0000000000000000" : args[2];
        new Runner(host, port, token).run();
    }
}
