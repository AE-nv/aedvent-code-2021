package aeadvent.code2021.day15.istvan;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day15 {
    private static Long lowestRiskSoFar = Long.MAX_VALUE;

    public static void main(String[] args) {
        var grid = readCosts("input.txt");
        long start = System.currentTimeMillis();
        System.out.println("Part A :" + Day15.partA(grid));
        long endA = System.currentTimeMillis();
        System.out.println("Duration A: " + (endA - start));
        System.out.println("Part B :" + Day15.partB(grid));
        System.out.println("Duration B: " + (System.currentTimeMillis() - endA));
    }

    public static Map<Vertex, Integer> readCosts(String filename) {
        Map<Vertex, Integer> result = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(Objects.requireNonNull(Day15.class.getClassLoader().getResource(filename)).toURI()));
            for (int y = 0; y < lines.size(); y++) {
                int finalY = y;
                IntStream.range(0, lines.get(y).length())
                        .boxed()
                        .forEach(x -> result.put(new Vertex(x, finalY), Character.getNumericValue(lines.get(finalY).charAt(x))));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Could not read file " + filename, e);
        }
    }

    public static long partA(Map<Vertex, Integer> costs) {
        final Vertex targetVertex = new Vertex(getMaxX(costs.keySet()), getMaxY(costs.keySet()));
        var distances = initializeDistances(costs.keySet());
        Map<Vertex, Long> fixedDistances = new HashMap<>();
        Set<Vertex> shortestPathTree = new HashSet<>();

        while (!shortestPathTree.contains(targetVertex)) {
            Vertex currentVertex = getVertexWithMinimalDistance(distances, shortestPathTree);
            currentVertex.getNeighbours().stream()
                    .filter(costs::containsKey)
                    .filter(Predicate.not(shortestPathTree::contains))
                    .forEach(v -> {
                        var distanceToV = distances.get(currentVertex) + costs.get(v);
                        if (distanceToV < distances.get(v)) {
                            distances.put(v, distanceToV);
                        }
                    });
            shortestPathTree.add(currentVertex);
            fixedDistances.put(currentVertex, distances.get(currentVertex));
            distances.remove(currentVertex);
            if (shortestPathTree.size()%1000 == 0) {
                System.out.println("Resolved " + shortestPathTree.size() + " of " + distances.size());
            }
        }

        return getDistanceToRightBottom(fixedDistances);

    }

    private static long getDistanceToRightBottom(Map<Vertex, Long> distances) {
        return distances.get(new Vertex(getMaxX(distances.keySet()), getMaxY(distances.keySet())));
    }

    private static int getMaxX(Set<Vertex> vertices) {
        return vertices.stream().map(Vertex::x).max(Integer::compareTo).get();
    }

    private static int getMaxY(Set<Vertex> vertices) {
        return vertices.stream().map(Vertex::y).max(Integer::compareTo).get();
    }

    private static Vertex getVertexWithMinimalDistance(Map<Vertex, Long> distances, Set<Vertex> spt) {
        var currMinDistance = Long.MAX_VALUE;
        Vertex selected = null;

        for (var e: distances.entrySet()) {
            if (!spt.contains(e.getKey())) {
                if (e.getValue() < currMinDistance) {
                    selected = e.getKey();
                    currMinDistance = e.getValue();
                }
            }
        }
        return selected;
    }

    private static Map<Vertex, Long> initializeDistances(Set<Vertex> vertices) {
        var result = vertices.stream()
                .collect(Collectors.toMap(Function.identity(), v -> Long.MAX_VALUE));
        result.put(new Vertex(0, 0), 0L);
        return result;
    }


    public static long partB(Map<Vertex, Integer> tileCosts) {
        return partA(expandCosts(tileCosts));
    }

    private static Map<Vertex, Integer> expandCosts(Map<Vertex, Integer> tileCosts) {
        var sizeX = getMaxX(tileCosts.keySet())+1;
        var sizeYY = getMaxX(tileCosts.keySet())+1;

        Map<Vertex, Integer> result = new HashMap<>();

        for (var e : tileCosts.entrySet()) {
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    result.put(new Vertex(e.getKey().x()+x*sizeX, e.getKey().y() + y*sizeYY), calcNewCost(e.getValue(),x+y));
                }

            }
        }


        return result;
    }

    private static int calcNewCost(Integer c, int increment) {
        return c + increment > 9 ? c+increment-9: c+increment;
    }

    record Vertex(int x, int y) {

        List<Vertex> getNeighbours() {
            return List.of(new Vertex(x, y - 1), new Vertex(x + 1, y), new Vertex(x, y + 1), new Vertex(x - 1, y));
        }

    }
}
