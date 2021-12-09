package aeadvent.code2021.day09.istvan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CaveMapTest {

    private CaveMap map;

    @BeforeEach
    void buildMap() throws Exception {
        map = CaveMap.fromFile("testInput.txt");
    }

    @Test
    void buildCaveMap() {
        assertThat(map).isNotNull();
    }

    @Test
    void isLowPoint_1_1() {
        assertThat(map.isLowPoint(1, 1)).isFalse();
    }

    @Test
    void isLowPoint_2_2() {
        assertThat(map.isLowPoint(2, 2)).isTrue();
    }

    @Test
    void isLowPoint_0_4() {
        assertThat(map.isLowPoint(0, 4)).isFalse();
    }

    @Test
    void isLowPoint_9_0() {
        assertThat(map.isLowPoint(9, 0)).isTrue();
    }

    @Test
    void calculateRiskLevel() {
        assertThat(map.calculateRiskLevel()).isEqualTo(15);
    }

    @Test
    void getBasins() {
        assertThat(map.getBasins()).containsExactlyInAnyOrder(3, 9, 14, 9);
    }
}
