package ru.tuanviet.javabox;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)

public @interface Benchmark {
    int repeats();
    long timeout();
}
