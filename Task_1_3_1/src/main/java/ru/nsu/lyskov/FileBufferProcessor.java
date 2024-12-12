package ru.nsu.lyskov;

@FunctionalInterface
public interface FileBufferProcessor {
    int processBuffer(RingBuffer<Character> buffer, boolean endOfData);
}
