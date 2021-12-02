package be.superjoran.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartTwo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<Integer> lines = Files.readAllLines(Paths.get(Objects.requireNonNull(PartTwo.class.getResource("input.txt")).toURI()))
                .stream().map(Integer::parseInt)
                .collect(Collectors.toList());

        Integer previousDepth = null;
        int increases = 0;
        for (int i = 2; i < lines.size(); i++) {
            Integer currentDepth = lines.get(i);
            currentDepth += lines.get(i -1);
            currentDepth += lines.get(i -2);
            System.out.println("increase? " + currentDepth + " " + (previousDepth != null && currentDepth > previousDepth));
            if (previousDepth != null && currentDepth > previousDepth) {
                increases++;
            }
            previousDepth = currentDepth;
        }
        System.out.println(increases);
    }
}
