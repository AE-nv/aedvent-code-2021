package aeadvent.code2021.day02.istvan;

import org.junit.jupiter.api.Test;

import static aeadvent.code2021.day02.istvan.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AimedSubmarineTest {

    private final AimedSubmarine submarine = new AimedSubmarine();

    @Test
    void startingAimIsZero() {
        assertThat(submarine.getAim()).isEqualTo(0);
    }

    @Test
    void downIncreaseAim() {
        assertThat(submarine
                .move(new MoveInstruction(3, DOWN))
                .getAim())
            .isEqualTo(3);
    }

    @Test
    void doubleDownAccumulatesAim() {
        assertThat(submarine
                .move(new MoveInstruction(3, DOWN))
                .move(new MoveInstruction(5, DOWN))
                .getAim())
            .isEqualTo(8);
    }

    @Test
    void upDecreasesAim() {
        assertThat(
                submarine.move(new MoveInstruction(3, UP))
                        .getAim())
                .isEqualTo(-3);
    }

    @Test
    void doubleUpAccumulatesAim() {
        assertThat(
                submarine.move(new MoveInstruction(3, DOWN))
                        .move(new MoveInstruction(2, UP))
                        .getAim())
                .isEqualTo(1);
    }

    @Test
    void horizontalMoveWith0AimOnlyChangesHorizontal() {
        submarine.move(new MoveInstruction(5, FORWARD));
        assertThat(submarine.getHorizontal()).isEqualTo(5);
        assertThat(submarine.getDepth()).isEqualTo(0);
    }

    @Test
    void horizontalMoveWith1AimAlsoIncreaseDepth() {
        submarine.move(new MoveInstruction(1, DOWN)).move(new MoveInstruction(5, FORWARD));
        assertThat(submarine.getHorizontal()).isEqualTo(5);
        assertThat(submarine.getDepth()).isEqualTo(5);
    }

    @Test
    void negativeAimCantMakeSubFly() {
        submarine.move(new MoveInstruction(1, UP)).move(new MoveInstruction(5, FORWARD));
        assertThat(submarine.getHorizontal()).isEqualTo(5);
        assertThat(submarine.getDepth()).isEqualTo(0);
    }
}
