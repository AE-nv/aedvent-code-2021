package be.superjoran.day4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class PartOne {

    public static void main(String[] args) throws IOException, URISyntaxException {
        processFile("sample.txt");
        processFile("input.txt");
    }

    public static void processFile(String fileName) throws URISyntaxException, IOException {
        Scanner scanner = new Scanner(Paths.get(Objects.requireNonNull(PartOne.class.getResource(fileName)).toURI()));

        String bingo = scanner.nextLine();
        System.out.println(bingo);

        List<bord> boards = new ArrayList<>();

        do {
            int[][] numbers = new int[5][5];

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    int number = scanner.nextInt();
                    numbers[i][j] = number;
                }
            }
            boards.add(new bord(numbers));
        } while (scanner.hasNext());

        System.out.println("Number of boards: " + boards.size());

        List<Integer> bingoNumbers = Arrays.stream(bingo.split(",")).map(Integer::parseInt).toList();

        System.out.println("Number of bingoNumbers: " + bingoNumbers.size());

        for (Integer bingoNumber : bingoNumbers) {
            Optional<Integer> wonBoard = boards.stream().map(b -> b.pickNumber(bingoNumber))
                    .filter(Objects::nonNull)
                    .findFirst();

            if (wonBoard.isPresent()) {
                System.out.println("Board has won, score: " + wonBoard.get());
                return;
            }
        }

    }

    private static class bord {
        Vak[][] numbers = new Vak[5][5];

        public bord(int[][] numbers) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    this.numbers[i][j] = new Vak(numbers[i][j]);
                }
            }
        }

        public Integer pickNumber(int number) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (this.numbers[i][j].number == number) {
                        this.numbers[i][j].pick();
                    }
                }
            }
            if (this.hasWon()) {
                int sumUnmarked = 0;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (!this.numbers[i][j].hasBeenPicked) {
                            sumUnmarked += this.numbers[i][j].number;
                        }
                    }
                }
                return sumUnmarked * number;
            }

            return null;
        }

        public boolean hasWon() {
            for (int i = 0; i < 5; i++) {
                boolean rowIsComplete = true;
                boolean columnIsComplete = true;
                for (int j = 0; j < 5; j++) {
                    if (!this.numbers[i][j].hasBeenPicked) {
                        rowIsComplete = false;
                    }
                    if (!this.numbers[j][i].hasBeenPicked) {
                        columnIsComplete = false;
                    }
                }
                if (rowIsComplete || columnIsComplete) {
                    return true;
                }
            }
            return false;
        }
    }

    private static class Vak {
        int number;
        boolean hasBeenPicked;

        public Vak(int number) {
            this.number = number;
        }

        public void pick() {
            this.hasBeenPicked = true;
        }
    }

}
