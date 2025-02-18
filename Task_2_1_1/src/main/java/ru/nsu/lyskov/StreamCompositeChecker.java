package ru.nsu.lyskov;

import java.util.Arrays;

class StreamCompositeChecker extends AbstractCompositeChecker {
    @Override
    boolean containsComposite(int[] array) {
        return Arrays.stream(array).parallel().anyMatch(AbstractCompositeChecker::isComposite);
    }
}
