package pl.pw.pap.akari.model.utills;

import java.util.Random;

public enum DIRECTION {
    LEFT, UP, RIGHT, DOWN;
    private static DIRECTION[] values = values();
    private final static Random RANDOM = new Random();

    public static DIRECTION GetRandomDirection() {
        return values[RANDOM.nextInt(values.length)];
    }
}