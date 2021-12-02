package aeadvent.code2021.day02.istvan;

import java.util.Objects;

public class Submarine {

    private int horizontal;
    private int depth;

    public Submarine() {
        horizontal = 0;
        depth = 0;
    }

    public long getHorizontal() {
        return horizontal;
    }

    public int getDepth() {
        return depth;
    }

    public Submarine move (final MoveInstruction moveInstruction) {
        Objects.requireNonNull(moveInstruction, "Instruction can't be null");
        final int distance = moveInstruction.units();
        switch(moveInstruction.direction()) {
            case DOWN -> moveDown(distance);
            case UP -> moveUp(distance);
            case FORWARD -> moveForward(distance);
        }
        return this;
    }

    protected void moveForward(int distance) {
        this.horizontal += distance;
    }

    protected void moveDown(int distance) {
        this.depth += distance;
    }

    protected void moveUp(final int distance) {
        this.depth -= distance;
        if (this.depth < 0)
            this.depth = 0;
    }

    @Override
    public String toString() {
        return String.format("Submarine at %d horizontal and depth %d", this.horizontal, this.depth);
    }
}
