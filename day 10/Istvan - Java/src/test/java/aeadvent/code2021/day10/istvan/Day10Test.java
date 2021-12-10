package aeadvent.code2021.day10.istvan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day10Test {

    @ParameterizedTest
    @ValueSource(chars = {')',']','}','>'})
    void isClosingCharacterTrue(char character) {
        assertThat(Day10.isClosingCharacter(character)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(chars = {'(','[','{','<','a','x','y','z'})
    void isClosingCharacterFalse(char character) {
        assertThat(Day10.isClosingCharacter(character)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("linesWithIllegalClosingChar")
    void getIllegalClosingCharacterInIllegalLine(String line, char character) {
        Optional<Character> illegalCharacter = Day10.findIllegalClosingCharacter(line);
        assertThat(illegalCharacter)
                .isPresent()
                .contains(character);
    }

    static Stream<Arguments> linesWithIllegalClosingChar() {
        return Stream.of(
                Arguments.of("{([(<{}[<>[]}>{[]{[(<()>",'}',1197),
                Arguments.of("[[<[([]))<([[{}[[()]]]",')',3),
                Arguments.of("[{[{({}]{}}([{[{{{}}([]",']',57),
                Arguments.of("[<(<(<(<{}))><([]([]()",')',3),
                Arguments.of("<{([([[(<>()){}]>(<<{{",'>',25137)
        );
    }

    @ParameterizedTest
    @MethodSource("linesWithIllegalClosingChar")
    void scoreLine(String line, char illegalCharacter, int expectedScore) {
        assertThat(Day10.scoreLine(line)).isEqualTo(expectedScore);
    }

    @Test
    void partA() throws URISyntaxException, IOException {
        assertThat(Day10.partA("testInput.txt")).isEqualTo(26397L);
    }

    @ParameterizedTest
    @MethodSource("linesWithExpectedClosingChar")
    void getCharactersToCompleteLine(String line, List<Character> expectedClosingChars) {
        assertThat(Day10.getCharactersToCompleteLine(line)).isEqualTo(expectedClosingChars);
    }

    static Stream<Arguments>linesWithExpectedClosingChar() {
        return Stream.of(
                Arguments.of("[({(<(())[]>[[{[]{<()<>>", List.of('}','}',']',']',')','}',')',']'), 288957L),
                Arguments.of("[(()[<>])]({[<{<<[]>>(", List.of(')','}','>',']','}',')'),5566L),
                Arguments.of("(((({<>}<{<{<>}{[]{[]{}", List.of('}','}','>','}','>',')',')',')',')'),1480781L),
                Arguments.of("{<[[]]>}<{[{[{[]{()[[[]", List.of(']',']', '}','}',']','}',']', '}','>'), 995444L),
                Arguments.of("<{([{{}}[<[[[<>{}]]]>[]]",List.of(']',')','}','>'), 294L)
        );
    }

    @ParameterizedTest
    @MethodSource("linesWithExpectedClosingChar")
    void scoreLineCompletion(String line, List<Character> chars, Long expectedScore) {
        assertThat(Day10.scoreLineCompletion(line)).isEqualTo(expectedScore);
    }

    @Test
    void partB() throws URISyntaxException, IOException {
        assertThat(Day10.partB("testInput.txt")).isEqualTo(288957L);
    }
}
