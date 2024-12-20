package ru.nsu.lyskov.exceptions;

public class IllegalFunctionReturnException extends RuntimeException {
    public IllegalFunctionReturnException(String message) {
        super(message);
    }
}
