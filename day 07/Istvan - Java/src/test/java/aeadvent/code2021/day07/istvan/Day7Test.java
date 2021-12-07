package aeadvent.code2021.day07.istvan;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day7Test {

    @Test
    void readPositions() throws URISyntaxException, IOException {
        List<Integer> positions = Day7.readPositions("testInput.txt");
        assertThat(positions).containsExactly(0,1,1,2,2,2,4,7,14,16);
    }

    @Test
    void calculateFuelToMoveToOptimalPositionWithConstantConsumption() {
        assertThat(Day7.calculateFuelCostToMoveToOptimalPosition(List.of(0,1,1,2,2,2,4,7,14,16),Day7::calculateConstantFuelCostToMoveTo)).isEqualTo(37);
    }

    @Test
    void calculateFuelToMoveToOptimalPositionWithIncrementalConsumption() {
        assertThat(Day7.calculateFuelCostToMoveToOptimalPosition(List.of(0,1,1,2,2,2,4,7,14,16), Day7::calculateIncrementalFuelCostToMoveTo)).isEqualTo(168);
    }

}
