package aeadvent.code2021.day21.istvan;

public class DeterministicDie {
    private int nextValue = 1;
    private int numberOfRolls = 0;

    public int roll() {
        numberOfRolls++;
        var result = nextValue++;

        if (nextValue > 100)
            nextValue = 1;

        return result;
    }

    public int getNumberOfRolls() {
        return numberOfRolls;
    }
}
