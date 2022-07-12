package xyz.pary.raic.coderoyal2022.model;

public enum SoundType {
    STEPS(0),
    SHOT_MAGIC_WAND(1), SHOT_STAFF(2), SHOT_BOW(3),
    HIT_MAGIC_WAND(4), HIT_STAFF(5), HIT_BOW(6);

    private final int index;

    private SoundType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static SoundType getByIndex(int index) {
        switch (index) {
            case 0:
                return STEPS;
            case 1:
                return SHOT_MAGIC_WAND;
            case 2:
                return SHOT_STAFF;
            case 3:
                return SHOT_BOW;
            case 4:
                return HIT_MAGIC_WAND;
            case 5:
                return HIT_STAFF;
            case 6:
                return HIT_BOW;
            default:
                throw new RuntimeException("Unknown SoundType index: " + index);
        }
    }
}
