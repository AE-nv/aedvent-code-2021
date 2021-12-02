package aeadvent.code2021.day01.istvan;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class DepthProcessor {


    private List<Integer> registeredDepths = new LinkedList<>();

    public void processDepth(int depth) {
        registeredDepths.add(depth);
    }

    public int getNumberOfDirectDepthIncrements() {
        return getNumberOfWindowedIncrements(1);
    }

    public int getNumberOfWindowedIncrements(int windowSize) {
        if (!isSufficientDataAvailableForWindowSize(windowSize))
            return 0;
        return calculateWindowedIncrements(windowSize);
    }

    private boolean isSufficientDataAvailableForWindowSize(int windowSize) {
        return registeredDepths.size() > windowSize;
    }

    private int calculateWindowedIncrements(int windowSize) {
        return IntStream.range(0, registeredDepths.size() - windowSize)
                .map(i -> isWindowedIncrement(i, windowSize) ? 1 : 0).sum();
    }

    private boolean isWindowedIncrement(final int i, final int windowSize) {
        return getSumForWindowStartingAt(i + 1, windowSize) > getSumForWindowStartingAt(i, windowSize);
    }

    private int getSumForWindowStartingAt(final int windowStart, final int windowSize) {
        Integer reduce = registeredDepths.subList(windowStart, windowStart+windowSize)
                .stream()
                .reduce(0, Integer::sum);
        return reduce;
    }
}
