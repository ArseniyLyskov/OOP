package ru.nsu.lyskov.exceptions;

/**
 * Исключение, которое выбрасывается при попытке чтения из кольцевого буфера, когда непрочитанных
 * элементов не осталось. (Какие-то элементы считываются повторно).
 */
public class DataOverreadingException extends RuntimeException {
    /**
     * Создаёт исключение с указанным сообщением.
     *
     * @param message сообщение, описывающее ошибку.
     */
    public DataOverreadingException(String message) {
        super(message);
    }
}
