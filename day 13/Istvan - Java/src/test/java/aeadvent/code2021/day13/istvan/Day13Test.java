package aeadvent.code2021.day13.istvan;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class Day13Test {

    @Test
    void readPoint() {
        assertThat(Day13.readPoint("6,10")).isEqualTo(new FoldingPoint(6,10));
    }

    @Test
    void readFoldInstructionFoldLeftAt2() {
        Function<FoldingPoint, FoldingPoint> foldFunction = Day13.readFoldInstruction("fold along x=2");
        assertThat(foldFunction.apply(new FoldingPoint(3,0))).isEqualTo(new FoldingPoint(1,0));
    }

    @Test
    void readFoldInstructionFoldUpAt2() {
        Function<FoldingPoint, FoldingPoint> foldFunction = Day13.readFoldInstruction("fold along y=2");
        assertThat(foldFunction.apply(new FoldingPoint(3,3))).isEqualTo(new FoldingPoint(3,1));
    }

    @Test
    void partA() {
        assertThat(Day13.partA("testInputA.txt")).isEqualTo(17);
    }



}
