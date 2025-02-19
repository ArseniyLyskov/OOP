package ru.nsu.lyskov;

/**
 * Утилитарный класс для проверки наличия составных чисел в массиве различными методами.
 */
public class CompositeChecker {

    /**
     * Проверяет, содержит ли массив хотя бы одно составное число с использованием
     * последовательного алгоритма.
     *
     * @param array массив целых чисел.
     * @return {@code true}, если массив содержит хотя бы одно составное число, иначе
     * {@code false}.
     */
    public static boolean sequentialCompositeCheck(int[] array) {
        AbstractCompositeChecker checker = new SequentialCompositeChecker();
        return checker.containsComposite(array);
    }

    /**
     * Проверяет, содержит ли массив хотя бы одно составное число с использованием многопоточного
     * алгоритма.
     *
     * @param array массив целых чисел.
     * @return {@code true}, если массив содержит хотя бы одно составное число, иначе
     * {@code false}.
     */
    public static boolean threadCompositeCheck(int[] array) {
        AbstractCompositeChecker checker = new ThreadCompositeChecker();
        return checker.containsComposite(array);
    }

    /**
     * Проверяет, содержит ли массив хотя бы одно составное число с использованием многопоточного
     * алгоритма с заданным количеством потоков.
     *
     * @param array      массив целых чисел.
     * @param numThreads количество потоков для обработки массива.
     * @return {@code true}, если массив содержит хотя бы одно составное число, иначе
     * {@code false}.
     */
    public static boolean threadCompositeCheck(int[] array, int numThreads) {
        ThreadCompositeChecker checker = new ThreadCompositeChecker();
        return checker.containsComposite(array, numThreads);
    }

    /**
     * Проверяет, содержит ли массив хотя бы одно составное число с использованием stream API.
     *
     * @param array массив целых чисел.
     * @return {@code true}, если массив содержит хотя бы одно составное число, иначе
     * {@code false}.
     */
    public static boolean streamCompositeCheck(int[] array) {
        AbstractCompositeChecker checker = new StreamCompositeChecker();
        return checker.containsComposite(array);
    }
}
