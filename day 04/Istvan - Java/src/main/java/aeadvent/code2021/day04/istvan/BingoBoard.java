package aeadvent.code2021.day04.istvan;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BingoBoard {

    private final List<RowOrColumn> rows = new ArrayList<>(5);
    private final List<RowOrColumn> columns = new ArrayList<>(5);

    public BingoBoard(String boardLayout) {
        buildBoard(boardLayout);
    }

    private void buildBoard(String boardLayout) {
        buildRows(boardLayout);
        buildColumns(boardLayout);
    }

    private void buildColumns(String boardLayout) {
        transposeRowsToColumns(
                getRows(boardLayout), initializeBuilderArray())
                .stream()
                .map(RowOrColumn::new)
                .forEach(columns::add);
    }

    private List<String> transposeRowsToColumns(String[] rowStrings, StringBuilder[] columnBuilders) {
        for (String rowString : rowStrings) {
            String[] columnValues = splitRowInColumnValues(rowString);
            IntStream.range(0, columnValues.length)
                    .forEach(i -> columnBuilders[i].append(" ").append(columnValues[i]));
        }

        return Arrays.stream(columnBuilders)
                .map(StringBuilder::toString)
                .toList();
    }

    private static String[] splitRowInColumnValues(String rowString) {
        return rowString.trim().split("\\s+");
    }

    private String[] getRows(String boardLayout) {
        return boardLayout.split("\n");
    }

    private StringBuilder[] initializeBuilderArray() {
        StringBuilder[] columnBuilders = new StringBuilder[5];
        for (int i = 0; i < 5; i++)
            columnBuilders[i] = new StringBuilder();
        return columnBuilders;
    }

    private void buildRows(String boardLayout) {
        Arrays.asList(getRows(boardLayout)).forEach(s -> rows.add(new RowOrColumn(s)));
    }

    public boolean mark(final String drawnValue) {
        return rows.stream().anyMatch(r -> r.mark(drawnValue))
                &&
                columns.stream().anyMatch(r -> r.mark(drawnValue));
    }

    public boolean isBingo() {
        return rows.stream().anyMatch(RowOrColumn::isBingo)
                ||
                columns.stream().anyMatch(RowOrColumn::isBingo);

    }

    public Collection<String> getUnmarkedValue() {
        return columns.stream()
                .flatMap(c -> c.unmarkedValues.stream())
                .collect(Collectors.toSet());
    }


    private static class RowOrColumn {
        private final Set<String> unmarkedValues;

        RowOrColumn(String rowOrColumnLayout) {
            unmarkedValues = Arrays.stream(splitRowInColumnValues(rowOrColumnLayout)).collect(Collectors.toSet());
        }

        boolean mark(String drawnValue) {
            return unmarkedValues.remove(drawnValue);
        }

        boolean isBingo() {
            return unmarkedValues.isEmpty();
        }


    }
}
