package ru.nsu.lyskov.interfaces;

@FunctionalInterface
public interface FileBufferProcessor {
    int processBuffer(BufferInterface<Character> buffer);
}
