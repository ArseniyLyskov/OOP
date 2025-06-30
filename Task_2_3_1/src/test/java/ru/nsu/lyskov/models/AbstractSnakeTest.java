package ru.nsu.lyskov.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.Direction;
import ru.nsu.lyskov.models.snake.AbstractSnake;
import ru.nsu.lyskov.models.snake.InvalidMoveDirectionException;
import ru.nsu.lyskov.views.rendering.PlayerSnake;

class AbstractSnakeTest {
    private AbstractSnake snake;

    @BeforeEach
    void setup() {
        snake = new PlayerSnake(1, 1, Direction.RIGHT);
    }

    @Test
    void testMove() {
        int initialSize = snake.getSegments().size();
        snake.move();
        assertEquals(initialSize, snake.getSegments().size());
        assertEquals(2, snake.getSegments().getFirst().getX());
    }

    @Test
    void testGrow() {
        int initialSize = snake.getSegments().size();
        snake.grow();
        assertEquals(initialSize + 1, snake.getSegments().size());
    }

    @Test
    void test180DegreeTurn() {
        snake.grow();
        assertThrows(InvalidMoveDirectionException.class,
                     () -> snake.setDirection(Direction.LEFT)
        );

    }
}