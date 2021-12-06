package aeadvent.code2021.day06.istvan;

import java.util.Arrays;
import java.util.List;

public class LanternFishSchool {

    private long[] countPerAge;

    private LanternFishSchool(final long[] initialCountPerAge) {
        this.countPerAge = initialCountPerAge;
    }

    public static LanternFishSchool fromInitialAgeList(List<Integer> initialFishAges) {
        long[] countPerAge = new long[9];
        initialFishAges.forEach(i -> countPerAge[i] += 1);
        return new LanternFishSchool(countPerAge);
    }

    public long getNumberOfFish() {
        return Arrays.stream(countPerAge).reduce(Long::sum).getAsLong();
    }

    public void simulateEvolutionOverDays(int numberOfDays) {
        long[] startCountPerAge = countPerAge;

        for (int i = 0; i < numberOfDays; i++) {
            startCountPerAge = simulateDay(startCountPerAge);
        }

        countPerAge = startCountPerAge;
    }

    private long[] simulateDay(long[] startCountPerAge) {
        long[] newCountPerAge = new long[9];
        for (int j = 0; j < startCountPerAge.length; j++) {
            if (j == 0)
                spawnNewFish(newCountPerAge, startCountPerAge[j]);
            else
                newCountPerAge[j-1] += startCountPerAge[j];
        }
        startCountPerAge = newCountPerAge;
        return startCountPerAge;
    }

    private void spawnNewFish(long[] newCountPerAge, long startCountPerAge) {
        newCountPerAge[6] = startCountPerAge;
        newCountPerAge[8] = startCountPerAge;
    }
}
