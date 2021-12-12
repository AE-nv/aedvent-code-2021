package aeadvent.code2021.day11.istvan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public record Point(int x, int y) {

    public List<Point> getAdjacentPoints() {
        List<Point> result = new LinkedList<>();

        result.addAll(getLeftNeighbours());
        result.addAll(getRightNeighbours());
        result.addAll(getStrictVerticalNeighbours(this));

        return result;
    }

    private List<Point> getStrictVerticalNeighbours(Point point) {
        List<Point> result = new ArrayList<>(2);
        if (!isOnTopEdge(point))
            result.add(new Point(point.x, point.y - 1));

        if (!isOnBottomEdge(point))
            result.add(new Point(point.x, point.y + 1));

        return result;
    }

    private List<Point> getRightNeighbours() {
        if (isOnRightEdge())
            return Collections.emptyList();

        List<Point> result = new ArrayList<>(3);
        Point strictRightNeighbour = new Point(x + 1, y);
        result.add(strictRightNeighbour);
        result.addAll(getStrictVerticalNeighbours(strictRightNeighbour));
        return result;
    }

    private List<Point> getLeftNeighbours() {
        if (isOnLeftEdge() )
            return Collections.emptyList();

        List<Point> result = new ArrayList<>(3);
        Point strictLeftNeigbour = new Point(x - 1, y);
        result.add(strictLeftNeigbour);
        result.addAll(getStrictVerticalNeighbours(strictLeftNeigbour));
        return result;
    }

    private boolean isOnRightEdge() {
        return x == 9;
    }

    private boolean isOnLeftEdge() {
        return x == 0;
    }

    private boolean isOnBottomEdge(Point p) {
        return p.y == 9;
    }

    private boolean isOnTopEdge(Point p) {
        return p.y == 0;
    }
}
