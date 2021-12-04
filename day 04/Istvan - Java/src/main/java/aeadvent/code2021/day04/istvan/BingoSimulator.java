package aeadvent.code2021.day04.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BingoSimulator {
    private List<String> valuesToDraw;
    private List<BingoBoard> boards = new LinkedList<>();

    private BoardBuilder builder;

    public BingoSimulator(String inputFileName) {
        try {
            Files.lines(Path.of(this.getClass().getClassLoader().getResource(inputFileName).toURI()))
                    .forEach(this::processLine);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Could not find file with name " + inputFileName + " on classpath");
        } catch (IOException e) {
            throw new RuntimeException("Could not read line from file");
        }

    }

    private void processLine(String line) {
        if (line.isBlank())
            return;

        if (line.contains(","))
            buildValuesToDraw(line);
        else
            addToBoard(line);

    }

    private void addToBoard(String line) {
        if (builder == null)
            builder = new BoardBuilder();

        builder.addLine(line);

        if (builder.isComplete())
            boards.add(builder.buildBoard());
    }

    private void buildValuesToDraw(String line) {
        if (valuesToDraw != null)
            throw new IllegalStateException("Values to draw has already been constructed");

        valuesToDraw = Arrays.asList(line.split(",")).stream().map(String::trim).toList();

    }

    List<String> getNumberToDraw() {
        return valuesToDraw;
    }

    int getBoardCount() {
        return boards.size();
    }

    public long getScoreForFirstWinningBoard() {
        for (String value : valuesToDraw) {
            for (BingoBoard board : boards) {
                board.mark(value);
                if (board.isBingo())
                    return board.getUnmarkedValue().stream()
                            .map(Long::valueOf)
                            .reduce(Long::sum)
                            .map(s -> s * Long.valueOf(value))
                            .orElseThrow(() -> new RuntimeException("Expected to be able to calculate value"));
            }
        }
        throw new IllegalStateException("Nobody won the game!");
    }


    public long getScoreForLastWinningBoard() {
        String lastNumberDrawnCausingAWin = null;
        BingoBoard lastWinningBoard = null;
        List<BingoBoard> boardsToCheck = new ArrayList<>(boards);

        for (String value : valuesToDraw) {
            List<BingoBoard> remainingBoards = new ArrayList<>(boardsToCheck.size());
            for (BingoBoard board : boardsToCheck) {
                board.mark(value);
                if (board.isBingo()) {
                    lastWinningBoard = board;
                    lastNumberDrawnCausingAWin = value;
                } else
                    remainingBoards.add(board);

            }
            boardsToCheck = remainingBoards;
        }

        final String lastWinNumber = lastNumberDrawnCausingAWin;
        return lastWinningBoard.getUnmarkedValue().stream()
                .map(Long::valueOf)
                .reduce(Long::sum)
                .map(s -> s * Long.valueOf(lastWinNumber))
                .orElseThrow(() -> new RuntimeException("Expected to be able to calculate value"));
    }

    private class BoardBuilder {

        private int lineCounter = 0;
        private StringBuilder builder = new StringBuilder();

        void addLine(String line) {
            if (isComplete()) {
                throw new IllegalStateException("Already received 5 lines for this board!");
            }
            lineCounter++;
            builder.append(line).append("\n");
        }

        boolean isComplete() {
            return lineCounter == 5;
        }

        BingoBoard buildBoard() {
            if (!isComplete())
                throw new IllegalStateException("Board is not yet complete");

            return new BingoBoard(builder.toString());
        }
    }
}
