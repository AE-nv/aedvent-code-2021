package aeadvent.code2021.day02.istvan;

public enum Direction {
    DOWN, UP, FORWARD;

    public static Direction fromString(final String directionString) {
        try {
            return valueOf(directionString.toUpperCase());
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(String.format("\"%s\" is an invalid direction", directionString));
        }
    }
}
