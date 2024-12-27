package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TextTest {
    @Test
    void testText() {
        Element plainText = new Text("Plain Text");
        Element boldText = new Text.Bold("Bold Text");
        Element italicText = new Text.Italic("Italic Text");
        Element strikeText = new Text.StrikeThrough("Strikethrough Text");
        Element codeText = new Text.Code("Code Text");

        assertEquals("Plain Text", plainText.toString());
        assertEquals("**Bold Text**", boldText.toString());
        assertEquals("*Italic Text*", italicText.toString());
        assertEquals("~~Strikethrough Text~~", strikeText.toString());
        assertEquals("`Code Text`", codeText.toString());
    }
}
