package aeadvent.code2021.day09.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;

public class Day9 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        CaveMap map = CaveMap.fromFile("input.txt");
        System.out.println("Part A :" + map.calculateRiskLevel());
        System.out.println("Part B :" + partB(map));
    }

    static long partB(CaveMap map) {
        List<Integer> basinSizes = map.getBasins();
        basinSizes.sort(Comparator.reverseOrder());
        return basinSizes.get(0)* basinSizes.get(1)* basinSizes.get(2);

    }
}
