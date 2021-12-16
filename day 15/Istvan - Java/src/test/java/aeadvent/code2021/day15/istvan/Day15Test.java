package aeadvent.code2021.day15.istvan;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day15Test {

    @Test
    void readFile() {
        var costs = Day15.readCosts("testInput.txt");
        assertThat(costs).hasSize(100);
    }

    @Test
    void partA() {
        assertThat(Day15.partA(Day15.readCosts("testInput.txt"))).isEqualTo(40L);
    }

    @Test
    void partB() {
        assertThat(Day15.partB(Day15.readCosts("testInput.txt"))).isEqualTo(315L);
    }

    @Test
    void partA_smallTestInput() {
        assertThat(Day15.partA(Day15.readCosts("smallTestInput.txt"))).isEqualTo(17L);
    }

}
