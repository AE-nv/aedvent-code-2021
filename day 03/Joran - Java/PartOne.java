package be.superjoran.day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartOne {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(Objects.requireNonNull(PartOne.class.getResource("input.txt")).toURI()));

        int numberOfLines = lines.size();

        String gammaRate = "";
        String epsilonRate = "";
        for (int i = 0; i < lines.get(0).length(); i++ ) {
            final int j = i;
            long numberOfZeros = lines.stream()
                    .map(s -> s.charAt(j))
                    .filter(c -> c == '0')
                    .count();

            if (numberOfZeros > (numberOfLines / 2)) {
                gammaRate += "0";
                epsilonRate += "1";
            } else {
                gammaRate += "1";
                epsilonRate += "0";
            }
        }

        System.out.printf("GammaRate = %s, epsilonRate = %s%n", gammaRate, epsilonRate);
        System.out.println(Integer.parseInt(gammaRate, 2) * Integer.parseInt(epsilonRate, 2));

    }
}
