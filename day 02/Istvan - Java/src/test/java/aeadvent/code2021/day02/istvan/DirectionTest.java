package aeadvent.code2021.day02.istvan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static aeadvent.code2021.day02.istvan.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DirectionTest {

    @ParameterizedTest
    @ValueSource(strings = {"forward", "FORWARD", "fOrWaRd"})
    void createForwardDirectionFromString(final String directionString) {
        assertThat(Direction.fromString(directionString)).isEqualTo(FORWARD);
    }

    @ParameterizedTest
    @ValueSource(strings = {"down", "DOWN", "DoWn"})
    void createDownDirectionFromString(final String directionString) {
        assertThat(Direction.fromString(directionString)).isEqualTo(DOWN);
    }

    @ParameterizedTest
    @ValueSource(strings = {"up", "UP", "uP"})
    void createUpDirectionFromString(final String directionString) {
        assertThat(Direction.fromString(directionString)).isEqualTo(UP);
    }

    @Test
    void unknownDirectionResultsInException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Direction.fromString("unknown"))
                .withMessage("\"unknown\" is an invalid direction");
    }

}