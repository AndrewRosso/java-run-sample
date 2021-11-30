package ru.tuanviet.javabox;

import java.lang.reflect.Method;
import java.util.*;

public class SuperBench {
    Map<Class<?>,BenchmarkResult> mapOfBenchmarkResults;
    List<BenchmarkResult> listOfBenchmarkResults;// на всякий

    public void benchmark(Class<?>[] classes) {
        if (classes == null) {
            throw new IllegalArgumentException("Null arguments received");
        }
        listOfBenchmarkResults = new ArrayList<>();
        for (Class<?> clazz : classes) {

//            fd
//                Object instance = oneOfClasses.getDeclaredConstructor().newInstance();
            Method[] allMethods = clazz.getMethods();
//                System.out.println("BENCHMARK CLASS: " + oneOfClasses.getName().toUpperCase() + "\n\r");
//                int countOfMarkedMethods = 0;

            List<Method> listOfMarkedMethods = new ArrayList<>();
            for (Method method : allMethods) {
                if (method.isAnnotationPresent(Benchmark.class)) {
//                    countOfMarkedMethods++;

//                    String methodName = method.getName();
//                    Benchmark annotation = method.getAnnotation(Benchmark.class);
//
//                    int repeats = annotation.repeats();
//                    long timeout = annotation.timeout();
//                    List<Long> repeatsTime = new ArrayList<>();
//
//                    for (int i = 0; i < repeats; i++) {
//                        long startTime = 0;
//                        long endTime = 0;
//                        try {
//                            startTime = System.nanoTime();
//                            method.invoke(instance);
//                            endTime = System.nanoTime();
//                        } catch (IllegalAccessException | InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                        repeatsTime.add(endTime - startTime);
//
//                        if (repeatsTime.get(i) > timeout) {
//                            break;
//                        }
//                    }
//                    printMethodsTimeStats(methodName, repeatsTime, repeats, timeout);
//                }
                    listOfMarkedMethods.add(method);
                }
//            if (countOfMarkedMethods == 0) {
//                System.out.println("Provided class do not have methods marked with a @Benchmark annotation");
//            }
            }
            if (!listOfMarkedMethods.isEmpty()) {
                BenchmarkResult benchResult = new BenchmarkResult(clazz, listOfMarkedMethods);
                listOfBenchmarkResults.add(benchResult);
//                mapOfBenchmarkResults.put(clazz,benchResult);
            }

            if (listOfBenchmarkResults.isEmpty()) {
                throw new IllegalArgumentException("Methods marked with a @Benchmark annotation not found");
            }
        }


//    private void printHeading() {
//        System.out.println("-- OUTPUT ------------------------------------------------");
//        System.out.println("Benchmark started at "
//                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS")));
//        System.out.println("");
//    }
//
//    private void printMethodsTimeStats(String methodName, List<Long> repeatsTime, int repeat, long timeout) {
//        String resultTest = "FAILED";
//        int successfulRepeat = repeatsTime.size();
//
//        if (successfulRepeat == repeat) resultTest = "PASSED";
//
//        System.out.println("[Test " + UUID.randomUUID() + " " + resultTest + "]");
//        System.out.println(methodNameFormatter(methodName));
//        System.out.println("Repeats: " + successfulRepeat + "/" + repeat);
//        System.out.println("Timeout: " + timeout);
//
//        System.out.println("Min: " + Collections.min(repeatsTime));
//        System.out.println("Avg: " + getAverageTime(repeatsTime));
//        System.out.println("Max: " + Collections.max(repeatsTime));
//
//
//        System.out.println("75% of the numbers are less than or equal to " + getPercentile(repeatsTime, 75));
//        System.out.println("95% of the numbers are less than or equal to " + getPercentile(repeatsTime, 95));
//        System.out.println("99% of the numbers are less than or equal to " + getPercentile(repeatsTime, 99));
//        System.out.println("");
//    }
//
//    private String methodNameFormatter(String methodName) {
//        String str = methodName.replace("should", "");
//        String result;
//
//        if (str.contains("_")) {
//            str = str.replace("_", " ").trim();
//            result = str.substring(0, 1).toUpperCase() + str.substring(1);
//        } else {
//            result = str.replaceAll("(?<=[A-Za-z])(?=[0-9])|(?<=[0-9])(?=[A-Za-z])|(?<=[a-z])(?=[A-Z])", " ");
//        }
//        return "> " + result;
//    }
//
//    private long getPercentile(List<Long> repeatsTime, double percentile) {
//        Collections.sort(repeatsTime);
//        int index = (int) Math.ceil((percentile / 100) * repeatsTime.size());
//        return repeatsTime.get(index - 1);
//    }
//
//    private long getAverageTime(List<Long> repeatsTime) {
//        long sumOfResultTime = 0;
//        for (long time : repeatsTime) {
//            sumOfResultTime += time;
//        }
//        return sumOfResultTime / repeatsTime.size();
//    }
    }
}

