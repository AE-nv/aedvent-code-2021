package aeadvent.code2021.day06.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day6 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("Part A: " + simulateOverDays(80));
        System.out.println("Part B: " + simulateOverDays(256));
    }

    private static long simulateOverDays(int numberOfDays) throws IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Path.of(Objects.requireNonNull(Day6.class.getClassLoader().getResource("input.txt")).toURI()));
        List<Integer> initialFishAges = Arrays.stream(lines.get(0).split(","))
                .map(Integer::parseInt)
                .toList();
        LanternFishSchool lanternFishSchool = LanternFishSchool.fromInitialAgeList(initialFishAges);
        lanternFishSchool.simulateEvolutionOverDays(numberOfDays);
        return lanternFishSchool.getNumberOfFish();
    }
}
