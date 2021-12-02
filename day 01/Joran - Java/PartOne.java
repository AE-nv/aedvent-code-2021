package be.superjoran.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class PartOne {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(Objects.requireNonNull(PartOne.class.getResource("input.txt")).toURI()));

        Integer previousDepth = null;
        int increases = 0;
        for (String line : lines) {
            int currentDepth = Integer.parseInt(line);
            if (previousDepth != null && currentDepth > previousDepth) {
                increases++;
            }
            previousDepth = currentDepth;
        }
        System.out.println(increases);
    }
}
