package aeadvent.code2021.day12.istvan;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static aeadvent.code2021.day12.istvan.Cave.fromString;

public class Day12 {

    Map<Cave, List<Cave>> readCaveSystem(String inputFile) {
        Map<Cave, List<Cave>> result = new HashMap<>();
        try (var lineStream = Files.lines(Path.of(Objects.requireNonNull(this.getClass().getClassLoader().getResource(inputFile)).toURI()))) {
            lineStream.forEach(l -> {
                var caves = l.split("-");
                var first = fromString(caves[0]);
                var second = fromString(caves[1]);
                result.merge(first, List.of(second),this::mergLists);
                result.merge(second, List.of(first), this::mergLists);
            });
        } catch (Exception e) {
            throw new RuntimeException("Exception while reading " + inputFile);
        }
        return result;
    }

    List<OnlyMultiVisitBigCavePath> findPaths(Map<Cave,List<Cave>> caveSystem, OnlyMultiVisitBigCavePath startPath) {
        List<OnlyMultiVisitBigCavePath> completedPaths = new LinkedList<>();
        List<Cave> reachableFromStart = caveSystem.get(fromString("start"));
        for (Cave c : reachableFromStart) {
            if (startPath.canAdd(c)) {
                continuePathFrom(c, startPath, caveSystem, completedPaths);
            }
        }
        return completedPaths;
    }

    void continuePathFrom(Cave currentCave, OnlyMultiVisitBigCavePath pathSoFar, Map<Cave, List<Cave>> caveSystem, List<OnlyMultiVisitBigCavePath> completedPaths) {
        var newPath = pathSoFar.add(currentCave);
        if (newPath.isComplete()) {
            completedPaths.add(newPath);
            return;
        }

        for (Cave nextCave : caveSystem.get(currentCave)) {
            if (newPath.canAdd(nextCave)) {
                continuePathFrom(nextCave, newPath, caveSystem, completedPaths);
            }
        }
    }

    private List<Cave> mergLists(List<Cave> old, List<Cave> n) {
        List<Cave> combined = new ArrayList<>(old.size()+ n.size());
        combined.addAll(old);
        combined.addAll(n);
        return combined;
    }

    public static void main(String[] args) {
        Day12 d = new Day12();
        System.out.println("Part A: " + d.findPaths(d.readCaveSystem("input.txt"), new OnlyMultiVisitBigCavePath(fromString("start"))).size());

        System.out.println("Part B: " + d.findPaths(d.readCaveSystem("input.txt"), new SingleSmallCaveDoubleVisitPath(fromString("start"))).size());
    }
}
