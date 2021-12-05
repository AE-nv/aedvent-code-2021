package aeadvent.code2021.day05.istvan;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day5Test {

    @Test
    void convertHorizontalSegmentToPoints() {
        List<Point> points =
                Day5.convertSegmentToPoints(new Segment(new Point(2, 7), new Point(6, 7)));
        assertThat(points).hasSize(5);
        assertThat(points).containsExactly(
                new Point(2, 7),
                new Point(3, 7),
                new Point(4, 7),
                new Point(5, 7),
                new Point(6, 7));
    }

    @Test
    void convertVerticalSegmentToPoints() {
        List<Point> points =
                Day5.convertSegmentToPoints(new Segment(new Point(7, 6), new Point(7, 2)));
        assertThat(points).hasSize(5);
        assertThat(points).containsExactly(
                new Point(7, 2),
                new Point(7, 3),
                new Point(7, 4),
                new Point(7, 5),
                new Point(7, 6));
    }

    @Test
    void convertDiagonalSegmentToPoints() {
        List<Point> points =
                Day5.convertSegmentToPoints(new Segment(new Point(8, 2), new Point(5, 5)));
        assertThat(points).hasSize(4);
        assertThat(points).containsExactly(
                new Point(5, 5),
                new Point(6, 4),
                new Point(7, 3),
                new Point(8, 2));
    }


    @Test
    void partA() throws URISyntaxException, IOException {
        long numberOfPointsCoveredByAtLestTwoSegments = Day5.partA("testInput.txt");
        assertThat(numberOfPointsCoveredByAtLestTwoSegments).isEqualTo(5);
    }

    @Test
    void partB() throws URISyntaxException, IOException {
        long numberOfPointsCoveredByAtLestTwoSegments = Day5.partB("testInput.txt");
        assertThat(numberOfPointsCoveredByAtLestTwoSegments).isEqualTo(12);
    }
}
