package aeadvent.code2021.day17.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class AreaTest {

    private final Area a = new Area(230, 283, -107, -57);

    @Test
    void isInArea_somewhereInside() {
        assertThat(a.isInArea(new Point(250, -60))).isTrue();
    }

    @Test
    void isInArea_toTheLeft() {
        assertThat(a.isInArea(new Point(229, -60))).isFalse();
    }

    @Test
    void isInArea_toTheRight() {
        assertThat(a.isInArea(new Point(284, -60))).isFalse();
    }

    @Test
    void isInArea_above() {
        assertThat(a.isInArea(new Point(250, -56))).isFalse();
    }

    @Test
    void isInArea_below() {
        assertThat(a.isInArea(new Point(250,-108))).isFalse();
    }


}
