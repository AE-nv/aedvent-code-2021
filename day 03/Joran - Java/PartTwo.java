package be.superjoran.day3;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PartTwo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(Objects.requireNonNull(PartOne.class.getResource("input.txt")).toURI()));

        List<String> possibleOxygenNumbers = new ArrayList<>(lines);
        int i = 0;
        do {
            final int ii = i;
            char ch;
            long numberOfZeros = possibleOxygenNumbers.stream()
                    .map(s -> s.charAt(ii))
                    .filter(c -> c == '0')
                    .count();
            if (numberOfZeros > (possibleOxygenNumbers.size() / 2)) {
                ch = '0';
            } else {
                ch = '1';
            }
            possibleOxygenNumbers = possibleOxygenNumbers.stream()
                    .filter(s -> s.charAt(ii) == ch)
                    .toList();
            i++;
        } while (possibleOxygenNumbers.size() > 1);
        String oxygenGeneratorRating = possibleOxygenNumbers.get(0);

        List<String> possibleCo2ScrubberNumbers = new ArrayList<>(lines);
        int j = 0;
        do {
            final int jj = j;
            char ch;
            long numberOfZeros = possibleCo2ScrubberNumbers.stream()
                    .map(s -> s.charAt(jj))
                    .filter(c -> c == '0')
                    .count();
            if (numberOfZeros > (possibleCo2ScrubberNumbers.size() / 2)) {
                ch = '1';
            } else {
                ch = '0';
            }
            possibleCo2ScrubberNumbers = possibleCo2ScrubberNumbers.stream()
                    .filter(s -> s.charAt(jj) == ch)
                    .toList();
            j++;
        } while (possibleCo2ScrubberNumbers.size() > 1);
        String co2ScrubberRating = possibleCo2ScrubberNumbers.get(0);

        System.out.printf("oxygen = %s or %d, co2 = %s or %d %n", oxygenGeneratorRating, Integer.parseInt(oxygenGeneratorRating, 2), co2ScrubberRating, Integer.parseInt(co2ScrubberRating, 2));
        System.out.println(Integer.parseInt(oxygenGeneratorRating, 2) * Integer.parseInt(co2ScrubberRating, 2));
    }
}
