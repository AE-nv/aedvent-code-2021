package be.superjoran.day4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PartTwo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        processFile("sample.txt");
        processFile("input.txt");
    }

    public static void processFile(String fileName) throws URISyntaxException, IOException {
        Scanner scanner = new Scanner(Paths.get(Objects.requireNonNull(PartTwo.class.getResource(fileName)).toURI()));

        String bingo = scanner.nextLine();
        System.out.println(bingo);

        List<Board> boards = new ArrayList<>();

        do {
            int[][] numbers = new int[5][5];

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    int number = scanner.nextInt();
                    numbers[i][j] = number;
                }
            }
            boards.add(new Board(numbers));
        } while (scanner.hasNext());

        System.out.println("Number of boards: " + boards.size());

        List<Integer> bingoNumbers = Arrays.stream(bingo.split(",")).map(Integer::parseInt).toList();

        System.out.println("Number of bingoNumbers: " + bingoNumbers.size());

        for (Integer bingoNumber : bingoNumbers) {
            List<Board> wonBoards = new ArrayList<>();
            for (Board board : boards) {
                Integer result = board.pickNumber(bingoNumber);
                if (result != null) {
                    wonBoards.add(board);
                    System.out.println("Board has won, score: " + result);
                }
            }
            for (Board wonBoard : wonBoards) {
                boards.remove(wonBoard);
            }
        }

    }

    private static class Board {
        Field[][] numbers = new Field[5][5];

        public Board(int[][] numbers) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    this.numbers[i][j] = new Field(numbers[i][j]);
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

    private static class Field {
        int number;
        boolean hasBeenPicked;

        public Field(int number) {
            this.number = number;
        }

        public void pick() {
            this.hasBeenPicked = true;
        }
    }

}
