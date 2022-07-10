package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public abstract class Item {

    private final ItemType itemType;

    public Item(ItemType itemType) {
        this.itemType = itemType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public abstract void writeTo(OutputStream stream) throws IOException;

    public static Item readFrom(InputStream stream) throws IOException {
        switch (StreamUtil.readInt(stream)) {
            case Weapon.TAG:
                return Weapon.readFrom(stream);
            case ShieldPotions.TAG:
                return ShieldPotions.readFrom(stream);
            case Ammo.TAG:
                return Ammo.readFrom(stream);
            default:
                throw new IOException("Unexpected tag value");
        }
    }

    public static class Weapon extends Item {

        public static final int TAG = 0;

        private final WeaponType type;

        public Weapon(int typeIndex) {
            super(ItemType.WEAPON);
            this.type = WeaponType.getByIndex(typeIndex);
        }

        public WeaponType getType() {
            return type;
        }

        public static Weapon readFrom(InputStream stream) throws IOException {
            int typeIndex;
            typeIndex = StreamUtil.readInt(stream);
            return new Weapon(typeIndex);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeInt(stream, type.getIndex());
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Weapon { ");
            stringBuilder.append("typeIndex: ");
            stringBuilder.append(String.valueOf(type));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class ShieldPotions extends Item {

        public static final int TAG = 1;

        private int amount;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int value) {
            this.amount = value;
        }

        public ShieldPotions(int amount) {
            super(ItemType.SHIELD_POTION);
            this.amount = amount;
        }

        public static ShieldPotions readFrom(InputStream stream) throws IOException {
            int amount;
            amount = StreamUtil.readInt(stream);
            return new ShieldPotions(amount);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeInt(stream, amount);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("ShieldPotions { ");
            stringBuilder.append("amount: ");
            stringBuilder.append(String.valueOf(amount));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Ammo extends Item {

        public static final int TAG = 2;

        private final WeaponType weaponType;
        private final int amount;

        public Ammo(int weaponTypeIndex, int amount) {
            super(ItemType.AMMO);
            this.weaponType = WeaponType.getByIndex(weaponTypeIndex);
            this.amount = amount;
        }

        public WeaponType getWeaponType() {
            return weaponType;
        }

        public int getAmount() {
            return amount;
        }

        public static Ammo readFrom(InputStream stream) throws IOException {
            int weaponTypeIndex;
            weaponTypeIndex = StreamUtil.readInt(stream);
            int amount;
            amount = StreamUtil.readInt(stream);
            return new Ammo(weaponTypeIndex, amount);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeInt(stream, weaponType.getIndex());
            StreamUtil.writeInt(stream, amount);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Ammo { ");
            stringBuilder.append("weaponTypeIndex: ");
            stringBuilder.append(String.valueOf(weaponType));
            stringBuilder.append(", ");
            stringBuilder.append("amount: ");
            stringBuilder.append(String.valueOf(amount));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }
}
