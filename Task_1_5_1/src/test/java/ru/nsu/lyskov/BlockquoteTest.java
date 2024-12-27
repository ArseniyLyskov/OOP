package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BlockquoteTest {
    @Test
    void blockquoteTest() {
        Blockquote blockquote = new Blockquote("This is a blockquote.");
        assertEquals("> This is a blockquote.", blockquote.toString());

        Blockquote blockquote1 = new Blockquote("Same blockquote");
        Blockquote blockquote2 = new Blockquote("Same blockquote");
        assertEquals(blockquote1, blockquote2);
    }
}
