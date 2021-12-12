package aeadvent.code2021.day12.istvan;

import org.junit.jupiter.api.Test;

import static aeadvent.code2021.day12.istvan.Cave.fromString;
import static org.assertj.core.api.Assertions.assertThat;

public class OnlyMultiVisitBigCavePathTest {


    @Test
    void createPath() {
        var p = new OnlyMultiVisitBigCavePath(fromString("start"));
        assertThat(p).isNotNull();
    }

    @Test
    void add() {
        var p = new OnlyMultiVisitBigCavePath(fromString("start"));
        assertThat(p.add(fromString("A")).toString()).isEqualTo("start,A");
    }

    @Test
    void addSameLargeCaveMultipleTimes() {
        var p = new OnlyMultiVisitBigCavePath(fromString("start"));
        var caveA = fromString("A");
        p.add(caveA);
        var response = p.canAdd(caveA);
        assertThat(response).isTrue();
    }

    @Test
    void smallCaveCanBeAddedOnlyOnce() {
        var p = new OnlyMultiVisitBigCavePath(fromString("start"));
        var smallCave = fromString("b");

        var firstResponse = p.canAdd(smallCave);
        p = p.add(smallCave);
        var secondResponse = p.canAdd(smallCave);

        assertThat(firstResponse).isTrue();
        assertThat(secondResponse).isFalse();
        assertThat(p.toString()).isEqualTo("start,b");
    }

    @Test
    void isComplete_False() {
        var p = new OnlyMultiVisitBigCavePath(fromString("start"));
        p.add(fromString("x"));
        assertThat(p.isComplete()).isFalse();
    }

    @Test
    void isComplete_True() {
        var p = new OnlyMultiVisitBigCavePath(fromString("start"));
        p = p.add(fromString("x"));
        p = p.add(fromString("end"));
        assertThat(p.isComplete()).isTrue();
        assertThat(p.toString()).isEqualTo("start,x,end");
    }



}
