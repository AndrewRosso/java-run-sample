package ru.tuanviet.javabox;

public class Benchmark1 {
    @Benchmark(repeats = 100, timeout = 10000000)
    public void should_add_20_numbers_in_1_millisecond() {
        add(10, 15);
    }

    private int add(int x, int y) {
        return x + y;
    }
}
