package aeadvent.code2021.day12.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CaveTest {

    @Test
    void fromString() {
        Cave c = Cave.fromString("");
        assertThat(c).isNotNull();
    }

    @Test
    void isBig_true() {
        Cave c = Cave.fromString("Q");
        assertThat(c.isBig()).isTrue();
    }

    @Test
    void isBig_false() {
        Cave c = Cave.fromString("z");
        assertThat(c.isBig()).isFalse();
    }

    @Test
    void isStart_true() {
        Cave c = Cave.fromString("start");
        assertThat(c.isStart()).isTrue();
    }

    @Test
    void isStart_false() {
        Cave c = Cave.fromString("end");
        assertThat(c.isStart()).isFalse();
    }

    @Test
    void isEnd_false() {
        assertThat(Cave.fromString("start").isEnd()).isFalse();
    }

    @Test
    void isEnd_true() {
        assertThat(Cave.fromString("end").isEnd()).isTrue();
    }
}
