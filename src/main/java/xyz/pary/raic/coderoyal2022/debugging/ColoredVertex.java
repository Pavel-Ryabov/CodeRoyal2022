package xyz.pary.raic.coderoyal2022.debugging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.model.Vec2;

public class ColoredVertex {

    private Vec2 position;

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 value) {
        this.position = value;
    }

    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color value) {
        this.color = value;
    }

    public ColoredVertex(Vec2 position, Color color) {
        this.position = position;
        this.color = color;
    }

    public static ColoredVertex readFrom(InputStream stream) throws IOException {
        Vec2 position;
        position = Vec2.readFrom(stream);
        Color color;
        color = Color.readFrom(stream);
        return new ColoredVertex(position, color);
    }

    public void writeTo(OutputStream stream) throws IOException {
        position.writeTo(stream);
        color.writeTo(stream);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ColoredVertex { ");
        stringBuilder.append("position: ");
        stringBuilder.append(String.valueOf(position));
        stringBuilder.append(", ");
        stringBuilder.append("color: ");
        stringBuilder.append(String.valueOf(color));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
