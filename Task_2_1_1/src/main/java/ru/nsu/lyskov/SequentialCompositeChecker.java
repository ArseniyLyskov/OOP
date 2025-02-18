package ru.nsu.lyskov;

class SequentialCompositeChecker extends AbstractCompositeChecker {
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
