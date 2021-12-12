package aeadvent.code2021.day12.istvan;

public class SingleSmallCaveDoubleVisitPath extends OnlyMultiVisitBigCavePath {

    private final boolean smallCaveRevisited;

    public SingleSmallCaveDoubleVisitPath(Cave start) {
        super(start);
        smallCaveRevisited = false;
    }

    public SingleSmallCaveDoubleVisitPath(SingleSmallCaveDoubleVisitPath other, Cave c) {
        super(other, c);
        this.smallCaveRevisited = other.smallCaveRevisited || (!c.isBig() && other.caves.contains(c));
    }

    @Override
    public boolean canAdd(Cave c) {
        return super.canAdd(c) || (!smallCaveRevisited && (!c.isStart() && !c.isEnd()));
    }

    @Override
    public OnlyMultiVisitBigCavePath add(Cave c) {
        return new SingleSmallCaveDoubleVisitPath(this, c);
    }
}
