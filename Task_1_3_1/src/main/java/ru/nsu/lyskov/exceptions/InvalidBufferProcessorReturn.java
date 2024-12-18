package ru.nsu.lyskov.exceptions;

public class InvalidBufferProcessorReturn extends RuntimeException {
    public InvalidBufferProcessorReturn(String message) {
        super(message);
    }
}
