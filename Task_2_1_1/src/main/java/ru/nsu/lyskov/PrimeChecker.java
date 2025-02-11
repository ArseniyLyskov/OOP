package ru.nsu.lyskov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrimeChecker {

    private static boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    public static boolean hasNonPrimeSequential(int[] array) {
        for (int num : array) {
            if (!isPrime(num)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasNonPrimeParallelThreads(int[] array, int numThreads)
            throws InterruptedException {
        int length = array.length;
        Thread[] threads = new Thread[numThreads];
        AtomicBoolean hasNonPrime = new AtomicBoolean(false);

        for (int i = 0; i < numThreads; i++) {
            final int start = i * length / numThreads;
            final int end = (i + 1) * length / numThreads;

            threads[i] = new Thread(() -> {
                for (int j = start; j < end && !hasNonPrime.get(); j++) {
                    if (!isPrime(array[j])) {
                        hasNonPrime.set(true);
                        break;
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return hasNonPrime.get();
    }

    public static boolean hasNonPrimeParallelStream(int[] array) {
        return Arrays.stream(array).parallel().anyMatch(num -> !isPrime(num));
    }

    public static void main(String[] args) throws InterruptedException {
        int[] testArray = generatePrimes(1000000);

        long start, end;

        start = System.nanoTime();
        boolean result1 = hasNonPrimeSequential(testArray);
        end = System.nanoTime();
        System.out.println("Sequential: " + (end - start) / 1e6 + " ms");

        for (int threads = 1; threads <= 8; threads++) {
            start = System.nanoTime();
            boolean result2 = hasNonPrimeParallelThreads(testArray, threads);
            end = System.nanoTime();
            System.out.println("Threads " + threads + ": " + (end - start) / 1e6 + " ms");
        }

        start = System.nanoTime();
        boolean result3 = hasNonPrimeParallelStream(testArray);
        end = System.nanoTime();
        System.out.println("ParallelStream: " + (end - start) / 1e6 + " ms");
    }

    private static int[] generatePrimes(int size) {
        List<Integer> primes = new ArrayList<>();
        int num = 2;
        while (primes.size() < size) {
            if (isPrime(num)) {
                primes.add(num);
            }
            num++;
        }
        return primes.stream().mapToInt(i -> i).toArray();
    }
}
