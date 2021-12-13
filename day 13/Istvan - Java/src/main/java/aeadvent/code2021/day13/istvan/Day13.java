package aeadvent.code2021.day13.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 {

    public static void main(String[] args) {
        System.out.println("Part A: " + partA("inputA.txt"));
        System.out.println("Part B: \n" + partB());
    }



    public static FoldingPoint readPoint(String line) {
        String[] coordinate = line.split(",");
        return new FoldingPoint(Integer.parseInt(coordinate[0]), Integer.parseInt(coordinate[1]));
    }

    public static Function<FoldingPoint, FoldingPoint> readFoldInstruction(String line) {
        final int lineCoordinate = Integer.parseInt(line.substring(line.indexOf("=") + 1));
        if (line.contains("x"))
            return (FoldingPoint p) -> p.foldLeft(lineCoordinate);
        else
            return (FoldingPoint p) -> p.foldUp(lineCoordinate);
    }


    public static int partA(String inputFile) {
        Set<FoldingPoint> points = new HashSet<>();
        List<Function<FoldingPoint, FoldingPoint>> foldInstructions = new LinkedList<>();
        fillUpPointsAndInstructionsFromFile(inputFile, points, foldInstructions);

        return applyFoldingInstructions(points, foldInstructions).size();

    }

    private static void fillUpPointsAndInstructionsFromFile(String inputFile, Set<FoldingPoint> points, List<Function<FoldingPoint, FoldingPoint>> foldInstructions) {
        try (Stream<String> lineStream = Files.lines(Path.of(Objects.requireNonNull(Day13.class.getClassLoader().getResource(inputFile)).toURI()))) {
            lineStream
                    .filter(Predicate.not(String::isBlank))
                    .forEach(s -> {
                        if (s.contains(","))
                            points.add(readPoint(s));
                        else
                            foldInstructions.add(readFoldInstruction(s));
                    });
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read from " + inputFile);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Couldn't build path to " + inputFile);
        }
    }

    private static Set<FoldingPoint> applyFoldingInstructions(final Set<FoldingPoint> startingPoints, List<Function<FoldingPoint, FoldingPoint>> foldInstructions) {
        Set<FoldingPoint> points = startingPoints;
        foldInstructions.forEach(f -> {
            var tempSet = startingPoints.stream()
                    .map(f)
                    .collect(Collectors.toSet());
            points.clear();
            points.addAll(tempSet);

        });
        return points;
    }

    private static String partB() {
        Set<FoldingPoint> points = new HashSet<>();
        List<Function<FoldingPoint, FoldingPoint>> foldingInstructions = new LinkedList<>();
        fillUpPointsAndInstructionsFromFile("inputB.txt", points, foldingInstructions);
        Set<FoldingPoint> pointsAfterFolding = applyFoldingInstructions(points, foldingInstructions);

        return convertToString(pointsAfterFolding);
    }

    private static String convertToString(Set<FoldingPoint> pointsAfterFolding) {
        int maxX = pointsAfterFolding.stream().map(FoldingPoint::x).max(Integer::compareTo).orElse(0);
        int maxY = pointsAfterFolding.stream().map(FoldingPoint::y).max(Integer::compareTo).orElse(0);

        String[][] field = new String[maxX+1][maxY+1];
        pointsAfterFolding.forEach(p -> field[p.x()][p.y()] = "*");
        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < maxY+1; y++) {
            for (int x = 0; x < maxX+1; x++)
                builder.append(field[x][y]==null?" ":field[x][y]);
            builder.append("\n");
        }

        return builder.toString();

    }
}
