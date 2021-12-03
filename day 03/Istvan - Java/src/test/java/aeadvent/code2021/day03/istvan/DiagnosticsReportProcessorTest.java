package aeadvent.code2021.day03.istvan;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DiagnosticsReportProcessorTest {

    public static final List<String> SAMPLE_INPUT = List.of("00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010");
    public static final List<String> LIST_OF_4_30_22 = List.of("00100", "11110", "10110");
    public static final List<String> LIST_OF_12_12 = List.of("01100","01100");
    private final DiagnosticsReportProcessor reportProcessor = new DiagnosticsReportProcessor();

    @Test
    void calculateGammaForFour() {
        reportProcessor.process("00100");
        assertThat(reportProcessor.calculateGamma()).isEqualTo(4);
    }

    @Test
    void calculateGammaForTwelveAndTwelve() {
        processList(LIST_OF_12_12);
        assertThat(reportProcessor.calculateGamma()).isEqualTo(12);
    }

    @Test
    void calculateGammaForFourThirtyAndTwentyTwo() {
        processList(LIST_OF_4_30_22);
        assertThat(reportProcessor.calculateGamma()).isEqualTo(22);
    }

    @Test
    void calculateEpsilonForFour() {
        reportProcessor.process("00100");
        assertThat(reportProcessor.calculateEpsilon()).isEqualTo(27);
    }

    @Test
    void calculateEpsilonForTwelveAndTwelve() {
        processList(LIST_OF_12_12);
        assertThat(reportProcessor.calculateEpsilon()).isEqualTo(19);
    }

    @Test
    void calculateEpsilonForFourThirtyAndTwentyTwo() {
        processList(LIST_OF_4_30_22);
        assertThat(reportProcessor.calculateEpsilon()).isEqualTo(9);
    }

    @Test
    void partA() {
        processList(SAMPLE_INPUT);
        assertThat(reportProcessor.calculateGamma()).isEqualTo(22);
        assertThat(reportProcessor.calculateEpsilon()).isEqualTo(9);
    }

    @Test
    void calculateOxygenGeneratorRatingForFour() {
        reportProcessor.process("00100");
        assertThat(reportProcessor.calculateOxygenGeneratorRating()).isEqualTo(4);
    }

    @Test
    void calculateOxygenGeneratorRatingForTwelveAndThirteen() {
        reportProcessor.process("01100");
        reportProcessor.process("01101");
        assertThat(reportProcessor.calculateOxygenGeneratorRating()).isEqualTo(13);
    }

    @Test
    void calculateCO2ScrubberRatingForFour() {
        reportProcessor.process("00100");
        assertThat(reportProcessor.calculateCO2ScrubberRating()).isEqualTo(4);
    }

    @Test
    void calculateCO2ScrubberRatingForTwelveAndThirteenAndSixteen() {
        processList(List.of("01100","01101","10000"));
        assertThat(reportProcessor.calculateCO2ScrubberRating()).isEqualTo(16);
    }

    @Test
    void partB() {
        processList(SAMPLE_INPUT);
        assertThat(reportProcessor.calculateOxygenGeneratorRating()).isEqualTo(23);
        assertThat(reportProcessor.calculateCO2ScrubberRating()).isEqualTo(10);
    }

    private void processList(List<String> list) {
        list.forEach(reportProcessor::process);
    }

}
