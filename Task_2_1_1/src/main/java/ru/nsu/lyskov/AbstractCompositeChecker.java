package ru.nsu.lyskov;

abstract class AbstractCompositeChecker {
    static boolean isComposite(int num) {
        if (num < 1) {
            throw new IllegalArgumentException("All numbers must be >= 1");
        }
        switch (num) {
            case 1, 2:
                return false;
            default:
                for (int i = 2; i * i <= num; i++) {
                    if (num % i == 0) return true;
                }
                break;
        }
        return false;
    }

    abstract boolean containsComposite(int[] array);
}
