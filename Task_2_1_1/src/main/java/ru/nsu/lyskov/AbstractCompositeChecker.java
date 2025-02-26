package ru.nsu.lyskov;

/**
 * Абстрактный класс для проверки наличия составных чисел.
 */
abstract class AbstractCompositeChecker {

    /**
     * Определяет, является ли число составным.
     *
     * @param num число для проверки (должно быть >= 1).
     * @return {@code true}, если число составное, иначе {@code false}.
     * @throws IllegalArgumentException если число меньше 1.
     */
    static boolean isComposite(int num) {
        if (num < 1) {
            throw new IllegalArgumentException("Все числа должны быть >= 1");
        }
        switch (num) {
            case 1, 2:
                return false;
            default:
                for (int i = 2; i * i <= num; i++) {
                    if (num % i == 0) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    /**
     * Проверяет, содержит ли массив хотя бы одно составное число.
     *
     * @param array массив целых чисел.
     * @return {@code true}, если в массиве есть хотя бы одно составное число, иначе {@code false}.
     */
    abstract boolean containsComposite(int[] array);
}
