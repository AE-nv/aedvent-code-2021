package aeadvent.code2021.day16.istvan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day16Test {

    final Day16 day = new Day16();

    @ParameterizedTest
    @MethodSource("getHexValues")
    void hexToBits(String hexValue, String bitValue) {
        assertThat(day.parseHexValue(hexValue)).isEqualTo(bitValue);
    }

    static Stream<Arguments> getHexValues() {
        return Stream.of(
                Arguments.of("0", "0000"),
                Arguments.of("1", "0001"),
                Arguments.of("2", "0010"),
                Arguments.of("3", "0011"),
                Arguments.of("4", "0100"),
                Arguments.of("5", "0101"),
                Arguments.of("6", "0110"),
                Arguments.of("7", "0111"),
                Arguments.of("8", "1000"),
                Arguments.of("9", "1001"),
                Arguments.of("A", "1010"),
                Arguments.of("B", "1011"),
                Arguments.of("C", "1100"),
                Arguments.of("D", "1101"),
                Arguments.of("E", "1110"),
                Arguments.of("F", "1111")
        );
    }

    @Test
    void toBinaryString() {
        assertThat(day.toBinaryString("D2FE28")).isEqualTo("110100101111111000101000");
    }

    @Test
    void readPackages_Literal() {
        assertThat(day.readPackages("110100101111111000101000")).containsExactly(2021L);
    }

    @ParameterizedTest
    @MethodSource("versionSums")
    void versionSum(String hexString, long expectedVersionSum) {
        day.readPackages(day.toBinaryString(hexString));
        assertThat(day.versionSum).isEqualTo(expectedVersionSum);
    }

    static Stream<Arguments> versionSums() {
        return Stream.of(
                Arguments.of("8A004A801A8002F478", 16L),
                Arguments.of("620080001611562C8802118E34", 12L),
                Arguments.of("C0015000016115A2E0802F182340", 23L),
                Arguments.of("A0016C880162017C3686B18A3D4780", 31L)
        );
    }

    @ParameterizedTest
    @MethodSource("appliedOperators")
    void applyOperators(String hexInput, Long expectedValue) {
        assertThat(day.readPackages(day.toBinaryString(hexInput))).containsExactly(expectedValue);
    }

    static Stream<Arguments> appliedOperators() {
        return Stream.of(
                Arguments.of("C200B40A82", 3L),
                Arguments.of("04005AC33890", 54L),
                Arguments.of("880086C3E88112", 7L),
                Arguments.of("CE00C43D881120", 9L),
                Arguments.of("D8005AC2A8F0", 1L),
                Arguments.of("F600BC2D8F", 0L),
                Arguments.of("9C005AC2F8F0", 0L),
                Arguments.of("9C0141080250320F1802104A08", 1L)
        );
    }
}
