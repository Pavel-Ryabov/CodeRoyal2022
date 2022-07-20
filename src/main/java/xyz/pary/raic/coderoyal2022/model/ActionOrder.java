package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public abstract class ActionOrder {

    private final ActionOrderType type;

    public ActionOrder(ActionOrderType type) {
        this.type = type;
    }

    public ActionOrderType getType() {
        return type;
    }

    public abstract void writeTo(OutputStream stream) throws IOException;

    public static ActionOrder readFrom(InputStream stream) throws IOException {
        switch (StreamUtil.readInt(stream)) {
            case Pickup.TAG:
                return Pickup.readFrom(stream);
            case UseShieldPotion.TAG:
                return UseShieldPotion.readFrom(stream);
            case DropShieldPotions.TAG:
                return DropShieldPotions.readFrom(stream);
            case DropWeapon.TAG:
                return DropWeapon.readFrom(stream);
            case DropAmmo.TAG:
                return DropAmmo.readFrom(stream);
            case Aim.TAG:
                return Aim.readFrom(stream);
            default:
                throw new IOException("Unexpected tag value");
        }
    }

    public static class Pickup extends ActionOrder {

        public static final int TAG = 0;

        private int loot;

        public Pickup(int loot) {
            super(ActionOrderType.PICKUP);
            this.loot = loot;
        }

        public int getLoot() {
            return loot;
        }

        public void setLoot(int value) {
            this.loot = value;
        }

        public static Pickup readFrom(InputStream stream) throws IOException {
            int loot;
            loot = StreamUtil.readInt(stream);
            return new Pickup(loot);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeInt(stream, loot);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Pickup { ");
            stringBuilder.append("loot: ");
            stringBuilder.append(String.valueOf(loot));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class UseShieldPotion extends ActionOrder {

        public static final int TAG = 1;

        public UseShieldPotion() {
            super(ActionOrderType.USE_SHIELD_POTION);
        }

        public static UseShieldPotion readFrom(InputStream stream) throws IOException {
            return new UseShieldPotion();
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("UseShieldPotion { ");
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class DropShieldPotions extends ActionOrder {

        public static final int TAG = 2;

        private int amount;

        public DropShieldPotions(int amount) {
            super(ActionOrderType.DROP_SHIELD_POTION);
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int value) {
            this.amount = value;
        }

        public static DropShieldPotions readFrom(InputStream stream) throws IOException {
            int amount;
            amount = StreamUtil.readInt(stream);
            return new DropShieldPotions(amount);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeInt(stream, amount);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("DropShieldPotions { ");
            stringBuilder.append("amount: ");
            stringBuilder.append(String.valueOf(amount));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class DropWeapon extends ActionOrder {

        public static final int TAG = 3;

        public DropWeapon() {
            super(ActionOrderType.DROP_WEAPON);
        }

        public static DropWeapon readFrom(InputStream stream) throws IOException {
            return new DropWeapon();
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("DropWeapon { ");
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class DropAmmo extends ActionOrder {

        public static final int TAG = 4;

        private int weaponTypeIndex;
        private int amount;

        public DropAmmo(int weaponTypeIndex, int amount) {
            super(ActionOrderType.DROP_AMMO);
            this.weaponTypeIndex = weaponTypeIndex;
            this.amount = amount;
        }

        public int getWeaponTypeIndex() {
            return weaponTypeIndex;
        }

        public void setWeaponTypeIndex(int value) {
            this.weaponTypeIndex = value;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int value) {
            this.amount = value;
        }

        public static DropAmmo readFrom(InputStream stream) throws IOException {
            int weaponTypeIndex;
            weaponTypeIndex = StreamUtil.readInt(stream);
            int amount;
            amount = StreamUtil.readInt(stream);
            return new DropAmmo(weaponTypeIndex, amount);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeInt(stream, weaponTypeIndex);
            StreamUtil.writeInt(stream, amount);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("DropAmmo { ");
            stringBuilder.append("weaponTypeIndex: ");
            stringBuilder.append(String.valueOf(weaponTypeIndex));
            stringBuilder.append(", ");
            stringBuilder.append("amount: ");
            stringBuilder.append(String.valueOf(amount));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Aim extends ActionOrder {

        public static final int TAG = 5;

        private boolean shoot;

        public Aim(boolean shoot) {
            super(ActionOrderType.AIM);
            this.shoot = shoot;
        }

        public boolean isShoot() {
            return shoot;
        }

        public void setShoot(boolean value) {
            this.shoot = value;
        }

        public static Aim readFrom(InputStream stream) throws IOException {
            boolean shoot;
            shoot = StreamUtil.readBoolean(stream);
            return new Aim(shoot);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeBoolean(stream, shoot);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Aim { ");
            stringBuilder.append("shoot: ");
            stringBuilder.append(String.valueOf(shoot));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }
}
