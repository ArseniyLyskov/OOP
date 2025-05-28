package ru.nsu.lyskov;

/**
 * Класс, реализующий последовательную проверку массива на наличие составных чисел.
 */
class SequentialCompositeChecker extends AbstractCompositeChecker {

    /**
     * Проверяет, содержит ли массив хотя бы одно составное число.
     *
     * @param array массив целых чисел.
     * @return {@code true}, если в массиве есть хотя бы одно составное число, иначе {@code false}.
     */
    @Override
    boolean containsComposite(int[] array) {
        for (int num : array) {
            if (isComposite(num)) {
                return true;
            }
        }
        return false;
    }
}
