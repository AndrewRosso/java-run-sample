package ru.tuanviet.javabox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class BenchmarkResult {
    private final Class<?> benchClass;
    private final List<Method> listOfBenchMethod;


    public BenchmarkResult(Class<?> benchClass, List<Method> listOfBenchMethod) {
        this.benchClass = benchClass;
        this.listOfBenchMethod = listOfBenchMethod;
    }

    private void invokeMethod()
            throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {

        Object instance = benchClass.getDeclaredConstructor().newInstance();
        for (Method benchMethod:listOfBenchMethod) {
            benchMethod.invoke(instance);
        }
    }
}
