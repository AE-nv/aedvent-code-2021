package aeadvent.code2021.day17.istvan;

public record Area(int x1, int x2, int y1, int y2) {
    public boolean isInArea(Point point) {
        return ! (point.x() < x1 && point.x() < x2)
                && ! (point.x() > x1 && point.x() > x2)
                && ! (point.y() > y1 && point.y() > y2)
                && ! (point.y() < y1 && point.y() < y2);
    }
}
