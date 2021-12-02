package aeadvent.code2021.day02.istvan;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RunnerTest {

    @Test
    void partA() throws URISyntaxException, IOException {
        assertThat(Runner.partA("testInputA.txt")).isEqualTo(150);
    }

}