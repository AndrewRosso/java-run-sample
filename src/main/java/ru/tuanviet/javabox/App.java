package ru.tuanviet.javabox;

public class App {

    public static void main(String[] args) {

        Class<?>[] classes = new Class[]{Benchmark1.class,Benchmark2.class,Benchmark3.class};

        new SuperBench().benchmark(classes);

    }
}