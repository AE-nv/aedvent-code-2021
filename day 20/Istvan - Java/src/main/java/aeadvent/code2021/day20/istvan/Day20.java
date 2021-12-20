package aeadvent.code2021.day20.istvan;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day20 {
    public static List<Character> readEnhancementAlgorithm(String filename) {
        try(Stream<String> lines = Files.lines(Path.of(Objects.requireNonNull(Day20.class.getClassLoader().getResource(filename)).toURI()))) {
            return lines.limit(1)
                    .flatMap(s -> s.chars().mapToObj(i -> (char)i))
                    .map(c -> c == '.'?'0':'1')
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Could not read from fle " + filename, e);
        }
    }

    public static ScanImage readScanImage(String filename) {
        try(Stream<String> lines = Files.lines(Path.of(Objects.requireNonNull(Day20.class.getClassLoader().getResource(filename)).toURI()))) {
            return new ScanImage(lines.skip(2)
                    .map(l -> l.chars().mapToObj(i -> (char)i).map(c -> c=='.'?'0':'1').toList())
                    .toList());
        } catch (Exception e) {
            throw new RuntimeException("Could not read from file " + filename,e);
        }
    }

    public static long partA(String filename) {
        final var algorithm = readEnhancementAlgorithm(filename);
        final var initial = readScanImage(filename);

        var result = initial.enhanceRepeatedly(algorithm, 2);

        return result.getLightPixelCount();
    }

    public static long partB(String filename) {
        final var algorithm = readEnhancementAlgorithm(filename);
        final var initial = readScanImage(filename);

        var result = initial.enhanceRepeatedly(algorithm, 50);

        return result.getLightPixelCount();
    }

    public static void main(String[] args) {
        System.out.println("Part A: " + partA("input.txt"));
        System.out.println("Part B: " + partB("input.txt"));
    }
}
