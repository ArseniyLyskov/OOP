package ru.nsu.lyskov.models;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.nsu.lyskov.Direction.RIGHT;
import static ru.nsu.lyskov.models.snake.SnakeSegmentType.HEAD_RIGHT;

import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.Direction;
import ru.nsu.lyskov.models.food.AbstractFood;
import ru.nsu.lyskov.models.snake.SnakeSegment;
import ru.nsu.lyskov.models.snake.SnakeSegmentType;

/**
 * Тестовый класс для {@link GameModel}. Проверяет базовую функциональность игровой модели.
 */
public class GameModelTest {
    private static final Direction TEST_DIRECTION = RIGHT;
    private static final SnakeSegmentType TEST_SEGMENT_TYPE = HEAD_RIGHT;

    private GameModel model;

    /**
     * Инициализация тестового окружения перед каждым тестом.
     */
    @BeforeEach
    public void setUp() {
        model = new GameModel();
    }

    /**
     * Проверяет начальное состояние модели.
     */
    @Test
    public void testInitialScoreAndSnakeNotNull() {
        assertEquals(0, model.getScore());
        assertNotNull(model.getSnake());
        assertFalse(model.getSnake().getSegments().isEmpty());
    }

    /**
     * Проверяет метод reset().
     */
    @Test
    public void testResetChangesSnakePosition() {
        model.reset();
        SnakeSegment newHead = model.getSnake().getSegments().getFirst();
        assertNotNull(newHead);
    }

    /**
     * Проверяет метод incrementScore().
     */
    @Test
    public void testIncrementScore() {
        int oldScore = model.getScore();
        model.incrementScore();
        assertEquals(oldScore + 1, model.getScore());
    }

    /**
     * Проверяет обнаружение столкновения змейки с самой собой.
     */
    @Test
    public void testSelfCollisionDetection() {
        var snake = model.getSnake();

        snake.getSegments().clear();
        snake.getSegments().add(new SnakeSegment(5, 5, TEST_DIRECTION, TEST_SEGMENT_TYPE));
        snake.getSegments().add(new SnakeSegment(5, 6, TEST_DIRECTION, TEST_SEGMENT_TYPE));
        snake.getSegments().add(new SnakeSegment(5, 7, TEST_DIRECTION, TEST_SEGMENT_TYPE));
        snake.getSegments().add(new SnakeSegment(5, 5, TEST_DIRECTION, TEST_SEGMENT_TYPE));

        assertTrue(model.checkSelfCollision());
    }

    /**
     * Проверяет отсутствие столкновения змейки с самой собой в начальном состоянии.
     */
    @Test
    public void testNoSelfCollision() {
        assertFalse(model.checkSelfCollision());
    }

    /**
     * Проверяет обнаружение столкновения с едой.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCheckFoodCollision() throws NoSuchFieldException, IllegalAccessException {
        model.reset();
        model.getSnake().getSegments().clear();
        model.getSnake().getSegments().add(
                new SnakeSegment(3, 3, TEST_DIRECTION, TEST_SEGMENT_TYPE));
        model.getSnake().grow();

        var food = new ru.nsu.lyskov.views.rendering.StandardFood(3, 3);
        var foodList = model.getClass().getDeclaredField("foods");
        foodList.setAccessible(true);
        List<AbstractFood> foods = (List<AbstractFood>) foodList.get(model);
        foods.clear();
        foods.add(food);

        int scoreBefore = model.getScore();
        boolean collided = model.checkFoodCollision();

        assertTrue(collided);
        assertEquals(scoreBefore + 1, model.getScore());
    }

    /**
     * Проверяет, что метод render() не выбрасывает исключений.
     */
    @Test
    public void testRenderDoesNotThrow() {
        Canvas canvas = new Canvas(100, 100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        assertDoesNotThrow(() -> model.render(gc));
    }
}