package aeadvent.code2021.day14.istvan;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day14 {

    Map<String, Long> pairCount;
    final Map<String, String> rules;

    public Day14(String startPolymer, Map<String, String> rules) {
        pairCount = buildStartPairCount(startPolymer);
        this.rules = rules;
    }

    private Map<String, Long> buildStartPairCount(String startPolymer) {
        return IntStream.range(0, startPolymer.length() - 1)
                .boxed()
                .collect(Collectors.toMap(i -> "" + startPolymer.charAt(i) + startPolymer.charAt(i + 1), i -> 1L, Long::sum));
    }


    public static Day14 fromFile(String s) {
        String polymer;
        try (Stream<String> lines = Files.lines(Path.of(Objects.requireNonNull(Day14.class.getClassLoader().getResource(s)).toURI()))) {
            polymer = lines.findFirst().get();
        } catch (Exception e) {
            throw new RuntimeException("Could not get input from file " + s, e);
        }

        Map<String, String> rules;
        try (Stream<String> lines = Files.lines(Path.of(Objects.requireNonNull(Day14.class.getClassLoader().getResource(s)).toURI()))) {
            rules = lines.dropWhile(String::isBlank)
                    .skip(2)
                    .map(l -> l.split(" -> "))
                    .collect(Collectors.toMap(a -> a[0], a -> a[1]));
        } catch (Exception e) {
            throw new RuntimeException("Could not get input from file " + s, e);
        }

        return new Day14(polymer, rules);

    }

    public void applyStep() {

        var tempPairCount = new HashMap<String, Long>();

        pairCount.forEach(
                (k, v) -> {
                    var toAdd = rules.get(k);
                    tempPairCount.merge(k.charAt(0) + toAdd, v, Long::sum);
                    tempPairCount.merge(toAdd + k.charAt(1), v, Long::sum);
                }
        );

        pairCount = tempPairCount;
    }

    public static void main(String[] args) {
        System.out.println("Part A: " + partA("input.txt"));
        System.out.println("Part B: " + partB("input.txt"));
    }

    static long partB(String filename) {
        return diffBetweenMaxAndMinAfterSteps(filename, 40);
    }

    static long partA(String filename) {
        return diffBetweenMaxAndMinAfterSteps(filename, 10);

    }

    static long diffBetweenMaxAndMinAfterSteps(String filename, int numberOfSteps) {
        Day14 d = Day14.fromFile(filename);
        IntStream.range(0, numberOfSteps).forEach(i -> d.applyStep());

        Map<Character, Long> counts = new HashMap<>();
        d.pairCount.forEach((key, value) -> {
            counts.merge(key.charAt(0), value, Long::sum);
            counts.merge(key.charAt(1), value, Long::sum);
        });

        long min = Math.round(counts.values().stream().min(Long::compareTo).orElse(0L)/2.0);
        long max = Math.round(counts.values().stream().max(Long::compareTo).orElse(Long.MAX_VALUE)/2.0);

        return max - min;
    }
}
