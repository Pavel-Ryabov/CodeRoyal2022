package xyz.pary.raic.coderoyal2022;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.codegame.ClientMessage.DebugMessage;
import xyz.pary.raic.coderoyal2022.codegame.ClientMessage.RequestDebugState;
import xyz.pary.raic.coderoyal2022.debugging.Color;
import xyz.pary.raic.coderoyal2022.debugging.ColoredVertex;
import xyz.pary.raic.coderoyal2022.debugging.DebugCommand;
import xyz.pary.raic.coderoyal2022.debugging.DebugData;
import xyz.pary.raic.coderoyal2022.debugging.DebugState;
import xyz.pary.raic.coderoyal2022.model.Vec2;

public class DebugInterface {

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public DebugInterface(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void addPlacedText(Vec2 position, String text, Vec2 alignment, double size, Color color) {
        this.add(new DebugData.PlacedText(position, text, alignment, size, color));
    }

    public void addCircle(Vec2 position, double radius, Color color) {
        this.add(new DebugData.Circle(position, radius, color));
    }

    public void addGradientCircle(Vec2 position, double radius, Color innerColor, Color outerColor) {
        this.add(new DebugData.GradientCircle(position, radius, innerColor, outerColor));
    }

    public void addRing(Vec2 position, double radius, double width, Color color) {
        this.add(new DebugData.Ring(position, radius, width, color));
    }

    public void addPie(Vec2 position, double radius, double startAngle, double endAngle, Color color) {
        this.add(new DebugData.Pie(position, radius, startAngle, endAngle, color));
    }

    public void addArc(Vec2 position, double radius, double width, double startAngle, double endAngle, Color color) {
        this.add(new DebugData.Arc(position, radius, width, startAngle, endAngle, color));
    }

    public void addRect(Vec2 bottomLeft, Vec2 size, Color color) {
        this.add(new DebugData.Rect(bottomLeft, size, color));
    }

    public void addPolygon(Vec2[] vertices, Color color) {
        this.add(new DebugData.Polygon(vertices, color));
    }

    public void addGradientPolygon(ColoredVertex[] vertices) {
        this.add(new DebugData.GradientPolygon(vertices));
    }

    public void addSegment(Vec2 firstEnd, Vec2 secondEnd, double width, Color color) {
        this.add(new DebugData.Segment(firstEnd, secondEnd, width, color));
    }

    public void addGradientSegment(Vec2 firstEnd, Color firstColor, Vec2 secondEnd, Color secondColor, double width) {
        this.add(new DebugData.GradientSegment(firstEnd, firstColor, secondEnd, secondColor, width));
    }

    public void addPolyLine(Vec2[] vertices, double width, Color color) {
        this.add(new DebugData.PolyLine(vertices, width, color));
    }

    public void addGradientPolyLine(ColoredVertex[] vertices, double width) {
        this.add(new DebugData.GradientPolyLine(vertices, width));
    }

    public void add(DebugData debugData) {
        this.send(new DebugCommand.Add(debugData));
    }

    public void clear() {
        this.send(new DebugCommand.Clear());
    }

    public void setAutoFlush(boolean enable) {
        this.send(new DebugCommand.SetAutoFlush(enable));
    }

    public void flush() {
        this.send(new DebugCommand.Flush());
    }

    public void send(DebugCommand command) {
        try {
            new DebugMessage(command).writeTo(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DebugState getState() {
        try {
            new RequestDebugState().writeTo(outputStream);
            outputStream.flush();
            return DebugState.readFrom(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
