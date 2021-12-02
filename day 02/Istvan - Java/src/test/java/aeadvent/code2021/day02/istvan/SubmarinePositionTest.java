package aeadvent.code2021.day02.istvan;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static aeadvent.code2021.day02.istvan.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Fail.fail;

public class SubmarinePositionTest {

    private final SubmarinePosition position = new SubmarinePosition();

    @Test
    @DisplayName("Starting position is at 0 horizontal")
    void startingPostionIsAtZeroHorizontal() {
        assertThat(position.getHorizontal()).isEqualTo(0);
    }

    @Test
    @DisplayName("Starting position is at 0 depth")
    void startingPositionIsAtZeroDepth() {
        assertThat(position.getDepth()).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 3})
    @DisplayName("1 move alters horizontal position")
    void moveForward5AltersHorizontalPositionTo5(final int distance) {
        assertThat(position.move(new MoveInstruction(distance, FORWARD)).getHorizontal()).isEqualTo(distance);
    }

    @Test
    void multipleMovesAccumlateHorizontalPosition() {
        assertThat(
                position.move(new MoveInstruction(3, FORWARD))
                        .move(new MoveInstruction(5, FORWARD))
                        .getHorizontal())
                .isEqualTo(8);
    }

    @Test
    void moveDownIncreasesDepth() {
        assertThat(position.move(new MoveInstruction(3, DOWN)).getDepth()).isEqualTo(3);
    }

    @Test
    @DisplayName("Expect a NullPointerException when null direction is passed")
    void nullDirectionProducesNPE() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> position.move(new MoveInstruction(3, null)))
                .withMessage("Direction can't be null");
    }

    @Test
    void multipleMovesDownAccumulatesDepthIncrease() {
        assertThat(
                position.move(new MoveInstruction(3, DOWN))
                        .move(new MoveInstruction(7, DOWN))
                        .getDepth())
                .isEqualTo(10);
    }

    @Test
    void moveUpFromZeroDepthHasNoEffect() {
        assertThat(position.move(new MoveInstruction(3, UP)).getDepth()).isEqualTo(0);
    }

    @Test
    void moveUpFromSomeDepthDecreasesDepth() {
        assertThat(
                position.move(new MoveInstruction(5,DOWN))
                        .move(new MoveInstruction(3, UP))
                        .getDepth())
                .isEqualTo(2);
    }
}
