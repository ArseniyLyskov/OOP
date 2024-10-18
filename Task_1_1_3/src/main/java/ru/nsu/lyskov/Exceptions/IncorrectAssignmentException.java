package ru.nsu.lyskov.Exceptions;

/**
 * Исключение, которое выбрасывается в случае некорректного присваивания переменных.
 */
public class IncorrectAssignmentException extends Exception {

    /**
     * Создаёт исключение с указанным сообщением.
     *
     * @param message сообщение, описывающее ошибку
     */
    public IncorrectAssignmentException(String message) {
        super(message);
    }
}
