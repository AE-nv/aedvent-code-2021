package be.superjoran.day2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartOne {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<Input> lines = Files.readAllLines(Paths.get(Objects.requireNonNull(PartOne.class.getResource("input.txt")).toURI()))
                .stream()
                .map(s -> {
                    String[] split = s.split(" ");
                    return new Input(Direction.valueOf(split[0]), Integer.parseInt(split[1]));
                })
                .collect(Collectors.toList());

        int depth = 0;
        int horizontal = 0;
        for (Input line : lines) {
            switch (line.direction) {
                case up -> depth -= line.units;
                case down -> depth += line.units;
                case forward -> horizontal += line.units;
            }
        }
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
