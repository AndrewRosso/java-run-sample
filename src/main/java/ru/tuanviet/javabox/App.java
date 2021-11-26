package ru.tuanviet.javabox;

public class App {

    public static void main(String[] args) {

        Class[] classes = new Class[3];
        classes[0] = Benchmark1.class;
        classes[1] = Benchmark2.class;
        classes[2] = Benchmark3.class;

        new SuperBench().benchmark(classes);

    }
}