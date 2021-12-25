package aeadvent.code2021.day25.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;

public class Day25 {

    private final int width;
    private final int height;

    protected Set<Position> eastHerd;
    protected Set<Position> southHerd;


    public Day25(String filename) {
        List<String> lines;
        try{
            lines = Files.readAllLines(Path.of(Objects.requireNonNull(this.getClass().getClassLoader().getResource(filename)).toURI()));
        } catch (Exception e) {
            throw new RuntimeException("Could not read from "+ filename);
        }

        eastHerd = new HashSet<>();
        southHerd = new HashSet<>();

        this.height = lines.size();
        this.width = lines.get(0).length();

        for (int y = 0; y < this.height; y++) {
            char[] line = lines.get(y).toCharArray();
            for (int x = 0; x < this.width; x++) {
                if (line[x] == '>')
                    eastHerd.add(new Position(x,y));
                if (line[x] == 'v')
                    southHerd.add(new Position(x,y));
            }
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean move() {
        boolean eastHerdMoved = moveEast();
        boolean southHerdMoved =moveSouth();
        return eastHerdMoved || southHerdMoved;
    }

    private boolean moveEast() {
        return doMove(eastHerd, p -> new Position((p.x()+1)%this.width,p.y()));
    }

    private boolean moveSouth() {
        return doMove(southHerd, p -> new Position(p.x(),(p.y()+1)%this.height));
    }

    private boolean doMove(Set<Position> herd, UnaryOperator<Position> nextPosCalculator) {
        boolean hasMoved = false;
        Set<Position> newHerd = new HashSet<>();
        for (Position pos : herd) {
            Position nextPos = nextPosCalculator.apply(pos);
            if (eastHerd.contains(nextPos) || southHerd.contains(nextPos))
                newHerd.add(pos);
            else {
                hasMoved = true;
                newHerd.add(nextPos);
            }
        }
        herd.clear();
        herd.addAll(newHerd);
        return hasMoved;
    }

    public static long partA() {
        Day25 d = new Day25("input.txt");
        long moves = 1;
        while (d.move())
            moves++;

        return moves;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(this.height*(this.width+1));
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                Position pos = new Position(x,y);
                if (eastHerd.contains(pos))
                    builder.append('>');
                else if (southHerd.contains(pos))
                    builder.append('v');
                else
                    builder.append('.');
            }
            builder.append("\n");
        }
        return builder.substring(0, builder.length()-1);
    }

    record Position(int x, int y) {}

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("Part A: " + partA());
        long afterPartA = System.currentTimeMillis();
        System.out.println("Part A took " + (afterPartA - start) + "ms");
    }
}
