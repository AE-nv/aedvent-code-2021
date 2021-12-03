package aeadvent.code2021.day03.istvan;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class DiagnosticsReportProcessor {
    private int[] oneCounter;
    private int[] zeroCounter;
    private List<String> values = new LinkedList<>();
    private String prefixSoFar = "";
    private int filterBitPosition = 0;

    public DiagnosticsReportProcessor() {
    }

    public DiagnosticsReportProcessor(int filterBitPosition , String prefixSoFar) {
        this.filterBitPosition = filterBitPosition;
        this.prefixSoFar = prefixSoFar;
    }

    public void process(String binaryCodeString) {
        values.add(binaryCodeString);
        char[] chars = binaryCodeString.toCharArray();
        if (oneCounter == null) {
            oneCounter = new int[chars.length];
            Arrays.fill(oneCounter, 0);
            zeroCounter = Arrays.copyOfRange(oneCounter, 0, oneCounter.length);
        }

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '1')
                oneCounter[i] += 1;
            else
                zeroCounter[i] += 1;
        }



    }

    public int calculateGamma() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < oneCounter.length; i ++) {
            if (isOneMostCommonForPosition(i))
                builder.append("1");
            else
                builder.append("0");
        }
        return Integer.parseInt(builder.toString(),2);
    }

    private boolean isOneMostCommonForPosition(int i) {
        return oneCounter[i] >= zeroCounter[i];
    }

    public int calculateEpsilon() {
        int max = Integer.parseInt(new String(new char[oneCounter.length]).replace("\0","1"),2);
        return calculateGamma() ^ max;
    }

    public int calculateOxygenGeneratorRating() {
        if (isRatingValueDetermined())
            return Integer.parseInt(values.get(0),2);

        String newPrefix = new StringBuilder(prefixSoFar).append((isOneMostCommonForPosition(filterBitPosition)?"1":"0")).toString();

        DiagnosticsReportProcessor subReportProcessor = new DiagnosticsReportProcessor(filterBitPosition+1, newPrefix);
        processSubReport((String s) -> s.startsWith(newPrefix), subReportProcessor);

        return subReportProcessor.calculateOxygenGeneratorRating();
    }

    public int calculateCO2ScrubberRating() {
        if (isRatingValueDetermined())
            return Integer.parseInt(values.get(0),2);

        String newPrefix = new StringBuilder(prefixSoFar).append((isOneMostCommonForPosition(filterBitPosition)?"0":"1")).toString();

        DiagnosticsReportProcessor subReportProcessor = new DiagnosticsReportProcessor(filterBitPosition+1, newPrefix);
        processSubReport((String s) -> s.startsWith(newPrefix), subReportProcessor);

        return subReportProcessor.calculateCO2ScrubberRating();
    }

    private void processSubReport(Predicate<String> predicate, DiagnosticsReportProcessor reportProcessor) {
        values.stream()
                .filter(predicate)
                .forEach(reportProcessor::process);
    }

    private boolean isRatingValueDetermined() {
        return values.size() == 1;
    }
}
