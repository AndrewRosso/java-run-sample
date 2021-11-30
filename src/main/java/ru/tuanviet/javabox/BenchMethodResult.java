package ru.tuanviet.javabox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BenchMethodResult {
    private final Class<?> benchClass;
    private final Method benchMethod;
    private final UUID id;
    private final String benchMethodName;
    private int repeats;
    private long timeout;
    private List<Long> repeatsTime;
    private Boolean successfulness;
    private int successRepeats;
    private long minTime;
    private long avgTime;
    private long maxTime;
    private long percentile75;
    private long percentile95;
    private long percentile99;

    public BenchMethodResult(Class<?> benchClass, Method benchMethod) {
        this.benchClass = benchClass;
        this.benchMethod = benchMethod;
        this.id = UUID.randomUUID();
        this.benchMethodName = methodNameFormatter(benchMethod.getName());
        runBenchMethod();
    }

    private void runBenchMethod() {
        Benchmark annotation = benchMethod.getAnnotation(Benchmark.class);
        repeats = annotation.repeats();
        timeout = annotation.timeout();
        repeatsTime = new ArrayList<>();

        for (int i = 0; i < repeats; i++) {
            long startTime = 0;
            long endTime = 0;
            startTime = System.nanoTime();
            invokeMethod();
            endTime = System.nanoTime();
            repeatsTime.add(endTime - startTime);
            if (repeatsTime.get(i) > timeout) {
                break;
            }
        }
        successRepeats = repeatsTime.size();
        successfulness = (successRepeats == repeats);
        minTime = Collections.min(repeatsTime);
        avgTime = getAverageTime(repeatsTime);
        maxTime = Collections.max(repeatsTime);
        percentile75 = getPercentile(repeatsTime,Percentile.SEVENTYFIVE);
        percentile95 = getPercentile(repeatsTime,Percentile.NINETYFIVE);
        percentile99 = getPercentile(repeatsTime,Percentile.NINETYNINE);
    }
    private String methodNameFormatter(String methodName) {
        String methodNameWithoutShould = methodName.replace("should", "");
        StringBuilder result = new StringBuilder();

        String regexPattern = "([A-Z|a-z][a-z]*)|([\\d]+)";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(methodNameWithoutShould);

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            result.append(methodNameWithoutShould.substring(start, end).toLowerCase()).append(" ");
        }
        return "> " + (result.substring(0, 1).toUpperCase() + result.substring(1)).trim();

    }

    private void invokeMethod() {
        try {
            Object instance = benchClass.getDeclaredConstructor().newInstance();
            benchMethod.invoke(instance);
        } catch (InstantiationException |
                InvocationTargetException |
                NoSuchMethodException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private long getAverageTime(List<Long> repeatsTime) {
        long sumOfResultTime = 0;
        for (long time : repeatsTime) {
            sumOfResultTime += time;
        }
        return sumOfResultTime / repeatsTime.size();
    }
    private long getPercentile(List<Long> repeatsTime, Percentile percentile) {
        Collections.sort(repeatsTime);
        int index = (int) Math.ceil((percentile.getPercent() / 100) * repeatsTime.size());
        return repeatsTime.get(index - 1);
    }

    public Class<?> getBenchClass() {
        return benchClass;
    }

    public Method getBenchMethod() {
        return benchMethod;
    }

    public UUID getId() {
        return id;
    }

    public String getBenchMethodName() {
        return benchMethodName;
    }

    public int getRepeats() {
        return repeats;
    }

    public long getTimeout() {
        return timeout;
    }

    public List<Long> getRepeatsTime() {
        return repeatsTime;
    }

    public Boolean getSuccessfulness() {
        return successfulness;
    }

    public int getSuccessRepeats() {
        return successRepeats;
    }

    public long getMinTime() {
        return minTime;
    }

    public long getAvgTime() {
        return avgTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public long getPercentile75() {
        return percentile75;
    }

    public long getPercentile95() {
        return percentile95;
    }

    public long getPercentile99() {
        return percentile99;
    }
}
