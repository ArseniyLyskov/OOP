package ru.nsu.lyskov;

/**
 * Исключение, которое выбрасывается в случае, если в процессе топологической сортировки в графе
 * обнаружен цикл.
 */
public class GraphCycleException extends Exception {

    /**
     * Создаёт исключение с указанным сообщением.
     *
     * @param message сообщение, описывающее ошибку
     */
    public GraphCycleException(String message) {
        super(message);
    }
}
