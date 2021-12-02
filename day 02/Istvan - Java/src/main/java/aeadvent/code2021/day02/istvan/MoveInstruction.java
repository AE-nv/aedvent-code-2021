package aeadvent.code2021.day02.istvan;

import java.util.Objects;

public record MoveInstruction(int units, Direction direction) {

    public MoveInstruction {
        Objects.requireNonNull(direction, "Direction can't be null");
    }

    static MoveInstruction fromString(String movementString) {
        String[] parts = movementString.split(" ");
        return new MoveInstruction(Integer.parseInt(parts[1]), Direction.fromString(parts[0]));
    }
}
