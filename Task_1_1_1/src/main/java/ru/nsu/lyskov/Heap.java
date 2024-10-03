package ru.nsu.lyskov;

/**
 * Класс "Куча", экземпляр которого можно отсортировать по классическому алгоритму
 * пирамидальной сортировки (сортировка кучей).
 */
public class Heap {
    private final int[] array;
    private final int length;

    /**
     * Конструктор класса "Куча", инициализирует final int[] array и его размер.
     * Конструктор приватный, так как в условии задачи массив сортируется через
     * статичный метод, а не экземпляр класса.
     *
     * @param array Массив, на основе которого создаётся куча.
     */
    private Heap(int[] array) {
        this.array = array;
        length = array.length;
    }

    /**
     * Статичный метод класса, возвращающий отсортированный пирамидальной сортировкой
     * переданный в качестве аргумента массив. (В условии задачи на вход подаётся
     * "heapsort(new int[] {5, 4, 3, 2, 1});", поэтому сортировка реализована так).
     *
     * @param array Массив, который необходимо отсортировать.
     * @return Отсортированный массив.
     */
    public static int[] heapsort(int[] array) {
        Heap heap = new Heap(array);
        heap.sort();

        return heap.array;
    }

    /**
     * Процедура, приводящая часть массива к виду кучи.
     *
     * @param unsorted   Число элементов массива, начиная с первого, которые необходимо
     *                   привести к виду кучи. (В процессе сортировки элементы в конце
     *                   располагаются в нужном порядке и сортировать их не надо).
     * @param rootedNode Индекс элемента, который будет являться корнем бинарного
     *                   дерева (кучи).
     */
    private void heapify(int unsorted, int rootedNode) {
        int largest = rootedNode;
        int left = rootedNode * 2 + 1;
        int right = rootedNode * 2 + 2;

        if (left < unsorted && array[left] > array[largest]) {
            largest = left;
        }
        if (right < unsorted && array[right] > array[largest]) {
            largest = right;
        }

        if (largest != rootedNode) {
            int temp = array[rootedNode];
            array[rootedNode] = array[largest];
            array[largest] = temp;
            heapify(unsorted, largest);
        }
    }

    /**
     * Классический алгоритм бинарной сортировки. Корень кучи (максимальный элемент)
     * перемещается в конец неотсортированной части массива, оставшаяся часть снова
     * приводится к виду кучи. Процесс повторяется, пока не будет отсортирован
     * последний элемент в куче.
     */
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
}
