package ru.tuanviet.javabox;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SuperBench {
    public void benchmark(Class<?>[] classes) {
        System.out.println("-- OUTPUT ------------------------------------------------");
        System.out.println("Benchmark started at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS")));
        System.out.println("");

        for (Class oneOfClasses : classes) {
            try {
                Object instance = oneOfClasses.newInstance();
                Method method = oneOfClasses.getMethods()[0];
                String methodName = method.getName();

                Annotation annotations = method.getAnnotation(Benchmark.class);
                Benchmark mBenchmark = (Benchmark) annotations;
//                System.out.println(mBenchmark.repeats());
//                System.out.println(mBenchmark.timeout());

                long startTime = 0;
                long endTime = 0;
                long minimal = 0;
                long average = 0;
                long sumOfResultTime = 0;
                long maximum = 0;
                int successfulRepeat = 0;
                String resultTest = "FAILED";

                List<Long> resultTime = new ArrayList<>();

                for (int i = 0; i < mBenchmark.repeats(); i++) {
                    try {
                        startTime = System.nanoTime();
                        method.invoke(instance);
                        endTime = System.nanoTime();
                        successfulRepeat++;
                        resultTime.add(endTime - startTime);
                        sumOfResultTime += resultTime.get(i);
                        if (resultTime.get(i) > mBenchmark.timeout()) {
                            break;
                        }
//                            System.out.println(endTime - startTime);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

                minimal = Collections.min(resultTime);
                average = sumOfResultTime/successfulRepeat;
                maximum = Collections.max(resultTime);

                if (successfulRepeat == mBenchmark.repeats()) resultTest = "PASSED";

                System.out.println("[Test " + UUID.randomUUID() + " " + resultTest + "]");
                System.out.println(methodNameFormatter(methodName));
                System.out.println("Repeats: " + successfulRepeat + "/" + mBenchmark.repeats());
                System.out.println("Timeout: " + mBenchmark.timeout());
                System.out.println("Min: "+minimal);
                System.out.println("Avg: "+average);
                System.out.println("Max: "+maximum);

                System.out.println("75% of the numbers are less than or equal to "+getPercentile(resultTime,75));
                System.out.println("95% of the numbers are less than or equal to "+getPercentile(resultTime,95));
                System.out.println("99% of the numbers are less than or equal to "+getPercentile(resultTime,99));

                System.out.println("");


            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
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

        return "> "+result;
    }
    private long getPercentile(List<Long> result, double percentile){
        Collections.sort(result);
        int index = (int)Math.ceil(((double)percentile / (double)100) * (double)result.size());
        return result.get(index-1);
    }
    private void printMethodsTimeStats (String methodName,List<Long> resultTime,int repeat,long timeout){

    }

}

