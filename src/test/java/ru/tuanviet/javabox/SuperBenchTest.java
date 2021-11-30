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
        testClasses = new Class[]{null,null};
        new SuperBench().benchmark(testClasses);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfClassesWithoutBenchmarkAnnotationMethods() {
        testClasses = new Class[]{ClassWithoutBenchMethods.class};
        new SuperBench().benchmark(testClasses);
    }

    @Test
    public void shouldGenerateBenchmarkResults(){
        testClasses = new Class[]{ClassWithBenchMethods.class};
        testBench = new SuperBench();
        testBench.benchmark(testClasses);
        assertThat(testBench.listOfBenchmarkResults.size()).isEqualTo(1);
    }




}

class ClassWithoutBenchMethods{
    public void simplyEmptyMethod() {
    }
}
class ClassWithBenchMethods{
    @Benchmark(repeats = 100, timeout = 1000)
    public void simplyEmptyMethod() {
    }
}


