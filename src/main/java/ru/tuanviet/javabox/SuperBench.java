package ru.tuanviet.javabox;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SuperBench {
    public void benchmark(Class<?>[] classes) {
        if (classes == null) {
            throw new IllegalArgumentException("Null arguments received");
        }

        printHeading();

        for (Class oneOfClasses : classes) {
            try {
                Object instance = oneOfClasses.getDeclaredConstructor().newInstance();
                Method[] allMethods = oneOfClasses.getMethods();
                for (Method method : allMethods) {
                    if (method.isAnnotationPresent(Benchmark.class)) {
                        String methodName = method.getName();
                        Annotation annotations = method.getAnnotation(Benchmark.class);
                        Benchmark mBenchmark = (Benchmark) annotations;

                        int repeats = mBenchmark.repeats();
                        long timeout = mBenchmark.timeout();
                        List<Long> resultTime = new ArrayList<>();

                        for (int i = 0; i < repeats; i++) {
                            try {
                                long startTime = System.nanoTime();
                                method.invoke(instance);
                                long endTime = System.nanoTime();
                                resultTime.add(endTime - startTime);
                                if (resultTime.get(i) > timeout) {
                                    break;
                                }
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        printMethodsTimeStats(methodName, resultTime, repeats, timeout);
                    }
                }
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void printHeading() {
        System.out.println("-- OUTPUT ------------------------------------------------");
        System.out.println("Benchmark started at "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS")));
        System.out.println("");
    }

    private void printMethodsTimeStats(String methodName, List<Long> resultTime, int repeat, long timeout) {
        String resultTest = "FAILED";
        int successfulRepeat = resultTime.size();

        if (successfulRepeat == repeat) resultTest = "PASSED";

        System.out.println("[Test " + UUID.randomUUID() + " " + resultTest + "]");
        System.out.println(methodNameFormatter(methodName));
        System.out.println("Repeats: " + successfulRepeat + "/" + repeat);
        System.out.println("Timeout: " + timeout);

        System.out.println("Min: " + Collections.min(resultTime));
        System.out.println("Avg: " + averageTime(resultTime));
        System.out.println("Max: " + Collections.max(resultTime));


        System.out.println("75% of the numbers are less than or equal to " + getPercentile(resultTime, 75));
        System.out.println("95% of the numbers are less than or equal to " + getPercentile(resultTime, 95));
        System.out.println("99% of the numbers are less than or equal to " + getPercentile(resultTime, 99));
        System.out.println("");
    }

    private String methodNameFormatter(String methodName) {
        String str = methodName.replace("should", "");
        String result;

        if (str.contains("_")) {
            str = str.replace("_", " ").trim();
            result = str.substring(0, 1).toUpperCase() + str.substring(1);
        } else {
            result = str.replaceAll("(?<=[A-Za-z])(?=[0-9])|(?<=[0-9])(?=[A-Za-z])|(?<=[a-z])(?=[A-Z])", " ");
        }
        return "> " + result;
    }

    private long getPercentile(List<Long> result, double percentile) {
        Collections.sort(result);
        int index = (int) Math.ceil((percentile / (double) 100) * (double) result.size());
        return result.get(index - 1);
    }

    private long averageTime(List<Long> resultTime) {
        long sumOfResultTime = 0;
        for (long time : resultTime) {
            sumOfResultTime += time;
        }
        return sumOfResultTime / resultTime.size();
    }
}

