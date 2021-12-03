package ru.tuanviet.javabox;


import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Method;

public class SuperBenchRunner extends Runner {
    private final Class<?> benchClass;

    public SuperBenchRunner(Class<?> benchClass) {
        this.benchClass = benchClass;
    }

    @Override
    public Description getDescription() {
        return Description
                .createTestDescription(benchClass, SuperBenchRunner.class.getName());
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            for (Method method : benchClass.getMethods()) {
                if (method.isAnnotationPresent(Benchmark.class)) {
                    notifier.fireTestStarted(Description
                            .createTestDescription(benchClass, method.getName()));

                    BenchMethodResult benchMethodResult = new BenchMethodResult(benchClass, method);

                    if (!benchMethodResult.getSuccessfulness()) {
                        notifier.fireTestFailure(
                                new Failure(Description.createTestDescription(benchClass, method.getName()),
                                        new BenchFailedException("Count of success repeats: "+ benchMethodResult.getSuccessRepeats()+"/"+ benchMethodResult.getRepeats())));
                    }
                    BenchPrinter benchPrint = new BenchPrinter(benchMethodResult);
                    benchPrint.printBenchMethodResult();

                    notifier.fireTestFinished(Description
                            .createTestDescription(benchClass, method.getName()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
