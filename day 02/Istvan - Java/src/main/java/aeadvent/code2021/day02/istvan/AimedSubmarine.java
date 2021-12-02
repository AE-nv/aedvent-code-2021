package aeadvent.code2021.day02.istvan;

public class AimedSubmarine extends Submarine {

    private int aim = 0;

    public int getAim() {
        return this.aim;
    }

    @Override
    public AimedSubmarine move(MoveInstruction moveInstruction) {
        switch (moveInstruction.direction()) {
            case DOWN -> this.aim += moveInstruction.units();
            case UP -> this.aim -= moveInstruction.units();
            case FORWARD -> this.moveForward(moveInstruction.units());
        }

        return this;
    }

    @Override
    protected void moveForward(int distance) {
        super.moveForward(distance);
        if (aim > 0)
            this.moveDown(distance*aim);
        else
            this.moveUp(distance*(aim*-1));
    }
}
