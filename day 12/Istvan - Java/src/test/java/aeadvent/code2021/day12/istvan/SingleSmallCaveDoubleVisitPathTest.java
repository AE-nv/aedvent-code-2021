package aeadvent.code2021.day12.istvan;

import org.junit.jupiter.api.Test;

import static aeadvent.code2021.day12.istvan.Cave.fromString;
import static org.assertj.core.api.Assertions.assertThat;

public class SingleSmallCaveDoubleVisitPathTest {


    private OnlyMultiVisitBigCavePath p = new SingleSmallCaveDoubleVisitPath(fromString("start"));

    @Test
    void createPath() {
        assertThat(p).isNotNull();
    }

    @Test
    void add() {
        assertThat(p.add(fromString("A")).toString()).isEqualTo("start,A");
    }

    @Test
    void addSameLargeCaveMultipleTimes() {
        var caveA = fromString("A");
        p.add(caveA);
        var response = p.canAdd(caveA);
        assertThat(response).isTrue();
    }

    @Test
    void smallCaveCanBeOnlyTwice() {
        var smallCave = fromString("b");

        var firstResponse = p.canAdd(smallCave);
        p = p.add(smallCave);
        var secondResponse = p.canAdd(smallCave);
        p = p.add(smallCave);
        var thirdResponse = p.canAdd(smallCave);

        assertThat(firstResponse).isTrue();
        assertThat(secondResponse).isTrue();
        assertThat(thirdResponse).isFalse();
        assertThat(p.toString()).isEqualTo("start,b,b");
    }

    @Test
    void startCanBeAddedOnlyOnce() {
        var startCave = fromString("start");
        var response = p.canAdd(startCave);
        assertThat(response).isFalse();
    }


    @Test
    void anySmallCaveCanBeOnlyTwice() {
        var bCave = fromString("b");
        var cCave = fromString("c");

        var firstResponse = p.canAdd(bCave);
        p = p.add(bCave);
        var secondResponse = p.canAdd(cCave);
        p = p.add(cCave);
        var thirdResponse = p.canAdd(bCave);
        p = p.add(bCave);
        var fourthResponse = p.canAdd(cCave);

        assertThat(firstResponse).isTrue();
        assertThat(secondResponse).isTrue();
        assertThat(thirdResponse).isTrue();
        assertThat(fourthResponse).isFalse();
        assertThat(p.toString()).isEqualTo("start,b,c,b");
    }

    @Test
    void isComplete_False() {
        p.add(fromString("x"));
        assertThat(p.isComplete()).isFalse();
    }

    @Test
    void isComplete_True() {
        p = p.add(fromString("x"));
        p = p.add(fromString("end"));
        assertThat(p.isComplete()).isTrue();
        assertThat(p.toString()).isEqualTo("start,x,end");
    }

    @Test
    void canAdd_b_to_startbAcA() {
        var b = fromString("b");
        var A = fromString("A");
        var c = fromString("c");

        p = p.add(b).add(A).add(c).add(A);

        assertThat(p.canAdd(b)).isTrue();
    }

    @Test
    void bugCannotAddNewSmallCaveToParentAfterAddingOtheSmallCaveSecondTime() {
        var b = fromString("b");
        var A = fromString("A");
        var c = fromString("c");

        var root = p.add(b).add(A).add(c).add(A);

        var other = root.add(c);
        assertThat(other).isNotEqualTo(root);
        assertThat(root.canAdd(b)).isTrue();
    }


}
