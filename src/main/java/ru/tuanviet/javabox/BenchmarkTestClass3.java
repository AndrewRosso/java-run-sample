package ru.tuanviet.javabox;

public class BenchmarkTestClass3 {
    public void should_add_numbers() {
        add(10, 15);
    }

    private int add(int x, int y) {
        return x - y;
    }
}
