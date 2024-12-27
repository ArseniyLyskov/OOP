package ru.nsu.lyskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageTest {
    @Test
    void testImage() {
        Image image1 = new Image("Image", "https://url.url");
        Image image2 = new Image("Image", "https://url.url");
        assertEquals(image1, image2);
        assertEquals("![Image](https://url.url)", image1.toString());
    }
}
