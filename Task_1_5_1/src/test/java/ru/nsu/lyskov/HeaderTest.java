package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class HeaderTest {
    @Test
    void headerTest() {
        Header header = new Header("Header", 3);
        assertEquals("### Header", header.toString());

        assertThrows(IllegalArgumentException.class, () -> new Header("Invalid", 0));
        assertThrows(IllegalArgumentException.class, () -> new Header("Invalid", 7));

        Header header1 = new Header("Test Header", 2);
        Header header2 = new Header("Test Header", 2);
        assertEquals(header1, header2);
    }
}
