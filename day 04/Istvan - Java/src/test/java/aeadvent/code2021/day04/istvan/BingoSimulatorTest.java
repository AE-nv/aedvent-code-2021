package aeadvent.code2021.day04.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BingoSimulatorTest {

    private BingoSimulator simulator = new BingoSimulator("testInput.txt");

    @Test
    void canReadNumbersToDraw() {
        assertThat(simulator.getNumberToDraw())
                .containsExactly("7", "4", "9", "5", "11", "17", "23", "2", "0", "14", "21", "24", "10", "16", "13",
                        "6", "15", "25", "12", "22", "18", "20", "8", "19", "3", "26", "1");
    }

    @Test
    void getBoardCount() {
        assertThat(simulator.getBoardCount()).isEqualTo(3);
    }

    @Test
    void getScoreForFirstWinningBoard() {
        assertThat(simulator.getScoreForFirstWinningBoard()).isEqualTo(4512);
    }

    @Test
    void getScoreForLastWinningBoard() {
        assertThat(simulator.getScoreForLastWinningBoard()).isEqualTo(1924);
    }
}
