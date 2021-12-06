package aeadvent.code2021.day06.istvan;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LanternFishSchoolTest {

    @Test
    void readInputTest() {
        LanternFishSchool school = LanternFishSchool.fromInitialAgeList(List.of(3,4,3,1,2));
        assertThat(school.getNumberOfFish()).isEqualTo(5);
    }

    @Test
    void simulate18Days() {
        LanternFishSchool school = LanternFishSchool.fromInitialAgeList(List.of(3,4,3,1,2));
        school.simulateEvolutionOverDays(18);
        assertThat(school.getNumberOfFish()).isEqualTo(26);
    }

    @Test
    void simulate80Days() {
        LanternFishSchool school = LanternFishSchool.fromInitialAgeList(List.of(3,4,3,1,2));
        school.simulateEvolutionOverDays(80);
        assertThat(school.getNumberOfFish()).isEqualTo(5934);
    }

    @Test
    void simulate256Days() {
        LanternFishSchool school = LanternFishSchool.fromInitialAgeList(List.of(3,4,3,1,2));
        school.simulateEvolutionOverDays(256);
        assertThat(school.getNumberOfFish()).isEqualTo(26984457539L);
    }
}
