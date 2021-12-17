package aeadvent.code2021.day17.istvan;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Day17 {
    public static void main(String[] args) {
        System.out.println("Part A: " + partA());
        System.out.println("Part B: " + partB());
    }

    private static long partB() {
        Area target = new Area(230,283,-107,-57);
        Point start = new Point(0,0);
        return generatePotentialStartVelocities(283, -57)
                .map(v -> new Probe(start, v))
                .map(p -> p.getHighestPointWhenReachingTarget(target))
                .filter(Optional::isPresent)
                .count();
    }

    private static int partA() {
        Area target = new Area(230,283,-107,-57);
        Point start = new Point(0,0);
        return generatePotentialStartVelocities(283, -57)
                .map(v -> new Probe(start, v))
                .map(p -> p.getHighestPointWhenReachingTarget(target))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Point::y)
                .max(Integer::compareTo)
                .get();
    }

    private static Stream<Velocity> generatePotentialStartVelocities(int farHorizontalEdge, int bottemEdge) {
        List<Velocity> velocities = new LinkedList<>();
        int horizontalStep = farHorizontalEdge > 0? -1:1;
        for (int i = farHorizontalEdge; i != 0; i+=horizontalStep) {
            for (int j = -Math.abs(bottemEdge*2); j < Math.abs(bottemEdge*2); j++) {
                velocities.add(new Velocity(i,j));
            }
        }
        return velocities.stream();
    }
}
