package ru.nsu.lyskov;

import static ru.nsu.lyskov.PerformanceAnalyzer.getAvailableProcessorsCount;

import java.util.concurrent.atomic.AtomicBoolean;

class ThreadCompositeChecker extends AbstractCompositeChecker {

    @Override
    boolean containsComposite(int[] array) {
        return containsComposite(array, getAvailableProcessorsCount());
    }

    boolean containsComposite(int[] array, int numThreads) {
        int length = array.length;
        Thread[] threads = new Thread[numThreads];
        AtomicBoolean hasComposite = new AtomicBoolean(false);

        for (int i = 0; i < numThreads; i++) {
            final int start = i * length / numThreads;
            final int end = (i + 1) * length / numThreads;

            threads[i] = new Thread(() -> {
                for (int j = start; j < end && !hasComposite.get(); j++) {
                    if (isComposite(array[j])) {
                        hasComposite.set(true);
                        break;
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted exception occurred.");
            }
        }

        return hasComposite.get();
    }
}
