package aeadvent.code2021.day05.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SegmentTest {

    @Test
    void readSegment() {
        Segment segment = Day5.readSegment("0,9 -> 5,9");
        assertThat(segment).isNotNull();
        assertThat(segment.start()).isEqualTo(new Point(0, 9));
        assertThat(segment.end()).isEqualTo(new Point(5, 9));
    }

    @Test
    void isHorizontalGivesTrueForHorizontalSegment() {
        Segment segment = Day5.readSegment("0,9 -> 5,9");
        assertThat(segment.isHorizontal()).isTrue();
    }

    @Test
    void isHorizontalGivesFalseForVerticalSegment() {
        Segment segment = Day5.readSegment("9,0 -> 9,5");
        assertThat(segment.isHorizontal()).isFalse();
    }

}