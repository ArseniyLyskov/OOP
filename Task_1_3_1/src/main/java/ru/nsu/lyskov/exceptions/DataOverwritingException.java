package ru.nsu.lyskov.exceptions;

public class DataOverwritingException extends RuntimeException {
    public DataOverwritingException(String message) {
        super(message);
    }
}
