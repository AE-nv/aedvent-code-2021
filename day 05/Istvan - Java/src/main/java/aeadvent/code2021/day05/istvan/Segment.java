package aeadvent.code2021.day05.istvan;

public record Segment(Point start, Point end) {
    public boolean isHorizontal() {
        return start.y() == end.y();
    }

    public boolean isVertical() {
        return start.x() == end.x();
    }
}
