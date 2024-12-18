package ru.nsu.lyskov.exceptions;

public class InvalidCapacityException extends RuntimeException {
    public InvalidCapacityException(String message) {
        super(message);
    }
}
