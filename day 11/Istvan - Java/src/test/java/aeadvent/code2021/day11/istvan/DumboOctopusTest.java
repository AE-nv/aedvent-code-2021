package aeadvent.code2021.day11.istvan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DumboOctopusTest {


    @Test
    void createDumboOctopus() {
        DumboOctopus octopus = new DumboOctopus(new Point(1,1), 5);
        assertThat(octopus).isNotNull();
        assertThat(octopus.getEnergyLevel()).isEqualTo(5);
        assertThat(octopus.getPosition()).isEqualTo(new Point(1,1));
    }

    @Test
    void accumulateEnergyNoFlash() {
        DumboOctopus octopus = new DumboOctopus(new Point(1,1), 5);
        assertThat(octopus.accumulateEnergy()).isFalse();
        assertThat(octopus.getEnergyLevel()).isEqualTo(6);
    }

    @Test
    void accumulateEnergyFlash() {
        DumboOctopus octopus = new DumboOctopus(new Point(1,1), 9);
        assertThat(octopus.accumulateEnergy()).isTrue();
        assertThat(octopus.getEnergyLevel()).isEqualTo(0);
    }

    @Test
    void absorbFlashNotFlashedBeforeNoFlash() {
        DumboOctopus octopus = new DumboOctopus(new Point(1,1), 5);
        assertThat(octopus.absorbFlash()).isFalse();
        assertThat(octopus.getEnergyLevel()).isEqualTo(6);
    }

    @Test
    void absorbFlashNotFlashedBeforeFlash() {
        DumboOctopus octopus = new DumboOctopus(new Point(1,1), 9);
        assertThat(octopus.absorbFlash()).isTrue();
        assertThat(octopus.getEnergyLevel()).isEqualTo(0);
    }

    @Test
    void absorbFlashFlashedBeforeNoFlash() {
        DumboOctopus octopus = new DumboOctopus(new Point(1,1), 9);
        octopus.accumulateEnergy();
        assertThat(octopus.absorbFlash()).isFalse();
        assertThat(octopus.getEnergyLevel()).isEqualTo(0);
    }

    @Test
    void canAbsorbFlashAfterNormalEnergyAbsorption() {
        DumboOctopus octopus = new DumboOctopus(new Point(1,1), 9);
        octopus.accumulateEnergy();
        octopus.accumulateEnergy();
        assertThat(octopus.absorbFlash()).isFalse();
        assertThat(octopus.getEnergyLevel()).isEqualTo(2);
    }
}
