package ru.tuanviet.javabox;

import org.junit.runner.RunWith;

@RunWith(SuperBenchRunner.class)
public class BenchmarkTestClass1 {
    @Benchmark(repeats = 100, timeout = 1000000)
    public void should_add_100_numbers_in_1_millisecond() {
        add(10, 15);
    }

    @Benchmark(repeats = 20, timeout = 1000000)
    public void should_add_20_numbers_in_1_millisecond() {
        add(10, 15);
    }

    private int add(int x, int y) {
        return x + y;
    }

}
