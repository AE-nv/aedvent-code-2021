package aeadvent.code2021.day09.istvan;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class Day9Test {

    @Test
    void partB() throws URISyntaxException, IOException {
        CaveMap map = CaveMap.fromFile("testInput.txt");
        assertThat(Day9.partB(map)).isEqualTo(1134L);
    }
}
