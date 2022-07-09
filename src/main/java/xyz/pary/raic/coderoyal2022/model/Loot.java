package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Loot implements Point {

    private int id;
    private Vec2 position;
    private Item item;

    public Loot(int id, Vec2 position, Item item) {
        this.id = id;
        this.position = position;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setPosition(Vec2 value) {
        this.position = value;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item value) {
        this.item = value;
    }

    @Override
    public double getX() {
        return position.getX();
    }

    @Override
    public void setX(double value) {
        position.setX(value);
    }

    @Override
    public double getY() {
        return position.getY();
    }

    @Override
    public void setY(double value) {
        position.setY(value);
    }

    public static Loot readFrom(InputStream stream) throws IOException {
        int id;
        id = StreamUtil.readInt(stream);
        Vec2 position;
        position = Vec2.readFrom(stream);
        Item item;
        item = Item.readFrom(stream);
        return new Loot(id, position, item);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, id);
        position.writeTo(stream);
        item.writeTo(stream);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Loot { ");
        stringBuilder.append("id: ");
        stringBuilder.append(String.valueOf(id));
        stringBuilder.append(", ");
        stringBuilder.append("position: ");
        stringBuilder.append(String.valueOf(position));
        stringBuilder.append(", ");
        stringBuilder.append("item: ");
        stringBuilder.append(String.valueOf(item));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
