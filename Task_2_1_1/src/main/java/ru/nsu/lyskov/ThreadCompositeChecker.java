package ru.nsu.lyskov;

import static ru.nsu.lyskov.PerformanceAnalyzer.getAvailableProcessorsCount;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс, реализующий многопоточный поиск составных чисел в массиве.
 */
class ThreadCompositeChecker extends AbstractCompositeChecker {

    /**
     * Проверяет, содержит ли массив хотя бы одно составное число, используя количество потоков,
     * соответствующее числу доступных процессоров.
     *
     * @param array массив целых чисел.
     * @return {@code true}, если в массиве есть хотя бы одно составное число, иначе {@code false}.
     */
    @Override
    boolean containsComposite(int[] array) {
        return containsComposite(array, getAvailableProcessorsCount());
    }

    /**
     * Проверяет, содержит ли массив хотя бы одно составное число, используя заданное количество
     * потоков.
     *
     * @param array      массив целых чисел.
     * @param numThreads количество потоков для обработки массива.
     * @return {@code true}, если в массиве есть хотя бы одно составное число, иначе {@code false}.
     */
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
                throw new RuntimeException("Произошло прерывание потока.");
            }
        }

        return hasComposite.get();
    }
}
