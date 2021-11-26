package ru.tuanviet.javabox;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class SuperBenchTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfBenchmarkMethodParameterIsNull() {
        new SuperBench().benchmark(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfSomeElementOfBenchmarkMethodParameterIsNull() {
        Class[] testClasses1 = new Class[2];
        testClasses1[0] = null;
        testClasses1[1] = null;
        new SuperBench().benchmark(testClasses1);
    }
}



