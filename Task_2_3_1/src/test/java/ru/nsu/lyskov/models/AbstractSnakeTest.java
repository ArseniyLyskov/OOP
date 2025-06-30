package ru.nsu.lyskov.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.nsu.lyskov.Direction.LEFT;
import static ru.nsu.lyskov.Direction.RIGHT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.models.snake.AbstractSnake;
import ru.nsu.lyskov.models.snake.InvalidMoveDirectionException;
import ru.nsu.lyskov.views.rendering.PlayerSnake;

/**
 * Тестовый класс для {@link AbstractSnake}. Проверяет базовую функциональность абстрактной
 * змейки.
 */
class AbstractSnakeTest {
    private AbstractSnake snake;

    /**
     * Подготовка тестового окружения перед каждым тестом.
     */
    @BeforeEach
    void setup() {
        snake = new PlayerSnake(1, 1, RIGHT);
    }

    /**
     * Тестирует метод {@link AbstractSnake#move()}.
     */
    @Test
    void testMove() {
        int initialSize = snake.getSegments().size();
        snake.move();
        assertEquals(initialSize, snake.getSegments().size());
        assertEquals(2, snake.getSegments().getFirst().getX());
    }

    /**
     * Тестирует метод {@link AbstractSnake#grow()}.
     */
    @Test
    void testGrow() {
        int initialSize = snake.getSegments().size();
        snake.grow();
        assertEquals(initialSize + 1, snake.getSegments().size());
    }

    /**
     * Тестирует невозможность разворота на 180 градусов (при змейке длины >= 2). Проверяет что
     * попытка установить противоположное направление (LEFT при текущем RIGHT) вызывает исключение
     * InvalidMoveDirectionException.
     */
    @Test
    void test180DegreeTurn() {
        snake.grow(); // Делаем змейку длиннее 1 сегмента
        assertThrows(InvalidMoveDirectionException.class,
                     () -> snake.setDirection(LEFT)
        );
    }
}