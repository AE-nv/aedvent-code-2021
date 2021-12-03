package aeadvent.code2021.day03.istvan;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Runner {
    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("Part A: "+ partA("input.txt"));
        System.out.println("Part B:" + partB("input.txt"));
    }

    public static int partA(String fileName) throws URISyntaxException, IOException {
        DiagnosticsReportProcessor reportProcessor = getDiagnosticsReportProcessor(fileName);
        return reportProcessor.calculateGamma()* reportProcessor.calculateEpsilon();
    }

    private static DiagnosticsReportProcessor getDiagnosticsReportProcessor(String fileName) throws URISyntaxException, IOException {
        DiagnosticsReportProcessor reportProcessor = new DiagnosticsReportProcessor();
        Path inputFilePath = Path.of(Objects.requireNonNull(Runner.class.getClassLoader().getResource(fileName)).toURI());
        Files.lines(inputFilePath).forEach(reportProcessor::process);
        return reportProcessor;
    }

    public static int partB(String fileName) throws URISyntaxException, IOException {
        DiagnosticsReportProcessor reportProcessor = getDiagnosticsReportProcessor(fileName);
        return reportProcessor.calculateOxygenGeneratorRating()* reportProcessor.calculateCO2ScrubberRating();
    }
}
