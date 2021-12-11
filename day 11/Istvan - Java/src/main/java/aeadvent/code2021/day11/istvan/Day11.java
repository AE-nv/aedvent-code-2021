package aeadvent.code2021.day11.istvan;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class Day11 {

    private final Map<Point, DumboOctopus> octopuses = new HashMap<>();
    private int flashCount = 0;

    Day11(String filename) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getClassLoader().getResource(filename)).toURI()));
        } catch (Exception e) {
            throw new RuntimeException("Could not read " + filename);
        }

        for (int i = 0; i < lines.size(); i++) {
            char[] energyLevels = lines.get(i).toCharArray();
            for (int j = 0; j < energyLevels.length; j++) {
                Point p = new Point(j, i);
                octopuses.put(p, new DumboOctopus(p, Integer.parseInt(String.valueOf(energyLevels[j]))));
            }
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                builder.append(octopuses.get(new Point(x, y)).getEnergyLevel());
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public void playStep() {
        Queue<DumboOctopus> octopusesToAbsorbFlash = new LinkedList<>();

        octopuses.values().stream()
                .filter(DumboOctopus::accumulateEnergy)
                .forEach(o -> handleFlashTriggered(octopusesToAbsorbFlash, o));

        while (!octopusesToAbsorbFlash.isEmpty()) {
            DumboOctopus dumboOctopus = octopusesToAbsorbFlash.remove();
            if (dumboOctopus.absorbFlash()) {
                handleFlashTriggered(octopusesToAbsorbFlash, dumboOctopus);
            }
        }
    }

    private void handleFlashTriggered(Queue<DumboOctopus> octopusesToAbsorbFlash, DumboOctopus o) {
        o.getPosition().getAdjacentPoints().forEach(p -> octopusesToAbsorbFlash.offer(octopuses.get(p)));
        flashCount++;
    }

    public int getFlashCount() {
        return this.flashCount;
    }


    public int findSynchronousFlashStep() {
        int stepCounter = 0;
        while (!hasSyncFlashed()) {
            stepCounter++;
            playStep();
        }
        return stepCounter;
    }

    boolean hasSyncFlashed() {
        return toString().equals("""
                0000000000
                0000000000
                0000000000
                0000000000
                0000000000
                0000000000
                0000000000
                0000000000
                0000000000
                0000000000
                """);
    }

    public static void main(String[] args) {
        partA();
        partB();
    }

    private static void partB() {
        Day11 d = new Day11("input.txt");
        System.out.println("Part B: " + d.findSynchronousFlashStep());
    }

    private static void partA() {
        Day11 d = new Day11("input.txt");
        for (int i = 0; i < 100; i++)
            d.playStep();

        System.out.println("Part A:" + d.getFlashCount());
    }
}
