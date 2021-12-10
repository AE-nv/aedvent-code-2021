package aeadvent.code2021.day10.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;

public class Day10 {
    public static final Map<Character, Character> CHARACTER_PAIRS = Map.of('(', ')', '[', ']', '{', '}', '<', '>');
    private static final Map<Character, Integer> ILLEGAL_CLOSING_CHAR_SCORES = Map.of(')',3,']',57,'}',1197,'>',25137);
    private static final Map<Character, Integer> COMPLETION_CLOSING_CHAR_SCORES = Map.of(')',1,']',2,'}',3,'>',4);

    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("Part A: " + partA("input.txt"));
        System.out.println("Part B: " + partB("input.txt"));
    }

    static Long partA(String filename) throws URISyntaxException, IOException {
        return Files.lines(Path.of(Objects.requireNonNull(Day10.class.getClassLoader().getResource(filename)).toURI()))
                .map(Day10::scoreLine)
                .map(Integer::longValue)
                .reduce(0L,Long::sum);
    }

    static Long partB(String filename) throws URISyntaxException, IOException {
        List<Long> completionScores =  Files.lines(Path.of(Objects.requireNonNull(Day10.class.getClassLoader().getResource(filename)).toURI()))
                .filter(Day10::isLegalLine)
                .map(Day10::scoreLineCompletion)
                .sorted()
                .toList();
        return completionScores.get(completionScores.size()/2);
    }

    private static boolean isLegalLine(String l) {
        return Day10.findIllegalClosingCharacter(l).isEmpty();
    }


    public static boolean isClosingCharacter(char c) {
        return CHARACTER_PAIRS.containsValue(c);
    }

    public static Optional<Character> findIllegalClosingCharacter(String line) {
        char[] lineChars = line.toCharArray();
        Stack<Character> openingChars = new Stack<>();

        for (char c : lineChars) {
            if (isOpeningCharacter(c))
                openingChars.push(c);
            if (isClosingCharacter(c))
                if (!isMatchingClosingCharacter(openingChars, c))
                    return Optional.of(c);
        }
        return Optional.empty();
    }

    private static boolean isMatchingClosingCharacter(Stack<Character> openingChars, char c) {
        return CHARACTER_PAIRS.get(openingChars.pop()).equals(c);
    }

    private static boolean isOpeningCharacter(char c) {
        return CHARACTER_PAIRS.containsKey(c);
    }

    public static int scoreLine(String line) {
        return findIllegalClosingCharacter(line)
                .map(ILLEGAL_CLOSING_CHAR_SCORES::get)
                .orElse(0);
    }

    public static List<Character> getCharactersToCompleteLine(String line) {
        char[] lineChars = line.toCharArray();
        Stack<Character> openingChars = new Stack<>();

        for (char c : lineChars) {
            if (isOpeningCharacter(c))
                openingChars.push(c);
            if (isClosingCharacter(c))
                openingChars.pop();
        }

        List<Character> result = new ArrayList<>(openingChars.size());
        while (!openingChars.isEmpty())
            result.add(CHARACTER_PAIRS.get(openingChars.pop()));
        return result;
    }

    public static long scoreLineCompletion(String line) {
        return getCharactersToCompleteLine(line).stream()
                .map(COMPLETION_CLOSING_CHAR_SCORES::get)
                .map(Integer::longValue)
                .reduce(0L, (p,n) -> p*5+n);
    }
}
