package aeadvent.code2021.day01.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day01 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        DepthProcessor depthProcessor = new DepthProcessor();
        Path path = Path.of(Day01.class.getClassLoader().getResource("inputA.txt").toURI());
        Files.lines(path)
                .map(Integer::parseInt)
                .forEach(depthProcessor::processDepth);

        System.out.println("Number of direct increments: " + depthProcessor.getNumberOfDirectDepthIncrements());
        System.out.println("Number of window increments: " + depthProcessor.getNumberOfWindowedIncrements(3));

    }
}
