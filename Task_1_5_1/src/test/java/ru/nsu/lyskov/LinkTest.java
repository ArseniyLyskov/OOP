package ru.nsu.lyskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {
    @Test
    void linkTest() {
        Link link1 = new Link("GitHub", "https://github.com");
        Link link2 = new Link("GitHub", "https://github.com");
        assertEquals(link1, link2);
        assertEquals("[GitHub](https://github.com)", link1.toString());
    }
}
