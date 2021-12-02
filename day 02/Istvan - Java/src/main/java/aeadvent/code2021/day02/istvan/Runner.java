package aeadvent.code2021.day02.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Runner {

    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println(partA("inputA.txt"));

    }

    public static long partA(String filename) throws URISyntaxException, IOException {
        SubmarinePosition position = new SubmarinePosition();
        Path inputFile = Path.of(Runner.class.getClassLoader().getResource(filename).toURI());
        Files.lines(inputFile)
                .map(l -> MoveInstruction.fromString(l))
                .forEach(m -> position.move(m));
        System.out.println(position.toString());
        return position.getDepth() * position.getHorizontal();
    }
}
