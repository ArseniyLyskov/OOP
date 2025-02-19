package ru.nsu.lyskov;

import java.util.Arrays;

/**
 * Класс, реализующий проверку массива на наличие составных чисел с использованием stream API.
 */
class StreamCompositeChecker extends AbstractCompositeChecker {

    /**
     * Проверяет, содержит ли массив хотя бы одно составное число, используя stream API.
     *
     * @param array массив целых чисел.
     * @return {@code true}, если в массиве есть хотя бы одно составное число, иначе {@code false}.
     */
    @Override
    boolean containsComposite(int[] array) {
        return Arrays.stream(array).parallel().anyMatch(AbstractCompositeChecker::isComposite);
    }
}
