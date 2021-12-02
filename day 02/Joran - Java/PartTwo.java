package be.superjoran.day2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartTwo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<Input> lines = Files.readAllLines(Paths.get(Objects.requireNonNull(PartTwo.class.getResource("input.txt")).toURI()))
                .stream()
                .map(s -> {
                    String[] split = s.split(" ");
                    return new Input(Direction.valueOf(split[0]), Integer.parseInt(split[1]));
                })
                .collect(Collectors.toList());

        int aim = 0;
        int horizontal = 0;
        int depth = 0;
        for (Input line : lines) {
            switch (line.direction) {
                case up -> aim -= line.units;
                case down -> aim += line.units;
                case forward -> {
                    depth += aim * line.units;
                    horizontal += line.units;
                }
            }
        }
        System.out.printf("aim: %d, horizontal: %d, depth: %d%n", aim, horizontal, depth);
        System.out.println(depth * horizontal);
    }

    public static class Input {
        Direction direction;
        int units;

        public Input(Direction direction, int units) {
            this.direction = direction;
            this.units = units;
        }
    }

    public enum Direction {
        forward,
        down,
        up
    }
}
