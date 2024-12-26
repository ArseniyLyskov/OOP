package ru.nsu.lyskov.logic;

import ru.nsu.lyskov.exceptions.DataOverreadingException;
import ru.nsu.lyskov.exceptions.DataOverwritingException;
import ru.nsu.lyskov.interfaces.BufferInterface;

public class RingBuffer<T> implements BufferInterface<T> {
    private final T[] buffer;
    private final int capacity;
    private final Pointer readP;
    private final Pointer writeP;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public RingBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Buffer capacity must be greater than 0.");
        }
        this.capacity = capacity;
        readP = new Pointer();
        writeP = new Pointer();
        this.buffer = (T[]) new Object[capacity];
    }

    public void put(T element) {
        if (isFull()) {
            throw new DataOverwritingException(
                    "The write element pointer has reached the read pointer. "
                            + "Overwriting unread elements?");
        }
        buffer[writeP.index] = element;
        writeP.increment();
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new DataOverreadingException(
                    "The read element pointer has reached the write pointer. "
                            + "Reading elements again?");
        }
        T element = buffer[readP.index];
        readP.increment();
        size--;
        return element;
    }

    @Override
    public T peek(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(
                    "The index being accessed is greater than the number of unread items.");
        }
        int peekingIndex = readP.index + index < capacity ?
                readP.index + index :
                readP.index + index - capacity;
        return buffer[peekingIndex];
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    private class Pointer {
        int index = 0;

        void increment() {
            index++;
            index = index == capacity ? 0 : index;
        }
    }
}
