package aeadvent.code2021.day02.istvan;

import java.util.Objects;

public class SubmarinePosition {

    private int horizontal;
    private int depth;

    public SubmarinePosition() {
        horizontal = 0;
        depth = 0;
    }

    public long getHorizontal() {
        return horizontal;
    }

    public int getDepth() {
        return depth;
    }

    public SubmarinePosition move (final MoveInstruction moveInstruction) {
        Objects.requireNonNull(moveInstruction, "Instruction can't be null");
        final int distance = moveInstruction.distance();
        switch(moveInstruction.direction()) {
            case DOWN -> moveDown(distance);
            case UP -> moveUp(distance);
            case FORWARD -> moveForward(distance);
        }
        return this;
    }

    public SubmarinePosition move(final int distance, final Direction direction) {
        return move (new MoveInstruction(distance, direction));
    }

    private void moveForward(int distance) {
        this.horizontal += distance;
    }

    private void moveDown(int distance) {
        this.depth += distance;
    }

    private void moveUp(final int distance) {
        this.depth -= distance;
        if (this.depth < 0)
            this.depth = 0;
    }

    @Override
    public String toString() {
        return String.format("SubmarinePostion at %d horizontal and depth %d", this.horizontal, this.depth);
    }
}
