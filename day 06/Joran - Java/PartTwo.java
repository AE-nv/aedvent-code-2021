package be.superjoran.day6;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PartTwo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        processFile("sample.txt");
        processFile("input.txt");
    }

    public static void processFile(String fileName) throws URISyntaxException, IOException {
        Scanner scanner = new Scanner(Paths.get(Objects.requireNonNull(PartTwo.class.getResource(fileName)).toURI()));
        List<Integer> fishes = Arrays.stream(scanner.nextLine().split(","))
                .map(Integer::valueOf)
                .toList();

        Map<Integer, BigDecimal> numberOfFishesPerAge = new HashMap<>();

        for (Integer fish : fishes) {
            numberOfFishesPerAge.merge(fish, BigDecimal.ONE, BigDecimal::add);
        }

        for (int i = 0; i < 256; i++) {
            Map<Integer, BigDecimal> newNumberOfFishesPerAge = new HashMap<>();
            numberOfFishesPerAge.forEach((key, value) -> {
                if (key == 0) {
                    newNumberOfFishesPerAge.merge(8, value, BigDecimal::add);
                    newNumberOfFishesPerAge.merge(6, value, BigDecimal::add);
                } else {
                    newNumberOfFishesPerAge.merge(key - 1, value, BigDecimal::add);
                }

            });
            numberOfFishesPerAge = newNumberOfFishesPerAge;
        }

        BigDecimal total = numberOfFishesPerAge.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(total);
    }

}
