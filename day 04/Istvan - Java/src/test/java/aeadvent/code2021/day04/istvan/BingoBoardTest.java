package aeadvent.code2021.day04.istvan;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class BingoBoardTest {


    public static final String BOARD_LAYOUT = """
            14 21 17 24  4
            10 16 15  9 19
            18  8 23 26 20
            22 11 13  6  5
             2  0 12  3  7
            """;
    private BingoBoard board = new BingoBoard(BOARD_LAYOUT);

    @Test
    void markingExistingNumberReturnsTrueButDoesNotGiveBingo() {
        assertThat(board.mark("23")).isTrue();
        assertThat(board.isBingo()).isFalse();
    }

    @Test
    void markNotExistingNumberReturnsFalse() {
        assertThat(board.mark("60")).isFalse();
    }

    @Test
    void drawingFiveOnSameRowGivesBingo() {
        List.of("14","21","17", "24", "4").forEach(board::mark);
        assertThat(board.isBingo()).isTrue();
    }

    @Test
    void drawingFiveOnSameColumnGivesBingo() {
        List.of("14","10","18","2", "22").forEach(board::mark);
        assertThat(board.isBingo()).isTrue();
    }

    @Test
    void getUnmarkedValuesAfterBingo() {
        List.of("14","10","18","2", "22").forEach(board::mark);
        assertThat(board.getUnmarkedValue()).containsOnly("21","16","8","11","0","17","15","23","13","12","24","9","26","6","3","4","19","20","5","7");
    }

}
