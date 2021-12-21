package aeadvent.code2021.day21.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day21Test {

    @Test
    void partA() {
        assertThat(Day21.partA(4, 8)).isEqualTo(739785);
    }

    @Test
    void partB() {
        assertThat(Day21.partB(4,8)).isEqualTo(444356092776315L);
    }
}
