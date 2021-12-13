package aeadvent.code2021.day13.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FoldingPointTest {

    @Test
    void foldUpFromBelowLine() {
        assertThat(new FoldingPoint(3,3).foldUp(2)).isEqualTo(new FoldingPoint(3,1));
    }

    @Test
    void foldUpFromAboveLine() {
        assertThat(new FoldingPoint(1,1).foldUp(2)).isEqualTo(new FoldingPoint(1,1));
    }

    @Test
    void foldLeftFromBehindLine() {
        assertThat(new FoldingPoint(3,3).foldLeft(2)).isEqualTo(new FoldingPoint(1,3));
    }

    @Test
    void foldLeftFromBeforeLine() {
        assertThat(new FoldingPoint(1,1).foldLeft(2)).isEqualTo(new FoldingPoint(1,1));
    }
}
