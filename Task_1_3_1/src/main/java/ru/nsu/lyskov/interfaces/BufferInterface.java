package ru.nsu.lyskov.interfaces;

public interface BufferInterface<T> {
    T peek(int index);

    boolean isFull();

    boolean isEmpty();

    int getSize();
}
