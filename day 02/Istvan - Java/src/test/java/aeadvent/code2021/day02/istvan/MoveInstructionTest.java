package aeadvent.code2021.day02.istvan;

import org.junit.jupiter.api.Test;

import static aeadvent.code2021.day02.istvan.Direction.FORWARD;
import static org.assertj.core.api.Assertions.assertThat;

class MoveInstructionTest {

    @Test
    void createFromString() {
        MoveInstruction instruction = MoveInstruction.fromString("forward 5");
        assertThat(instruction.direction()).isEqualTo(FORWARD);
    }

}