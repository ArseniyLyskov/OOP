package ru.nsu.lyskov;

import java.io.PrintStream;
import java.util.Map;

public class Number extends Expression {
    private final double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public void print(PrintStream out) {
        if (value % 1 == 0) {
            out.print((long) value);
        } else {
            out.print(value);
        }
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public double eval(Map<String, Double> variables) {
        return value;
    }
}
