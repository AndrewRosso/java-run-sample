package ru.tuanviet.javabox;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class SuperBenchTest {
    Class<?>[] testClasses;
    SuperBench testBench;

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfBenchmarkMethodParameterIsNull() {
        new SuperBench().benchmark(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfSomeElementOfBenchmarkMethodParameterIsNull() {
        testClasses = new Class[]{null, null};
        new SuperBench().benchmark(testClasses);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfAllClassesWithoutBenchmarkAnnotationMethods() {
        testClasses = new Class[]{ClassWithoutBenchMethods.class, ClassWithoutBenchMethods.class};
        new SuperBench().benchmark(testClasses);
    }

    @Test
    public void shouldGenerateBenchmarkResultsForOnlyClassesWithMarkedMethods() {
        testClasses = new Class[]{ClassWithoutBenchMethods.class, ClassWithBenchMethods.class};
        testBench = new SuperBench();
        testBench.benchmark(testClasses);
        assertThat(testBench.mapOfBenchmarkResults.size()).isEqualTo(1);
    }

    @Test
    public void shouldFailBenchWithLongBenchMethod() {
        testClasses = new Class[]{ClassWithLongBenchMethods.class};
        testBench = new SuperBench();
        testBench.benchmark(testClasses);
        assertThat(testBench.
                mapOfBenchmarkResults.
                get(ClassWithLongBenchMethods.class).
                get(0).getSuccessfulness()).
                isEqualTo(false);
    }
    @Test
    public void shouldFailBenchWithFastBenchMethod() {
        testClasses = new Class[]{ClassWithFastBenchMethods.class};
        testBench = new SuperBench();
        testBench.benchmark(testClasses);
        assertThat(testBench.
                mapOfBenchmarkResults.
                get(ClassWithFastBenchMethods.class).
                get(0).getSuccessfulness()).
                isEqualTo(true);
    }

    @Test
    public void shouldGenerateCorrectMethodNameAtBenchmarkResult() {
        testClasses = new Class[]{ClassWithBenchMethods.class};
        testBench = new SuperBench();
        testBench.benchmark(testClasses);
        assertThat(testBench.
                mapOfBenchmarkResults.
                get(ClassWithBenchMethods.class).
                get(0).getBenchMethodName()).
                isEqualTo("> Simply empty method");
    }
}

class ClassWithoutBenchMethods {
    public void simply_empty_method() {
    }
}

class ClassWithBenchMethods {
    @Benchmark(repeats = 100, timeout = 1000)
    public void simply_empty_method() {
    }
}

class ClassWithLongBenchMethods {
    @Benchmark(repeats = 100, timeout = 100000)
    public void simply_empty_method() throws InterruptedException {
        Thread.sleep(20);
    }
}
class ClassWithFastBenchMethods {
    @Benchmark(repeats = 10, timeout = 100000)
    public void simply_empty_method() {
    }
}


