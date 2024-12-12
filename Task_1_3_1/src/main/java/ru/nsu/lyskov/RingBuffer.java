package ru.nsu.lyskov;

import ru.nsu.lyskov.exceptions.DataOverreadingException;
import ru.nsu.lyskov.exceptions.DataOverwritingException;
import ru.nsu.lyskov.exceptions.InvalidCapacityException;

public class RingBuffer<T> {
    private final T[] buffer;
    private final int capacity;
    private final Pointer readP;
    private final Pointer writeP;
    private int unreadElementsCount = 0;

    @SuppressWarnings("unchecked")
    public RingBuffer(int capacity) throws InvalidCapacityException {
        if (capacity <= 0) {
            throw new InvalidCapacityException("Capacity must be greater than 0.");
        }
        this.capacity = capacity;
        readP = new Pointer();
        writeP = new Pointer();
        this.buffer = (T[]) new Object[capacity];
    }

    public void write(T element) throws DataOverwritingException {
        if (isFull()) {
            throw new DataOverwritingException(
                    "The write element pointer has reached the read pointer. "
                            + "Overwriting unread elements?");
        }
        buffer[writeP.index] = element;
        writeP.increment();
        unreadElementsCount++;
    }

    public T read() throws DataOverreadingException {
        if (isEmpty()) {
            throw new DataOverreadingException(
                    "The read element pointer has reached the write pointer. "
                            + "Reading elements again?");
        }
        T element = buffer[readP.index];
        readP.increment();
        unreadElementsCount--;
        return element;
    }

    public T peekAtIndex(int index) {
        if (index >= unreadElementsCount) {
            throw new IndexOutOfBoundsException(
                    "The index being accessed is greater than the number of unread items.");
        }
        int peekingIndex = readP.index + index < capacity ?
                readP.index + index :
                readP.index + index - capacity;
        return buffer[peekingIndex];
    }

    public boolean isFull() {
        return unreadElementsCount == capacity;
    }

    public boolean isEmpty() {
        return unreadElementsCount == 0;
    }

    public int getUnreadElementsCount() {
        return unreadElementsCount;
    }

    public static void main(String[] args)
            throws InvalidCapacityException, DataOverreadingException, DataOverwritingException {
        RingBuffer<Integer> ringBuffer = new RingBuffer<>(3);

        ringBuffer.write(1);
        ringBuffer.write(2);
        ringBuffer.write(1);
        //ringBuffer.write(3);

        System.out.println("Peeked: " + ringBuffer.peekAtIndex(1));
        System.out.println("Read: " + ringBuffer.read()); // 1
        System.out.println("Peeked: " + ringBuffer.peekAtIndex(1));
        ringBuffer.write(4);

        while (!ringBuffer.isEmpty()) {
            System.out.println("Read: " + ringBuffer.read());
        }
    }

    private class Pointer {
        int index = 0;

        void increment() {
            index++;
            index = index == capacity ? 0 : index;
        }
    }
}
