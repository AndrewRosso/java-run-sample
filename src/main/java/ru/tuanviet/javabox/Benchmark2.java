package ru.tuanviet.javabox;

public class Benchmark2 {
    @Benchmark(repeats = 50, timeout = 20000)
    public void should_add_50_numbers_in_20000_nanosecond() {
        add(10, 15);
    }

    private int add(int x, int y) {
        return x * y;
    }
}
