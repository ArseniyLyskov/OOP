package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static ru.nsu.lyskov.Utils.randomEnum;

import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.models.snake.SnakeSegmentType;

/**
 * Тестовый класс для проверки утилитарных методов. Содержит тесты для методов класса
 * {@link Utils}.
 */
class UtilsTest {

    /**
     * Тестирует метод {@link Utils#randomEnum(Class)}. Проверяет, что возвращаемое значение
     * является элементом перечисления SnakeSegmentType.
     */
    @Test
    void testRandomEnum() {
        assertInstanceOf(SnakeSegmentType.class, randomEnum(SnakeSegmentType.class));
    }
}