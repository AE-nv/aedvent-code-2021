package aeadvent.code2021.day16.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Day16 {
    public  long versionSum = 0L;


    public String parseHexValue(String hexValue) {
        StringBuilder builder = new StringBuilder(Long.toBinaryString(Long.valueOf(hexValue, 16)));
        while (builder.length() < 4)
            builder.insert(0,"0");
        return builder.toString();
    }

    public  String toBinaryString(String hexString) {
        StringBuilder builder = new StringBuilder(hexString.length()*4);
        for (char c: hexString.toCharArray())
            builder.append(parseHexValue(String.valueOf(c)));
        return builder.toString();
    }

    public  List<Long> readPackages(String input) {
        if (input.isEmpty())
            return Collections.emptyList();

        String newInput = input;

        List<Long> results = new LinkedList<>();
        while (!newInput.replaceAll("0","").isEmpty()) {
            var parseResult = readPackage(newInput);
            results.addAll(parseResult.result());
            newInput = parseResult.remainingString;
        }
        return  results;
    }

    private  ParseResult readPackage(String binaryString) {
        int version = Integer.parseInt(binaryString.substring(0,3),2);
        versionSum+= version;
        Integer type = Integer.parseInt(binaryString.substring(3,6),2);

        return switch(type) {
            case 4 ->  parseLiteralPackage(binaryString.substring(6));
            default -> parseOperator(binaryString.substring(6), type);
        };
    }

    private  ParseResult parseOperator(String binaryString, Integer type) {
        char lengthTypeId = binaryString.charAt(0);
        List<Long> results;
        String newBinaryString;

        if (lengthTypeId == '0') {
            int bitsInSubpackets = Integer.parseInt(binaryString.substring(1, 16),2);
            results = readPackages(binaryString.substring(16, 16 + bitsInSubpackets));
            newBinaryString = binaryString.substring(16+bitsInSubpackets);
        }
        else {
            int numberOfSubPackets = Integer.parseInt(binaryString.substring(1, 12),2);
            results = new LinkedList<>();
            newBinaryString = binaryString.substring(12);
            for (int i = 0; i < numberOfSubPackets; i++) {
                var parseResult = readPackage(newBinaryString);
                results.addAll(parseResult.result());
                newBinaryString = parseResult.remainingString();
            }
        }
        return new ParseResult(applyOperator(type, results), newBinaryString);
    }

    private List<Long> applyOperator(Integer operatorType, List<Long> results) {
        return List.of(switch(operatorType){
            case 0 -> results.stream().reduce(0L, Long::sum);
            case 1 -> results.stream().reduce(1L, (a,b) -> a*b);
            case 2 -> results.stream().min(Long::compareTo).orElseThrow();
            case 3 -> results.stream().max(Long::compareTo).orElseThrow();
            case 5 -> results.get(0) > results.get(1)? 1L:0L;
            case 6 -> results.get(0) < results.get(1)? 1L:0L;
            case 7 -> results.get(0).equals(results.get(1))? 1L:0L;
            default -> throw new IllegalArgumentException("Unknown operator type " + operatorType);
        });
    }

    private  ParseResult parseLiteralPackage(String binaryString) {
        String part = binaryString.substring(0,5);
        StringBuilder builder = new StringBuilder();
        int offSet = 5;
        while (part.startsWith("1")) {
            builder.append(part.substring(1));
            part = binaryString.substring(offSet, 5+offSet);
            offSet+=5;
        }
        builder.append(part.substring(1));

        while (offSet%5 != 0 && offSet < binaryString.length() && binaryString.charAt(offSet) == '0')
            offSet++;

        return new ParseResult(List.of(Long.parseLong(builder.toString(),2)), binaryString.substring(offSet));
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        String hexLine = Files.readString(Path.of(Objects.requireNonNull(Day16.class.getClassLoader().getResource("input.txt")).toURI()));

        System.out.println("Part A: " + partA(hexLine));
        System.out.println("Part B: " +partB(hexLine));
    }

    private static Long partB(String hexLine) {
        var d = new Day16();
        return d.readPackages(d.toBinaryString(hexLine)).get(0);
    }

    private static long partA(String hexLine) {
        var d = new Day16();
        d.readPackages(d.toBinaryString(hexLine));
        return d.versionSum;
    }

    record ParseResult(List<Long> result, String remainingString){}
}
