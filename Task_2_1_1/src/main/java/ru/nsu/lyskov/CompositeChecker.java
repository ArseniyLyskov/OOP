package ru.nsu.lyskov;

public class CompositeChecker {

    public static boolean sequentialCompositeCheck(int[] array) {
        AbstractCompositeChecker checker = new SequentialCompositeChecker();
        return checker.containsComposite(array);
    }

    public static boolean threadCompositeCheck(int[] array) {
        AbstractCompositeChecker checker = new ThreadCompositeChecker();
        return checker.containsComposite(array);
    }

    public static boolean threadCompositeCheck(int[] array, int numThreads) {
        ThreadCompositeChecker checker = new ThreadCompositeChecker();
        return checker.containsComposite(array, numThreads);
    }

    public static boolean streamCompositeCheck(int[] array) {
        AbstractCompositeChecker checker = new StreamCompositeChecker();
        return checker.containsComposite(array);
    }
}
