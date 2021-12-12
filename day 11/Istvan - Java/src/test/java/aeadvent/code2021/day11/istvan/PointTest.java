package aeadvent.code2021.day11.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointTest {

    @Test
    void getAdjacentPointsTopLeft() {
        Point p = new Point(0, 0);
        assertThat(p.getAdjacentPoints()).contains(new Point(0, 1), new Point(1, 1), new Point(0, 1));
    }

    @Test
    void getAdjacentPointsTopRight() {
        Point p = new Point(9, 0);
        assertThat(p.getAdjacentPoints()).contains(new Point(8, 0), new Point(8, 1), new Point(9, 1));
    }

    @Test
    void getAdjacentPointsBottomRight() {
        Point p = new Point(9, 9);
        assertThat(p.getAdjacentPoints()).contains(new Point(9, 8), new Point(8, 8), new Point(8, 9));
    }

    @Test
    void getAdjacentPointsBottomLeft() {
        Point p = new Point(0, 9);
        assertThat(p.getAdjacentPoints()).contains(new Point(0, 8), new Point(1, 8), new Point(1, 9));
    }

    @Test
    void getAdjacentPointsNoEdge() {
        Point p = new Point(5, 5);
        assertThat(p.getAdjacentPoints()).contains(new Point(5, 4), new Point(6, 4), new Point(6, 5),
                new Point(6, 6), new Point(5, 6), new Point(4, 6), new Point(4, 5),
                new Point(4, 4));
    }
}

