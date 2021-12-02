package aeadvent.code2021.day02.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Runner {

    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println(partA("input.txt"));
        System.out.println(partB("input.txt"));

    }

    public static long partA(String filename) throws URISyntaxException, IOException {
        Submarine position = new Submarine();
        moveSubmarine(filename, position);
        return position.getDepth() * position.getHorizontal();
    }

    private static void moveSubmarine(String filename, Submarine submarine) throws URISyntaxException, IOException {
        Path inputFile = Path.of(Objects.requireNonNull(Runner.class.getClassLoader().getResource(filename)).toURI());
        Files.lines(inputFile)
                .map(MoveInstruction::fromString)
                .forEach(submarine::move);
    }

    public static long partB(String filename) throws IOException, URISyntaxException {
        AimedSubmarine position = new AimedSubmarine();
        moveSubmarine(filename, position);
        return position.getDepth() * position.getHorizontal();
    }
}
