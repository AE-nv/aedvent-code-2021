package aeadvent.code2021.day05.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day5 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("Part A: " + partA("input.txt"));
        System.out.println("Part B: " + partB("input.txt"));
    }


    private static List<Point> convertHorizontalSegmentToPoints(final Segment s) {
        final int startX = Math.min(s.start().x(), s.end().x());
        final int endX = Math.max(s.start().x(), s.end().x());
        final int y = s.start().y();

        return  IntStream.rangeClosed(startX, endX)
                .boxed()
                .map(i -> new Point(i, y))
                .toList();
    }


    private static List<Point> convertVerticalSegmentToPoints(Segment s) {
        final int startY = Math.min(s.start().y(), s.end().y());
        final int endY = Math.max(s.start().y(), s.end().y());
        final int x = s.start().x();

        return IntStream.rangeClosed(startY, endY)
                .boxed()
                .map(i -> new Point(x, i))
                .toList();
    }

    public static List<Point> convertDiagonalSegmentToPoints(Segment s) {
        Point leftPoint = null;
        Point rightPoint = null;
        if (s.start().x() < s.end().x()) {
            leftPoint = s.start();
            rightPoint = s.end();
        } else {
            leftPoint = s.end();
            rightPoint = s.start();
        }

        int yIncrement = leftPoint.y() < rightPoint.y() ? 1:-1;

        List<Point> result = new LinkedList<>();
        int y = leftPoint.y();
        for (int x = leftPoint.x(); x <= rightPoint.x(); x++) {
                result.add(new Point(x,y));
                y+= yIncrement;
        }
        return result;
    }

    static List<Point> convertSegmentToPoints(Segment s) {
        if (s.isHorizontal())
            return convertHorizontalSegmentToPoints(s);
        else if (s.isVertical())
            return convertVerticalSegmentToPoints(s);
        else
            return convertDiagonalSegmentToPoints(s);
    }

    public static Segment readSegment(String segmentString) {
        String[] pointStrings = segmentString.split(" -> ");
        String[] startPointCoordinates = pointStrings[0].split(",");
        String[] endPointCoordinates = pointStrings[1].split(",");
        return new Segment(
                new Point(Integer.parseInt(startPointCoordinates[0]), Integer.parseInt(startPointCoordinates[1])),
                new Point(Integer.parseInt(endPointCoordinates[0]), Integer.parseInt(endPointCoordinates[1])));
    }

    public static long partA(String filename) throws URISyntaxException, IOException {
        final Map<Point, Integer> pointCoverage = new HashMap<>();
        Files.lines(Path.of(Day5.class.getClassLoader().getResource(filename).toURI()))
                .map(Day5::readSegment)
                .filter(segment -> segment.isHorizontal() || segment.isVertical())
                .map(Day5::convertSegmentToPoints)
                .flatMap(List::stream)
                .forEach(point -> Day5.addOrUpdateMap(point, pointCoverage));

        return pointCoverage.values().stream().filter(v -> v > 1).count();
    }

    public static long partB(String filename) throws URISyntaxException, IOException {
        final Map<Point, Integer> pointCoverage = new HashMap<>();
        Files.lines(Path.of(Day5.class.getClassLoader().getResource(filename).toURI()))
                .map(Day5::readSegment)
                .map(Day5::convertSegmentToPoints)
                .flatMap(List::stream)
                .forEach(point -> Day5.addOrUpdateMap(point, pointCoverage));

        return pointCoverage.values().stream().filter(v -> v > 1).count();
    }

    private static void addOrUpdateMap(Point point, Map<Point, Integer> pointCoverage) {
        if (pointCoverage.containsKey(point))
            pointCoverage.computeIfPresent(point, (k,v) -> ++v);
        else
            pointCoverage.put(point, 1);
    }
}
