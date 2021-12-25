package aeadvent.code2021.day25.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day25Test {

    @Test
    void create() {
        Day25 d = new Day25("testInput.txt");
        assertThat(d).isNotNull();
        assertThat(d.getWidth()).isEqualTo(10);
        assertThat(d.getHeight()).isEqualTo(9);
        assertThat(d.eastHerd.size()).isEqualTo(23);
        assertThat(d.southHerd.size()).isEqualTo(26);
    }

    @Test
    void to_String() {
        Day25 d = new Day25("testInput.txt");
        assertThat(d.toString()).isEqualTo("""
                v...>>.vv>
                .vv>>.vv..
                >>.>v>...v
                >>v>>.>.v.
                v>v.vv.v..
                >.>>..v...
                .vv..>.>v.
                v.v..>>v.v
                ....v..v.>""");
    }

    @Test
    void move() {
        Day25 d = new Day25("testInput.txt");
        d.move();
        assertThat(d.toString()).isEqualTo("""
                ....>.>v.>
                v.v>.>v.v.
                >v>>..>v..
                >>v>v>.>.v
                .>v.v...v.
                v>>.>vvv..
                ..v...>>..
                vv...>>vv.
                >.v.v..v.v""");
    }

    @Test
    void partA() {
        Day25 d = new Day25("testInput.txt");
        assertThat(d.partA()).isEqualTo(58);
    }
}
