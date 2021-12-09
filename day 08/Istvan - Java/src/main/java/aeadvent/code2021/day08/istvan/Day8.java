package aeadvent.code2021.day08.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day8 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("Part A: " + partA("input.txt"));
        System.out.println("Part B: " + partB("input.txt"));
    }


    public static String splitOfOutput(String inputLine) {
        return inputLine.split("\\|")[1].trim();
    }

    public static String[] splitOOutputInDigitSignals(String outputSignals) {
        return outputSignals.split(" ");
    }

    public static Integer mapToDigit(String outputDigitSignal) {
        return switch(outputDigitSignal.trim().length()) {
            case 2 -> 1;
            case 3 -> 7;
            case 4 -> 4;
            case 7 -> 8;
            default -> -1;
        };
    }

    public static Map<Integer, Integer> mapOutputDigits(String filename) throws URISyntaxException, IOException {
        return Files.lines(Path.of(Objects.requireNonNull(Day8.class.getClassLoader().getResource(filename)).toURI()))
                .map(Day8::splitOfOutput)
                .flatMap(s -> Arrays.stream(splitOOutputInDigitSignals(s)))
                .map(Day8::mapToDigit)
                .filter(i -> i != -1)
                .collect(Collectors.toMap(Function.identity(), v -> 1, Integer::sum));
    }

    public static Integer partA(String inputFile) throws URISyntaxException, IOException {
        return mapOutputDigits(inputFile)
                .values()
                .stream()
                .reduce(Integer::sum)
                .get();
    }

    public static long partB(String inputfile) throws URISyntaxException, IOException {
        return Files.lines(Path.of(Objects.requireNonNull(Day8.class.getClassLoader().getResource(inputfile)).toURI()))
                .map( l -> buildSignalDecoder(l.split("\\|")[0]).convertSignalsToNumber(splitOfOutput(l).split(" ")))
                .map(Integer::longValue)
                .reduce(Long::sum)
                .get();


    }

    static String removeCharsFromString(String subject, String toRemove) {
        String result = subject;

        for (char c : toRemove.toCharArray()) {
            result = result.replace(String.valueOf(c), "");
        }
        return result.trim();
    }

    public static SignalDecoder buildSignalDecoder(String wireSignals) {
        List<String> unmappedNumbers  = Arrays.stream(wireSignals.split(" "))
                .map(Day8::orderCharactersInString)
                .toList();

        Map<Integer, String> lengthBasedDecoded = decodeByLength(unmappedNumbers);

        unmappedNumbers = unmappedNumbers.stream()
                .filter(s -> s.length() == 6)
                .toList();

        char top = determineTopSignal(lengthBasedDecoded);

        char bottom = determineBottom(unmappedNumbers, lengthBasedDecoded, top);

        char leftBottom = determineLeftBottom(lengthBasedDecoded, top, bottom);

        char rightBottom =
                determineRightBottom(unmappedNumbers, lengthBasedDecoded, top, bottom, leftBottom);

        char rightTop = determineRightTop(lengthBasedDecoded, rightBottom);

        char leftTop = determineLeftTop(unmappedNumbers, lengthBasedDecoded, bottom, leftBottom);

        char middle = determineMiddle(lengthBasedDecoded, leftTop);

        return new SignalDecoder(top,leftTop, rightTop, middle, leftBottom, rightBottom, bottom);
    }

    private static char determineMiddle(Map<Integer, String> lengthBasedDecoded, char leftTop) {
        var tempMiddle = removeCharsFromString(lengthBasedDecoded.get(4), lengthBasedDecoded.get(1));
        char middle = removeCharsFromString(tempMiddle, String.valueOf(leftTop)).charAt(0);
        return middle;
    }

    private static Character determineLeftTop(List<String> unmappedNumbers, Map<Integer, String> lengthBasedDecoded, char bottom, char leftBottom) {
        return unmappedNumbers.stream()
                .map(s -> removeCharsFromString(s, lengthBasedDecoded.get(7)))
                .map(s -> removeCharsFromString(s, String.valueOf(bottom)))
                .map(s -> removeCharsFromString(s, String.valueOf(leftBottom)))
                .filter(s -> s.length() == 1)
                .map(s -> s.charAt(0))
                .findAny().get();
    }

    private static char determineRightTop(Map<Integer, String> lengthBasedDecoded, char rightBottom) {
        return removeCharsFromString(lengthBasedDecoded.get(1), String.valueOf(rightBottom)).charAt(0);
    }

    private static Character determineRightBottom(List<String> unmappedNumbers, Map<Integer, String> lengthBasedDecoded, char top, char bottom, char leftBottom) {
        return unmappedNumbers.stream()
                .map(s -> removeCharsFromString(s, String.valueOf(top)))
                .map(s -> removeCharsFromString(s, removeCharsFromString(lengthBasedDecoded.get(4), lengthBasedDecoded.get(1))))
                .map(s -> removeCharsFromString(s, String.valueOf(bottom)))
                .map(s -> removeCharsFromString(s, String.valueOf(leftBottom)))
                .filter(s -> s.length() == 1)
                .map(s -> s.charAt(0))
                .findAny().get();
    }

    private static char determineLeftBottom(Map<Integer, String> lengthBasedDecoded, char top, char bottom) {
        var tempLB = removeCharsFromString(lengthBasedDecoded.get(8),String.valueOf(top));
        tempLB = removeCharsFromString(tempLB, lengthBasedDecoded.get(1));
        tempLB = removeCharsFromString(tempLB, removeCharsFromString(lengthBasedDecoded.get(4), lengthBasedDecoded.get(1)));
        char leftBottom = removeCharsFromString(tempLB, String.valueOf(bottom)).charAt(0);
        return leftBottom;
    }

    private static Character determineBottom(List<String> unmappedNumbers, Map<Integer, String> lengthBasedDecoded, char top) {
        return unmappedNumbers.stream()
                .map(s -> removeCharsFromString(s, String.valueOf(top)))
                .map(s -> removeCharsFromString(s, lengthBasedDecoded.get(1)))
                .map(s -> removeCharsFromString(s, removeCharsFromString(lengthBasedDecoded.get(4), lengthBasedDecoded.get(1))))
                .filter(s -> s.length() == 1)
                .map(s -> s.charAt(0))
                .findAny().get();
    }

    private static char determineTopSignal(Map<Integer, String> lengthBasedDecoded) {
        return removeCharsFromString(lengthBasedDecoded.get(7), lengthBasedDecoded.get(1)).charAt(0);
    }

    private static Map<Integer, String> decodeByLength(List<String> unmappedNumbers) {
        return unmappedNumbers.stream()
                .filter(s -> Day8.mapToDigit(s) != -1)
                .collect(Collectors.toMap(s -> Day8.mapToDigit(s), Function.identity()));
    }

    public static String orderCharactersInString(String input) {
        char[] characters = input.toCharArray();
        Arrays.sort(characters);
        return new String(characters);
    }



    static class SignalDecoder {

        private Map<String, Integer> signalToDigit;

        SignalDecoder(char top, char topLeft, char topRight, char middle, char bottomLeft, char bottomRight, char bottom) {
            signalToDigit = new HashMap<>();
            signalToDigit.put(signalValue(top, topLeft, topRight,bottomLeft,bottomRight, bottom), 0);
            signalToDigit.put(signalValue(topRight, bottomRight), 1);
            signalToDigit.put(signalValue(top, topRight, middle, bottomLeft, bottom), 2);
            signalToDigit.put(signalValue(top, topRight, middle, bottomRight, bottom), 3);
            signalToDigit.put(signalValue(topLeft, topRight, middle, bottomRight), 4);
            signalToDigit.put(signalValue(top, topLeft, middle, bottomRight, bottom), 5);
            signalToDigit.put(signalValue(top, topLeft, middle, bottomLeft, bottomRight, bottom), 6);
            signalToDigit.put(signalValue(top, topRight, bottomRight), 7);
            signalToDigit.put(signalValue(top, topLeft, topRight, middle, bottomLeft, bottomRight, bottom), 8);
            signalToDigit.put(signalValue(top, topLeft, topRight,middle, bottomRight,bottom), 9);
        }

        Integer convertSignalToDigit(String signal) {
            return signalToDigit.get(signal);
        }
        
        private String signalValue(char... signals) {
            Arrays.sort(signals);
            return String.valueOf(signals);
        }

        Integer convertSignalsToNumber(String... signals) {
            final StringBuilder builder = new StringBuilder(signals.length);

            Arrays.stream(signals)
                    .map(String::toCharArray)
                    .map(this::signalValue)
                    .map(this::convertSignalToDigit)
                    .forEach(i -> builder.append(i));

            return Integer.valueOf(builder.toString());
        }

    }

}
