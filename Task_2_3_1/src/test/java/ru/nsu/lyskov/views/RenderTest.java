package ru.nsu.lyskov.views;

import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nsu.lyskov.Direction;
import ru.nsu.lyskov.Utils;
import ru.nsu.lyskov.views.rendering.PlayerSnake;
import ru.nsu.lyskov.views.rendering.StandardFood;
import ru.nsu.lyskov.views.rendering.StandardMap;

/**
 * Тестовый класс для проверки корректности отрисовки игровых объектов. Проверяет базовую
 * функциональность рендеринга с использованием mock-объектов.
 */
class RenderTest {

    /**
     * Тестирует отрисовку игровых объектов (карты, еды и змейки).
     */
    @Test
    void testRenderSegment() {
        Random random = new Random();
        GraphicsContext gc = Mockito.mock(GraphicsContext.class);
        PlayerSnake snake = new PlayerSnake(2, 2, Direction.RIGHT);
        StandardFood food = new StandardFood(1, 1);
        StandardMap map = new StandardMap();

        map.render(gc);
        food.render(gc);

        // Имитация 30 шагов движения змейки со случайными поворотами и ростом
        for (int i = 0; i < 30; i++) {
            snake.move();
            if (random.nextBoolean()) {
                snake.grow();
            }
            Direction newDirection = Utils.randomEnum(Direction.class);
            if (snake.canTurn(newDirection)) {
                snake.setDirection(newDirection);
            }
            snake.render(gc);
        }

        Mockito.verify(gc, Mockito.atLeastOnce()).fillOval(
                Mockito.anyDouble(), Mockito.anyDouble(),
                Mockito.anyDouble(), Mockito.anyDouble()
        );
    }
}