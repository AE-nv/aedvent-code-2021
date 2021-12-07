package be.superjoran.day6;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class PartOne {

    public static void main(String[] args) throws IOException, URISyntaxException {
        processFile("sample.txt");
        processFile("input.txt");
    }

    public static void processFile(String fileName) throws URISyntaxException, IOException {
        Scanner scanner = new Scanner(Paths.get(Objects.requireNonNull(PartOne.class.getResource(fileName)).toURI()));
        List<LanternFish> fishes = Arrays.stream(scanner.nextLine().split(","))
                .map(Integer::parseInt)
                .map(LanternFish::new)
                .collect(Collectors.toList());

        for (int i = 0; i < 80; i++) {
            List<LanternFish> newFishes = new ArrayList<>();
            for (LanternFish fish : fishes) {
                if (fish.growOlder()) {
                    newFishes.add(new LanternFish(6));
                }
            }
            fishes.addAll(newFishes);
        }

        System.out.println(fishes.size());

    }

    public static class LanternFish {
        int days;

        public LanternFish(int days) {
            this.days = days;
        }

        public boolean growOlder() {
            if (days == 0) {
                days = 8;
                return true;
            } else {
                days--;
                return false;
            }
        }
    }

}
