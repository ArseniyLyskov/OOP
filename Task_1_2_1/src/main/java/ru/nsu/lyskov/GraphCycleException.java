package ru.nsu.lyskov;

public class GraphCycleException extends Exception {
    public GraphCycleException(String message) {
        super(message);
    }
}
