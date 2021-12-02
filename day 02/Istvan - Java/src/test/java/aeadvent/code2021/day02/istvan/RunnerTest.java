package aeadvent.code2021.day02.istvan;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class RunnerTest {

    @Test
    void partA() throws URISyntaxException, IOException {
        assertThat(Runner.partA("testInput.txt")).isEqualTo(150);
    }

    @Test
    void partB() throws IOException, URISyntaxException {
        assertThat(Runner.partB("testInput.txt")).isEqualTo(900);
    }

}