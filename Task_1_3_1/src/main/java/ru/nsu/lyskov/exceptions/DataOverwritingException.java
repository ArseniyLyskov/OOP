package ru.nsu.lyskov.exceptions;

/**
 * Исключение, которое выбрасывается при попытке записи в кольцевой буфер, когда количество
 * непрочитанных в нём элементов равно его размеру. (Какие-то элементы остаются необработанными).
 */
public class DataOverwritingException extends RuntimeException {
    /**
     * Создаёт исключение с указанным сообщением.
     *
     * @param message сообщение, описывающее ошибку.
     */
    public DataOverwritingException(String message) {
        super(message);
    }
}
