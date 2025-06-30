package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static ru.nsu.lyskov.Utils.randomEnum;

import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.models.snake.SnakeSegmentType;

class UtilsTest {
    @Test
    void testRandomEnum() {
        assertInstanceOf(SnakeSegmentType.class, randomEnum(SnakeSegmentType.class));
    }
}