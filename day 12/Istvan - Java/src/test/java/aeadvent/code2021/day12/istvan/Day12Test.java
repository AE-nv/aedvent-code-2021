package aeadvent.code2021.day12.istvan;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static aeadvent.code2021.day12.istvan.Cave.fromString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class Day12Test {

    @Test
    void readCaveSystem() {
        Day12 d = new Day12();
        Map<Cave, List<Cave>> caveSystem = d.readCaveSystem("smallestExample.txt");
        assertThat(caveSystem)
                .isNotNull()
                .containsOnlyKeys(fromString("start"), fromString("A"), fromString("b"), fromString("c"),fromString("d"), fromString("end"));
        assertThat(caveSystem.get(fromString("start"))).containsExactlyInAnyOrder(fromString("A"),fromString("b"));
        assertThat(caveSystem.get(fromString("c"))).containsExactlyInAnyOrder(fromString("A"));
        assertThat(caveSystem.get(fromString("A"))).containsExactlyInAnyOrder(fromString("start"), fromString("b"), fromString("end"), fromString("c"));
        assertThat(caveSystem.get(fromString("b"))).containsExactlyInAnyOrder(fromString("start"), fromString("A"), fromString("d"), fromString("end"));
        assertThat(caveSystem.get(fromString("d"))).containsExactlyInAnyOrder(fromString("b"));
        assertThat(caveSystem.get(fromString("end"))).containsExactlyInAnyOrder(fromString("A"), fromString("b"));
    }

    @Test
    void findPaths_smallestExample() {
        var d = new Day12();
        List<OnlyMultiVisitBigCavePath> paths = d.findPaths(d.readCaveSystem("smallestExample.txt"), new OnlyMultiVisitBigCavePath(fromString("start")));
        assertThat(paths).hasSize(10);
        var pathStrings = paths.stream().map(OnlyMultiVisitBigCavePath::toString).toList();
        assertThat(pathStrings).containsExactlyInAnyOrder(
                "start,A,b,A,c,A,end",
                "start,A,b,A,end",
                "start,A,b,end",
                "start,A,c,A,b,A,end",
                "start,A,c,A,b,end",
                "start,A,c,A,end",
                "start,A,end",
                "start,b,A,c,A,end",
                "start,b,A,end",
                "start,b,end"
        );
    }

    @Test
    void findPaths_slightlyLargerExample() {
        var d = new Day12();
        List<OnlyMultiVisitBigCavePath> paths = d.findPaths(d.readCaveSystem("slightlyLargerExample.txt"), new OnlyMultiVisitBigCavePath(fromString("start")));
        assertThat(paths).hasSize(19);
        var pathStrings = paths.stream().map(OnlyMultiVisitBigCavePath::toString).toList();
        assertThat(pathStrings).containsExactlyInAnyOrder(
                "start,HN,dc,HN,end",
                "start,HN,dc,HN,kj,HN,end",
                "start,HN,dc,end",
                "start,HN,dc,kj,HN,end",
                "start,HN,end",
                "start,HN,kj,HN,dc,HN,end",
                "start,HN,kj,HN,dc,end",
                "start,HN,kj,HN,end",
                "start,HN,kj,dc,HN,end",
                "start,HN,kj,dc,end",
                "start,dc,HN,end",
                "start,dc,HN,kj,HN,end",
                "start,dc,end",
                "start,dc,kj,HN,end",
                "start,kj,HN,dc,HN,end",
                "start,kj,HN,dc,end",
                "start,kj,HN,end",
                "start,kj,dc,HN,end",
                "start,kj,dc,end"
        );
    }

    @Test
    void findPaths_evenLargerExample() {
        var d = new Day12();
        List<OnlyMultiVisitBigCavePath> paths = d.findPaths(d.readCaveSystem("evenLargerExample.txt"), new OnlyMultiVisitBigCavePath(fromString("start")));
        assertThat(paths).hasSize(226);
    }

    @Test
    void findPaths_smallestExample_withRevisit() {
        var d = new Day12();
        List<OnlyMultiVisitBigCavePath> paths = d.findPaths(d.readCaveSystem("smallestExample.txt"), new SingleSmallCaveDoubleVisitPath(fromString("start")));
        assertThat(paths).hasSize(36);
        var pathStrings = paths.stream().map(OnlyMultiVisitBigCavePath::toString).toList();
        assertThat(pathStrings).containsExactlyInAnyOrder(
                "start,A,b,A,b,A,c,A,end",
                "start,A,b,A,b,A,end",
                "start,A,b,A,b,end",
                "start,A,b,A,c,A,b,A,end",
                "start,A,b,A,c,A,b,end",
                "start,A,b,A,c,A,c,A,end",
                "start,A,b,A,c,A,end",
                "start,A,b,A,end",
                "start,A,b,d,b,A,c,A,end",
                "start,A,b,d,b,A,end",
                "start,A,b,d,b,end",
                "start,A,b,end",
                "start,A,c,A,b,A,b,A,end",
                "start,A,c,A,b,A,b,end",
                "start,A,c,A,b,A,c,A,end",
                "start,A,c,A,b,A,end",
                "start,A,c,A,b,d,b,A,end",
                "start,A,c,A,b,d,b,end",
                "start,A,c,A,b,end",
                "start,A,c,A,c,A,b,A,end",
                "start,A,c,A,c,A,b,end",
                "start,A,c,A,c,A,end",
                "start,A,c,A,end",
                "start,A,end",
                "start,b,A,b,A,c,A,end",
                "start,b,A,b,A,end",
                "start,b,A,b,end",
                "start,b,A,c,A,b,A,end",
                "start,b,A,c,A,b,end",
                "start,b,A,c,A,c,A,end",
                "start,b,A,c,A,end",
                "start,b,A,end",
                "start,b,d,b,A,c,A,end",
                "start,b,d,b,A,end",
                "start,b,d,b,end",
                "start,b,end"
        );
    }

    @Test
    void findPaths_slightlyLargerExample_withRevisit() {
        var d = new Day12();
        List<OnlyMultiVisitBigCavePath> paths = d.findPaths(d.readCaveSystem("slightlyLargerExample.txt"), new SingleSmallCaveDoubleVisitPath(fromString("start")));
        assertThat(paths).hasSize(103);
    }

    @Test
    void findPaths_evenLargerExample_withRevisit() {
        var d = new Day12();
        List<OnlyMultiVisitBigCavePath> paths = d.findPaths(d.readCaveSystem("evenLargerExample.txt"), new SingleSmallCaveDoubleVisitPath(fromString("start")));
        assertThat(paths).hasSize(3509);
    }

}
