package ru.tuanviet.javabox;

public class App {

    public static void main(String[] args) {

        Class<?>[] classes = new Class[]{BenchmarkTestClass1.class, BenchmarkTestClass2.class, BenchmarkTestClass3.class};

        new SuperBench().benchmark(classes);

    }
}