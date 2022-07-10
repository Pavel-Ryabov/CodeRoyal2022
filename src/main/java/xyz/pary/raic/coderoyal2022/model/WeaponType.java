package xyz.pary.raic.coderoyal2022.model;

public enum WeaponType {
    MAGIC_WAND(0), STAFF(1), BOW(2);
    private final int index;

    private WeaponType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static WeaponType getByIndex(int index) {
        switch (index) {
            case 0:
                return MAGIC_WAND;
            case 1:
                return STAFF;
            case 2:
                return BOW;
            default:
                throw new RuntimeException("Unknown WeaponType index: " + index);
        }
    }
}
