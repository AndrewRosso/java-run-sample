package ru.tuanviet.javabox;

public class Benchmark2 {
    @Benchmark(repeats = 30, timeout = 1000)
    public void should_add_30_numbers_in_1000_nanosecond() {
        add(10, 15);
    }

    private int add(int x, int y) {
        return x + y;
    }
}
