package ru.nsu.lyskov.exceptions;

/**
 * Исключение, которое выбрасывается, если функция, обрабатывающая буфер, вернула меньше единицы,
 * либо больше размера буфера. (Возвращаемое значение отображает количество обработанных функцией
 * элементов).
 */
public class IllegalFunctionReturnException extends RuntimeException {
    /**
     * Создаёт исключение с указанным сообщением.
     *
     * @param message сообщение, описывающее ошибку.
     */
    public IllegalFunctionReturnException(String message) {
        super(message);
    }
}
