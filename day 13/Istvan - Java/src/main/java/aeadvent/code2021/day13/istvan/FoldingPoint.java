package aeadvent.code2021.day13.istvan;

public record FoldingPoint(int x, int y) {

    public FoldingPoint foldUp(int foldLine) {
        if (isAboveFold(foldLine))
            return this;

        return new FoldingPoint(x, foldLine-(y-foldLine));
    }

    private boolean isAboveFold(int foldLine) {
        return foldLine > y;
    }

    public FoldingPoint foldLeft(int foldLine) {
        if (isLeftOfFold(foldLine))
            return this;

        return new FoldingPoint(foldLine - (x-foldLine), y);
    }

    private boolean isLeftOfFold(int foldLine) {
        return x < foldLine;
    }
}
