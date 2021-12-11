package aeadvent.code2021.day11.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day11Test {

    @Test
    void createDay11() {
        Day11 d = new Day11("testInput.txt");
        assertThat(d.toString()).isEqualTo("""
                5483143223
                2745854711
                5264556173
                6141336146
                6357385478
                4167524645
                2176841721
                6882881134
                4846848554
                5283751526
                """);
    }

    @Test
    void after1Step() {
        Day11 d = new Day11("testInput.txt");
        d.playStep();
        assertThat(d.toString()).isEqualTo("""
                6594254334
                3856965822
                6375667284
                7252447257
                7468496589
                5278635756
                3287952832
                7993992245
                5957959665
                6394862637
                """);
    }

    @Test
    void after2Step() {
        Day11 d = new Day11("testInput.txt");
        d.playStep();
        d.playStep();
        assertThat(d.toString()).isEqualTo("""
                8807476555
                5089087054
                8597889608
                8485769600
                8700908800
                6600088989
                6800005943
                0000007456
                9000000876
                8700006848
                """);
    }

    @Test
    void after100Steps() {
        Day11 d = new Day11("testInput.txt");
        for (int i = 0; i < 100; i++) {
            d.playStep();
        }
        assertThat(d.toString()).isEqualTo("""
                0397666866
                0749766918
                0053976933
                0004297822
                0004229892
                0053222877
                0532222966
                9322228966
                7922286866
                6789998766
                """);
        assertThat(d.getFlashCount()).isEqualTo(1656);
    }

    @Test
    void findSynchronousFlashStep() {
        Day11 d = new Day11("testInput.txt");
        assertThat(d.findSynchronousFlashStep()).isEqualTo(195);
    }
}
