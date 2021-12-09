package aeadvent.code2021.day08.istvan;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

public class Day8Test {

    @Test
    void splitOfOutput() {
        assertThat(Day8.splitOfOutput("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe")).isEqualTo("fdgacbe cefdb cefbgd gcbe");
    }

    @Test
    void splitOOutputInDigitSignals() {
        String[] outputDigitSignals = Day8.splitOOutputInDigitSignals("fdgacbe cefdb cefbgd gcbe");
        assertThat(outputDigitSignals).containsExactly("fdgacbe", "cefdb", "cefbgd", "gcbe");
    }

    @Test
    void mapOutputDigitSignalToNumber_1 () {
        assertThat(Day8.mapToDigit("gc")).isEqualTo(1);
    }

    @Test
    void mapOutputDigitSignalToNumber_4 () {
        assertThat(Day8.mapToDigit("gecf")).isEqualTo(4);
    }

    @Test
    void mapOutputDigitSignalToNumber_7 () {
        assertThat(Day8.mapToDigit("cgb")).isEqualTo(7);
    }

    @Test
    void mapOutputDigitSignalToNumber_8 () {
        assertThat(Day8.mapToDigit("gcadebf")).isEqualTo(8);
    }

    @Test
    void countOutputDigits() throws URISyntaxException, IOException {
        Map<Integer,Integer> outputDigits = Day8.mapOutputDigits("testInput.txt");
        assertThat(outputDigits.get(1)).isEqualTo(8);
        assertThat(outputDigits.get(4)).isEqualTo(6);
        assertThat(outputDigits.get(7)).isEqualTo(5);
        assertThat(outputDigits.get(8)).isEqualTo(7);
    }

    @Test
    void partA() throws URISyntaxException, IOException {
        assertThat(Day8.partA("testInput.txt")).isEqualTo(26);
    }

    @Test
    void buildSignalDecoder() {
        Day8.SignalDecoder decoder = Day8.buildSignalDecoder("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab");
        assertThat(decoder.convertSignalToDigit("abcdefg")).isEqualTo(8);
        assertThat(decoder.convertSignalToDigit("ab")).isEqualTo(1);
        assertThat(decoder.convertSignalToDigit("abd")).isEqualTo(7);
        assertThat(decoder.convertSignalToDigit("abef")).isEqualTo(4);

        assertThat(decoder.convertSignalToDigit("abcdef")).isEqualTo(9);
        assertThat(decoder.convertSignalToDigit("bcdefg")).isEqualTo(6);
        assertThat(decoder.convertSignalToDigit("abcdeg")).isEqualTo(0);

        assertThat(decoder.convertSignalToDigit("bcdef")).isEqualTo(5);
        assertThat(decoder.convertSignalToDigit("acdfg")).isEqualTo(2);
        assertThat(decoder.convertSignalToDigit("abcdf")).isEqualTo(3);
    }

    @Test
    void convertSignalsToNumber() {
        Day8.SignalDecoder decoder = Day8.buildSignalDecoder("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab");
        assertThat(decoder.convertSignalsToNumber("cdfeb", "fcadb", "cdfeb", "cdbaf")).isEqualTo(5353);
    }

    @Test
    void partB() throws URISyntaxException, IOException {
        assertThat(Day8.partB("testInput.txt")).isEqualTo(61229);
    }
}
