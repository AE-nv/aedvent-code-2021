package aeadvent.code2021.day04.istvan;

public class Day4 {

    public static void main(String[] args) {
        partA();
        partB();
    }

    static void partA() {
        BingoSimulator simulator = new BingoSimulator("input.txt");
        System.out.println("Part A: "+ simulator.getScoreForFirstWinningBoard());
    }

    static void partB() {
        BingoSimulator simulator = new BingoSimulator("input.txt");
        System.out.println("Part B: "+ simulator.getScoreForLastWinningBoard());
    }
}
