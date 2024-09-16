package ru.nsu.lyskov;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HeapTest {


    @Test
    void heapsort() {
        int[] array;
        array = new int[]{6, -4, 12, 8, 12, -69, 8, 0};
        assertArrayEquals(getSortedCopy(array), Heap.heapsort(array));
        array = new int[]{1};
        assertArrayEquals(getSortedCopy(array), Heap.heapsort(array));
        array = new int[]{};
        assertArrayEquals(getSortedCopy(array), Heap.heapsort(array));
        array = new int[]{1, -1_000_000, Integer.MAX_VALUE, 0, 5, Integer.MIN_VALUE, 4};
        assertArrayEquals(getSortedCopy(array), Heap.heapsort(array));
    }

    @Test
    void getArray() {
        int[] array = new int[]{6, -4, 12, 8, 12, -69, 8, 0};
        Heap h = new Heap(array);
        assertArrayEquals(array, h.getArray());
    }

    private int[] getSortedCopy(int[] array) {
        int[] copy = Arrays.copyOf(array, array.length);
        Arrays.sort(copy);
        return copy;
    }
}