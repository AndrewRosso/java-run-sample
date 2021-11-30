package ru.tuanviet.javabox;

import static java.lang.String.format;


public class BenchPrinter {
    private final BenchMethodResult benchMethodResult;

    public BenchPrinter(BenchMethodResult benchMethodResult) {
        this.benchMethodResult = benchMethodResult;
    }

    public void printBenchMethodResult() {
        String logResult = format("[Test %s %s] \n", benchMethodResult.getId(), (benchMethodResult.getSuccessfulness() ? "PASSED" : "FAILED")) +
                benchMethodResult.getBenchMethodName() + "\n" +
                format("Repeats: %d/%d \n", benchMethodResult.getSuccessRepeats(), benchMethodResult.getRepeats()) +
                format("Timeout: %dns \n", benchMethodResult.getTimeout()) +
                format("Min: %dms \n", benchMethodResult.getMinTime()) +
                format("Avg: %dms \n", benchMethodResult.getAvgTime()) +
                format("Max: %dms \n", benchMethodResult.getMaxTime()) +
                format("75%% of the result of time are less than or equal to %dns \n", benchMethodResult.getPercentile75()) +
                format("95%% of the result of time are less than or equal to %dns \n", benchMethodResult.getPercentile95()) +
                format("99%% of the result of time are less than or equal to %dns \n", benchMethodResult.getPercentile95());

        System.out.println(logResult);
    }
}
