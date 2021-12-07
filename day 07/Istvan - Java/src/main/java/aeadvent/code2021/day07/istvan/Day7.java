package aeadvent.code2021.day07.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Day7 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Integer> positions = readPositions("input.txt");
        System.out.println("Part A: " + calculateFuelCostToMoveToOptimalPosition(positions, Day7::calculateConstantFuelCostToMoveTo));
        System.out.println("Part B: " + calculateFuelCostToMoveToOptimalPosition(positions, Day7::calculateIncrementalFuelCostToMoveTo));
    }


    static List<Integer> readPositions(final String filename) throws URISyntaxException, IOException {
        return Files.lines(Path.of(Objects.requireNonNull(Day7.class.getClassLoader().getResource(filename)).toURI()))
                .flatMap(l -> Arrays.stream(l.split(",")))
                .map(Integer::parseInt)
                .sorted()
                .toList();
    }

    public static long calculateFuelCostToMoveToOptimalPosition(List<Integer> orderedPositions, FuelCostCalculator fuelCostCalculator) {
        return IntStream.rangeClosed(orderedPositions.get(0), orderedPositions.get(orderedPositions.size()-1))
                .boxed()
                .map(Integer::longValue)
                .map(i -> fuelCostCalculator.calculateTotalFuelCostToMoveTo(i, orderedPositions))
                .reduce(Long::min)
                .get();
    }

    static long calculateConstantFuelCostToMoveTo(long targetPosition, List<Integer> positions) {
        return positions.stream()
                .map(Integer::longValue)
                .map(p -> Math.abs(p-targetPosition))
                .reduce(Long::sum)
                .get();
    }

    static long calculateIncrementalFuelCostToMoveTo(long targetPosition, List<Integer> positions) {
        return positions.stream()
                .map(Integer::longValue)
                .map(p -> Math.abs(p-targetPosition))
                .map(d -> d*(d+1)/2) //Faulhaber formula
                .reduce(Long::sum)
                .get();
    }


    @FunctionalInterface
    interface FuelCostCalculator {
        long calculateTotalFuelCostToMoveTo(long targetPosition, List<Integer> orderedPositions);
    }



}
