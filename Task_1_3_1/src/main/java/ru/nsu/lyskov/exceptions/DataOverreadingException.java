package ru.nsu.lyskov.exceptions;

public class DataOverreadingException extends RuntimeException {
    public DataOverreadingException(String message) {
        super(message);
    }
}
