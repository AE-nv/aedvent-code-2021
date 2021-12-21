package aeadvent.code2021.day21.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeterministicDieTest {

    private final DeterministicDie dice = new DeterministicDie();

    @Test
    void roll_once() {
        assertThat(dice.roll()).isEqualTo(1);
        assertThat(dice.getNumberOfRolls()).isEqualTo(1);
    }

    @Test
    void roll_hundred_times() {
        for (int rollNumber = 1; rollNumber <=100 ; rollNumber++) {
            assertThat(dice.roll()).isEqualTo(rollNumber);
            assertThat(dice.getNumberOfRolls()).isEqualTo(rollNumber);
        }
    }

    @Test
    void roll_hundredAndOne_Times() {
        for (int i = 0; i <100; i++) {
            dice.roll();
        }

        assertThat(dice.roll()).isEqualTo(1);
        assertThat(dice.getNumberOfRolls()).isEqualTo(101);
    }
}
