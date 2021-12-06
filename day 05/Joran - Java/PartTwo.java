package be.superjoran.day5;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class PartTwo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        processFile("sample.txt");
        processFile("input.txt");
    }

    public static void processFile(String fileName) throws URISyntaxException, IOException {
        Scanner scanner = new Scanner(Paths.get(Objects.requireNonNull(PartTwo.class.getResource(fileName)).toURI()));
        List<Line> lines = new ArrayList<>();
        while (scanner.hasNext()) {
            String[] split = scanner.next().split(",");
            int x1 = Integer.parseInt(split[0]);
            int y1 = Integer.parseInt(split[1]);
            scanner.next();
            split = scanner.next().split(",");
            int x2 = Integer.parseInt(split[0]);
            int y2 = Integer.parseInt(split[1]);
            lines.add(new Line(new Point(x1, y1), new Point(x2, y2)));
        }

        List<Point> points = lines.stream()
                .flatMap(l -> l.getPointsTraversed().stream())
                .toList();

        Set<Point> uniquePoints = points.stream()
                .collect(Collectors.toSet());

        //just use parallelstream instead of optimize code -> this is why I need the new MacBook M1 Max okay
        long count = uniquePoints.parallelStream()
                .filter(p -> points.stream().filter(p2 -> p2.equals(p)).count() > 1)
                .count();

        System.out.println(count);
    }

    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static class Line {
        Point start;
        Point end;

        public Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        public Direction getDirection() {
            if (start.y == end.y) {
                if (start.x < end.x) {
                    return Direction.RIGHT;
                } else {
                    return Direction.LEFT;
                }
            } else if (start.x == end.x) {
                if (start.y < end.y) {
                    return Direction.UP;
                } else {
                    return Direction.DOWN;
                }
            } else if (start.x < end.x) {
                if (start.y < end.y) {
                    return Direction.UP_RIGHT;
                } else {
                    return Direction.DOWN_RIGHT;
                }
            } else if (start.x > end.x) {
                if (start.y < end.y) {
                    return Direction.UP_LEFT;
                } else {
                    return Direction.DOWN_LEFT;
                }
            }
            return Direction.UNKNOWN;
        }

        public List<Point> getPointsTraversed() {
            List<Point> pointsTraversed = new ArrayList<>();
            pointsTraversed.add(start);

            Point point = start;
            do {

                Point nextPoint = null;
                switch (this.getDirection()) {
                    case RIGHT -> nextPoint = new Point(point.x + 1, point.y);
                    case LEFT -> nextPoint = new Point(point.x - 1, point.y);
                    case UP -> nextPoint = new Point(point.x, point.y + 1);
                    case DOWN -> nextPoint = new Point(point.x, point.y - 1);
                    case DOWN_RIGHT -> nextPoint = new Point(point.x + 1, point.y - 1);
                    case UP_RIGHT -> nextPoint = new Point(point.x + 1, point.y + 1);
                    case DOWN_LEFT -> nextPoint = new Point(point.x - 1, point.y - 1);
                    case UP_LEFT -> nextPoint = new Point(point.x - 1, point.y + 1);
                }

                pointsTraversed.add(nextPoint);
                point = nextPoint;
            } while (!point.equals(end));

            return pointsTraversed;
        }
    }

    public enum Direction {
        UP, DOWN, RIGHT, LEFT, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, UNKNOWN;
    }
}
