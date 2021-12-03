package ru.tuanviet.javabox;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SuperBench {
    private Map<Class<?>, List<BenchMethodResult>> mapOfBenchmarkResults;

    public void benchmark(Class<?>[] classes) {
        if (classes == null) {
            throw new IllegalArgumentException("Null arguments received");
        }
        int countOfMarkedMethod = 0;
        mapOfBenchmarkResults = new HashMap<>();
        for (Class<?> clazz : classes) {

            if (clazz != null) {
                Method[] allMethods = clazz.getMethods();

                List<BenchMethodResult> listOfBenchMethodResult = new ArrayList<>();
                for (Method method : allMethods) {
                    if (method.isAnnotationPresent(Benchmark.class)) {
                        countOfMarkedMethod++;
                        listOfBenchMethodResult.add(new BenchMethodResult(clazz, method));
                    }
                }
                if (!listOfBenchMethodResult.isEmpty()) {
                    mapOfBenchmarkResults.put(clazz, listOfBenchMethodResult);
                }
            }
        }
        if (countOfMarkedMethod == 0) {
            throw new IllegalArgumentException("Methods marked with a @Benchmark annotation not found");
        }
        printBenchmarkResults();
    }

    private void printBenchmarkResults() {
        printHeading();
        for (Class<?> clazz : mapOfBenchmarkResults.keySet()) {
            System.out.println("BENCHMARK CLASS: " + clazz.getName().toUpperCase() + "\n\r");
            for (BenchMethodResult benchMethodResult : mapOfBenchmarkResults.get(clazz)) {
                BenchPrinter benchPrint = new BenchPrinter(benchMethodResult);
                benchPrint.printBenchMethodResult();
            }
        }
    }

    private void printHeading() {
        System.out.println("-- OUTPUT ------------------------------------------------");
        System.out.println("Benchmark started at "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS")) + "\n");
    }

    public Map<Class<?>, List<BenchMethodResult>> getMapOfBenchmarkResults() {
        return mapOfBenchmarkResults;
    }
}


