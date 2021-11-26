package ru.tuanviet.javabox;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {

        Class[] classes = new Class[2];
        classes[0] = Benchmark1.class;
        classes[1] = Benchmark2.class;
        new SuperBench().benchmark(classes);

    }
}