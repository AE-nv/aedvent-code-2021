package aeadvent.code2021.day09.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CaveMap {

    private final List<List<Integer>> rows;

    public CaveMap(List<List<Integer>> rows) {
        this.rows = rows;
    }

    public static CaveMap fromFile(String fileName) throws URISyntaxException, IOException {
        return new CaveMap(Files.lines(Path.of(Objects.requireNonNull(CaveMap.class.getClassLoader().getResource(fileName)).toURI()))
                .map(CaveMap::splitLine)
                .toList());
    }

    private static List<Integer> splitLine(String line) {
        List<Integer> row = new ArrayList<>(line.length());
        for (char c : line.toCharArray())
            row.add(Integer.parseInt(String.valueOf(c)));
        return row;
    }

    public boolean isLowPoint(int x, int y) {
        int pointHeight = rows.get(y).get(x);
        return pointHeight < getLeftHeight(x, y)
                && pointHeight < getUpHeight(x, y)
                && pointHeight < getRightHeight(x, y)
                && pointHeight < getDownHeight(x, y);
    }

    private int getLeftHeight(int x, int y) {
        if (x == 0)
            return Integer.MAX_VALUE;
        else
            return rows.get(y).get(x - 1);
    }

    private int getUpHeight(int x, int y) {
        if (y == 0)
            return Integer.MAX_VALUE;
        else
            return rows.get(y - 1).get(x);
    }

    private int getRightHeight(int x, int y) {
        if (x == rows.get(y).size() - 1)
            return Integer.MAX_VALUE;
        else
            return rows.get(y).get(x + 1);
    }

    private int getDownHeight(int x, int y) {
        if (y == rows.size() - 1)
            return Integer.MAX_VALUE;
        else
            return rows.get(y + 1).get(x);
    }

    public int calculateRiskLevel() {
        int riskLevel = 0;
        for (int y = 0; y < rows.size(); y++) {
            List<Integer> row = rows.get(y);
            for (int x = 0; x < row.size(); x++) {
                if (isLowPoint(x, y))
                    riskLevel += (rows.get(y).get(x) + 1);
            }
        }
        return riskLevel;
    }

    public List<Integer> getBasins() {
        List<Integer> basins = new LinkedList<>();
        for (int y = 0; y < rows.size(); y++) {
            List<Integer> row = rows.get(y);
            for (int x = 0; x < row.size(); x++) {
                if (isLowPoint(x, y))
                    basins.add(calculateBasinSize(x, y));
            }
        }
        return basins;
    }

    private Integer calculateBasinSize(int x, int y) {
        Set<Point> explored = new HashSet<>();

        return explore(new Point(x, y), explored);
    }

    private Integer explore(Point p, Set<Point> explored) {
        if (fallsOutsideMap(p) || isTooHigh(p) || explored.contains(p))
            return 0;

        explored.add(p);
        return 1
                + explore(p.getLeft(), explored)
                + explore(p.getUp(), explored)
                + explore(p.getRight(), explored)
                + explore(p.getDown(), explored);

    }

    private boolean fallsOutsideMap(Point p) {
        return p.x < 0
                || p.x >= rows.get(0).size()
                || p.y < 0
                || p.y >= rows.size();
    }

    private boolean isTooHigh(Point p) {
        return rows.get(p.y).get(p.x) == 9;
    }

    private record Point(int x, int y) {
        public Point getLeft() {
            return new Point(x - 1, y);
        }

        public Point getUp() {
            return new Point(x, y - 1);
        }

        public Point getRight() {
            return new Point(x + 1, y);
        }

        public Point getDown() {
            return new Point(x, y + 1);
        }
    }
}
