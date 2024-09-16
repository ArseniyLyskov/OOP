package ru.nsu.lyskov;

public class Heap {
    private final int[] array;
    private final int length;

    public Heap(int[] array) {
        this.array = array;
        length = array.length;
    }

    public static int[] heapsort(int[] array) {
        Heap heap = new Heap(array);
        heap.sort();

        return heap.getArray();
    }

    private void heapify(int unsorted, int rootedNode) {
        int largest = rootedNode;
        int left = rootedNode * 2 + 1;
        int right = rootedNode * 2 + 2;

        if (left < unsorted && array[left] > array[largest])
            largest = left;
        if (right < unsorted && array[right] > array[largest])
            largest = right;

        if (largest != rootedNode) {
            int temp = array[rootedNode];
            array[rootedNode] = array[largest];
            array[largest] = temp;
            heapify(unsorted, largest);
        }
    }

    private void sort() {
        for (int i = length / 2 - 1; i >= 0; i--)
            heapify(length, i);

        for (int i = length - 1; i >= 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(i, 0);
        }
    }

    public int[] getArray() {
        return array;
    }
}
