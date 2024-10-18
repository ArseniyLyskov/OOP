package ru.nsu.lyskov.exceptions;

/**
 * Исключение, которое выбрасывается при попытке деления на ноль.
 */
public class DivisionByZeroException extends Exception {

    /**
     * Создаёт исключение с указанным сообщением.
     *
     * @param message сообщение, описывающее ошибку
     */
    public DivisionByZeroException(String message) {
        super(message);
    }
}
