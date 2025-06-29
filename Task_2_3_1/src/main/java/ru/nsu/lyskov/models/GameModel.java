package ru.nsu.lyskov.models;

import static ru.nsu.lyskov.Constants.T_FOOD_ELEMENTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import ru.nsu.lyskov.Direction;
import ru.nsu.lyskov.Utils;
import ru.nsu.lyskov.models.food.AbstractFood;
import ru.nsu.lyskov.models.snake.SnakeSegment;
import ru.nsu.lyskov.views.rendering.PlayerSnake;
import ru.nsu.lyskov.views.rendering.Renderable;
import ru.nsu.lyskov.views.rendering.StandardFood;
import ru.nsu.lyskov.views.rendering.StandardMap;

public class GameModel implements Renderable {
    private static final Random RANDOM = new Random();

    private StandardMap map;
    private PlayerSnake snake;
    private final List<AbstractFood> foods = new ArrayList<>();
    private int score;

    public GameModel() {
        reset();
    }

    public void reset() {
        score = 0;
        map = new StandardMap();
        snake = new PlayerSnake(RANDOM.nextInt(map.getWidth()),
                                RANDOM.nextInt(map.getHeight()),
                                Utils.randomEnum(Direction.class)
        );
        foods.clear();
        for (int i = 0; i < T_FOOD_ELEMENTS; i++) {
            addStandardFood();
        }
    }

    public PlayerSnake getSnake() {
        return snake;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    private void addStandardFood() {
        int x, y;
        do {
            x = RANDOM.nextInt(map.getWidth());
            y = RANDOM.nextInt(map.getHeight());
        } while (!isPositionValid(x, y));

        foods.add(new StandardFood(x, y));
    }

    private boolean isPositionValid(int x, int y) {
        for (SnakeSegment segment : snake.getSegments()) {
            if (segment.getX() == x && segment.getY() == y) {
                return false;
            }
        }

        for (AbstractFood food : foods) {
            if (food.getX() == x && food.getY() == y) {
                return false;
            }
        }

        return true;
    }

    public boolean checkFoodCollision() {
        SnakeSegment head = snake.getSegments().getFirst();
        List<AbstractFood> foodsToRemove = new ArrayList<>();
        boolean collision = false;
        boolean shouldAddStandardFood = false;

        for (AbstractFood food : foods) {
            if (head.getX() == food.getX() && head.getY() == food.getY()) {
                collision = true;
                if (food instanceof StandardFood) {
                    snake.grow();
                    incrementScore();
                    foodsToRemove.add(food);
                    shouldAddStandardFood = true;
                }
            }
        }

        foods.removeAll(foodsToRemove);
        if (shouldAddStandardFood) {
            addStandardFood();
        }

        return collision;
    }

    public boolean checkSelfCollision() {
        SnakeSegment head = snake.getSegments().getFirst();
        for (int i = 1; i < snake.getSegments().size(); i++) {
            SnakeSegment segment = snake.getSegments().get(i);
            if (head.getX() == segment.getX() && head.getY() == segment.getY()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(GraphicsContext gc) {
        map.render(gc);
        for (AbstractFood food : foods) {
            food.render(gc);
        }
        snake.render(gc);
    }
}
