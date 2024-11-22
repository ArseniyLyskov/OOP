package ru.nsu.lyskov.exceptions;

/**
 * Исключение, которое выбрасывается в случае некорректного формата математического выражения.
 */
public class IncorrectExpressionException extends Exception {

    /**
     * Создаёт исключение с указанным сообщением.
     *
     * @param message сообщение, описывающее ошибку
     */
    public IncorrectExpressionException(String message) {
        super(message);
    }
}
