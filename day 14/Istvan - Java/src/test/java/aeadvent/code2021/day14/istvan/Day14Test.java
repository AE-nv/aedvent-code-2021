package aeadvent.code2021.day14.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day14Test {

    @Test
    void fromFile() {
        Day14 d = Day14.fromFile("testInput.txt");
        assertThat(d.rules).containsOnlyKeys(
                "CH", "HH", "CB", "NH", "HB", "HC", "HN", "NN", "BH", "NC", "NB", "BN", "BB", "BC", "CC", "CN");
        assertThat(d.rules).containsValues("B", "N", "H", "C");
    }

    @Test
    void partA() {
        assertThat(Day14.partA("testInput.txt")).isEqualTo(1588);
    }
}
