package ru.nsu.lyskov.models;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.Direction;
import ru.nsu.lyskov.models.food.AbstractFood;
import ru.nsu.lyskov.models.snake.SnakeSegment;
import ru.nsu.lyskov.models.snake.SnakeSegmentType;

public class GameModelTest {
    private static final Direction testDirection = Direction.RIGHT;
    public static final SnakeSegmentType testSegmentType = SnakeSegmentType.HEAD_RIGHT;
    private GameModel model;

    @BeforeEach
    public void setUp() {
        model = new GameModel();
    }

    @Test
    public void testInitialScoreAndSnakeNotNull() {
        assertEquals(0, model.getScore());
        assertNotNull(model.getSnake());
        assertFalse(model.getSnake().getSegments().isEmpty());
    }

    @Test
    public void testResetChangesSnakePosition() {
        model.reset();
        SnakeSegment newHead = model.getSnake().getSegments().getFirst();
        assertNotNull(newHead);
    }

    @Test
    public void testIncrementScore() {
        int oldScore = model.getScore();
        model.incrementScore();
        assertEquals(oldScore + 1, model.getScore());
    }

    @Test
    public void testSelfCollisionDetection() {
        var snake = model.getSnake();

        snake.getSegments().clear();
        snake.getSegments().add(new SnakeSegment(5, 5, testDirection, testSegmentType));
        snake.getSegments().add(new SnakeSegment(5, 6, testDirection, testSegmentType));
        snake.getSegments().add(new SnakeSegment(5, 7, testDirection, testSegmentType));
        snake.getSegments().add(new SnakeSegment(5, 5, testDirection, testSegmentType));

        assertTrue(model.checkSelfCollision());
    }

    @Test
    public void testNoSelfCollision() {
        assertFalse(model.checkSelfCollision());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCheckFoodCollision() throws IllegalAccessException, NoSuchFieldException {
        model.reset();
        model.getSnake().getSegments().clear();
        model.getSnake().getSegments().add(new SnakeSegment(3, 3, testDirection, testSegmentType));
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

    @Test
    public void testRenderDoesNotThrow() {
        Canvas canvas = new Canvas(100, 100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        assertDoesNotThrow(() -> model.render(gc));
    }
}
