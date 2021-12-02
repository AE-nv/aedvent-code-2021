package aeadvent.code2021.day02.istvan;

import java.util.Objects;

public record MoveInstruction(int distance, Direction direction) {

    public MoveInstruction(int distance, Direction direction) {
        Objects.requireNonNull(direction, "Direction can't be null");
        this.distance = distance;
        this.direction = direction;
    }

    static MoveInstruction fromString(String movementString) {
        String[] parts = movementString.split(" ");
        return new MoveInstruction(Integer.parseInt(parts[1]), Direction.fromString(parts[0]));
    }
}
