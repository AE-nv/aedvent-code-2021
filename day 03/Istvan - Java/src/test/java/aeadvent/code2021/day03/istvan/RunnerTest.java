package aeadvent.code2021.day03.istvan;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class RunnerTest {

    @Test
    void partA() throws URISyntaxException, IOException {
        assertThat(Runner.partA("testInput.txt")).isEqualTo(198);
    }

    @Test
    void partB() throws URISyntaxException, IOException {
        assertThat(Runner.partB("testInput.txt")).isEqualTo(230);
    }
}
