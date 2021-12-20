package aeadvent.code2021.day20.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day20Test {

    @Test
    void readEnhancementAlgorithm() {
        var valueMap = Day20.readEnhancementAlgorithm("testInput.txt");

        assertThat(valueMap)
                .hasSize(512)
                .containsOnly('0','1');
    }

    @Test
    void readScanImage() {
        var image = Day20.readScanImage("testInput.txt");

        assertThat(image.getLightPixelCount()).isEqualTo(10);
        assertThat(image.width).isEqualTo(5);
        assertThat(image.height).isEqualTo(5);
    }

    @Test
    void startWithOne() {
        var enhancementAlgorithm = Day20.readEnhancementAlgorithm("startWith1.txt");
        var startImage = Day20.readScanImage("startWith1.txt");

        var enhanced = startImage.enhance(enhancementAlgorithm,1);
        var doubleEnhanced = enhanced.enhance(enhancementAlgorithm,2);

        var twoStepsAtOnce =startImage.enhanceRepeatedly(enhancementAlgorithm, 2);

        assertThat(doubleEnhanced.getLightPixelCount()).isEqualTo(9);
        assertThat(twoStepsAtOnce.getLightPixelCount()).isEqualTo(9);
        assertThat(doubleEnhanced.toString()).isEqualTo(twoStepsAtOnce.toString());
    }


}
