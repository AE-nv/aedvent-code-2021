package aeadvent.code2021.day12.istvan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OnlyMultiVisitBigCavePath {
    protected List<Cave> caves;

    public OnlyMultiVisitBigCavePath(Cave start) {
        caves = List.of(start);
    }

    public OnlyMultiVisitBigCavePath(OnlyMultiVisitBigCavePath other, Cave c) {
        List<Cave> tempCaves = new ArrayList<>(other.caves);
        tempCaves.add(c);
        caves = Collections.unmodifiableList(tempCaves);
    }

    public boolean canAdd(Cave c) {
        return c.isBig() || !caves.contains(c);
    }

    public OnlyMultiVisitBigCavePath add(Cave c) {
        return new OnlyMultiVisitBigCavePath(this, c);

    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        caves.forEach(c -> builder.append(c.name()).append(','));
        return builder.substring(0, builder.length()-1);
    }

    public boolean isComplete() {
        return caves.get(caves.size()-1).name().equals("end");
    }
}
