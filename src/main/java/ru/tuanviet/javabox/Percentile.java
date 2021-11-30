package ru.tuanviet.javabox;

public enum Percentile {
    SEVENTYFIVE(75),
    NINETYFIVE(95),
    NINETYNINE(99);

    private final double percent;

    Percentile(double percent) {
        this.percent = percent;
    }

    public double getPercent() {
        return percent;
    }
}
