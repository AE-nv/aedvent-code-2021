package aeadvent.code2021.day01.istvan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

class DepthProcessorTest {


    private final DepthProcessor depthProcessor = new DepthProcessor();

    @Test
    void directDepthIncrementsShouldBeZeroWithOnlyOneDepthProcessed() {
        depthProcessor.processDepth(100);
        assertThat(depthProcessor.getNumberOfDirectDepthIncrements()).isEqualTo(0);
    }

    @Test
    void directDepthIncrementsShouldBeOneAfterAddingAHigherDpeth() {
        depthProcessor.processDepth(100);
        depthProcessor.processDepth(200);
        assertThat(depthProcessor.getNumberOfDirectDepthIncrements()).isEqualTo(1);
    }

    @Test
    void partATest() {
        List<Integer> depthReport = List.of(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);

        depthReport.forEach(depthProcessor::processDepth);

        assertThat(depthProcessor.getNumberOfDirectDepthIncrements()).isEqualTo(7);
    }

    @ParameterizedTest
    @MethodSource("windowedTestNoWindowProcessedInputGenerator")
    void windowedIncrementShouldRemainZeroWhileNo2WindowsWereProcessed(List<Integer> depthReport) {
        depthReport.forEach(depthProcessor::processDepth);

        assertThat(depthProcessor.getNumberOfWindowedIncrements(3)).isEqualTo(0);
    }

    private static Stream<Arguments> windowedTestNoWindowProcessedInputGenerator() {
        return Stream.of(
                Arguments.of(List.of(10)),
                Arguments.of(List.of(100,200)),
                Arguments.of(List.of(100,200,300))
        );
    }


    @Test
    void windowedIncrementShouldIncreaseAfterHigherWindowWasProcessed() {
        List.of(100,200,300,400).forEach(depthProcessor::processDepth);
        assertThat(depthProcessor.getNumberOfWindowedIncrements(3)).isEqualTo(1);
    }

    @Test
    void partBTest() {
        List.of(607,618,618,617,647,716,769,792).forEach(depthProcessor::processDepth);
        assertThat(depthProcessor.getNumberOfWindowedIncrements(3)).isEqualTo(5);

    }
}
