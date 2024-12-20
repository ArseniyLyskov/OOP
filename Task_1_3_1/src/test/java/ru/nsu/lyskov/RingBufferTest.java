package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.classes.RingBuffer;
import ru.nsu.lyskov.exceptions.DataOverreadingException;
import ru.nsu.lyskov.exceptions.DataOverwritingException;

class RingBufferTest {
    @Test
    void invalidCapacityExceptionTest() {
        assertDoesNotThrow(() -> new RingBuffer<>(1));
        assertThrows(IllegalArgumentException.class, () -> new RingBuffer<>(0));
    }

    @Test
    void dataOverwritingExceptionTest() {
        RingBuffer<Integer> rb = new RingBuffer<>(1);
        rb.put(1);
        assertThrows(DataOverwritingException.class, () -> rb.put(2));
    }

    @Test
    void dataOverreadingExceptionTest() {
        assertThrows(DataOverreadingException.class, () -> {
            RingBuffer<Integer> rb = new RingBuffer<>(1);
            rb.pop();
        });

        assertThrows(DataOverreadingException.class, () -> {
            RingBuffer<Integer> rb = new RingBuffer<>(2);
            rb.put(1);
            rb.pop();
            rb.pop();
        });
    }

    @Test
    void workTest() {
        int bufferCapacity = 3;
        RingBuffer<Integer> rb = new RingBuffer<>(bufferCapacity);

        assertEquals(rb.getSize(), 0);
        assertTrue(rb.isEmpty());

        rb.put(1);
        rb.put(2);
        assertEquals(rb.getSize(), 2);
        rb.put(3);

        assertTrue(rb.isFull());
        assertThrows(DataOverwritingException.class, () -> rb.put(4));

        assertEquals(2, rb.peek(1));
        assertEquals(1, rb.pop());
        assertEquals(2, rb.getSize());
        assertEquals(bufferCapacity, rb.getCapacity());
        assertEquals(3, rb.peek(1));

        assertThrows(IndexOutOfBoundsException.class, () -> rb.peek(3));
        rb.put(4);
        assertEquals(4, rb.peek(2));

        for (int i = 2; i <= 4; i++) {
            assertEquals(i, rb.pop());
        }
        assertThrows(DataOverreadingException.class, rb::pop);
    }
}