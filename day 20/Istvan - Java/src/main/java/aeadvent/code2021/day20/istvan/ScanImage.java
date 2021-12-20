package aeadvent.code2021.day20.istvan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScanImage {
    final int width;
    final int height;

    final List<List<Character>> grid;

    ScanImage(List<List<Character>> init) {
        height = init.size();
        width = init.get(0).size();
        grid = init;

    }

    public long getLightPixelCount() {
        return grid.stream()
                .flatMap(Collection::stream)
                .filter(c -> c.equals('1'))
                .count();
    }

    public ScanImage enhanceRepeatedly(List<Character> algorithm, int times) {
        ScanImage result = this;
        for (int i = 1; i <= times; i++) {
            result = result.enhance(algorithm, i);
        }
        return result;
    }
    public ScanImage enhance(List<Character> enhancementAlgorithm, int iteration) {
        List<List<Character>> newMatrix = new ArrayList<>(height+2);
        for (int y = 0; y < height+2; y++) {
            var row = new ArrayList<Character>(width+2);
            newMatrix.add(row);
            for (int x = 0; x < width+2; x++) {
                row.add(enhancePixelAt(x,y, enhancementAlgorithm, iteration));
            }
        }
        return new ScanImage(newMatrix);
    }

    private Character enhancePixelAt(int x, int y, List<Character> enhancementAlgo, int iteration) {
        StringBuilder builder = new StringBuilder(9);
        getPixelsToConsider(x,y).stream()
                .map(p -> p.x() < 1 || p.x() > width ? getPixelForIteration(iteration):
                        p.y() < 1 || p.y() > height? getPixelForIteration(iteration): grid.get(p.y()-1).get(p.x()-1))
                .forEach(builder::append);

        return enhancementAlgo.get(Integer.parseInt(builder.toString(),2));
    }

    private Character getPixelForIteration(int iteration) {
        return iteration%2 == 0? '1':'0';
    }

    private List<Pixel> getPixelsToConsider(int x, int y) {
        return List.of(
          new Pixel(x-1, y-1),
                new Pixel(x, y-1),
                new Pixel(x+1, y-1),
                new Pixel(x-1, y),
                new Pixel(x, y),
                new Pixel(x+1, y),
                new Pixel(x-1, y+1),
                new Pixel(x, y+1),
                new Pixel(x+1, y+1)
        );
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        grid.forEach(r -> {
            r.forEach(builder::append);
            builder.append("\n");
        });
        return builder.toString();
    }

    private record Pixel (int x, int y) {}
}
