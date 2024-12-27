package ru.nsu.lyskov.interfaces;

/**
 * При чтении файла с {@code TextFileReader}, данные из него поступают в буфер. Интерфейс позволяет
 * передавать лямбда-функцию в {@code TextFileReader}, которая определяет, что делать с информацией
 * в файле.
 */
@FunctionalInterface
public interface FileBufferProcessor {
    /**
     * Функция-обработчик буфера.
     *
     * @param buffer буфер, данные из которого подвергаются обработке.
     * @return количество обработанных символов.
     */
    int processBuffer(BufferInterface<Character> buffer);
}
