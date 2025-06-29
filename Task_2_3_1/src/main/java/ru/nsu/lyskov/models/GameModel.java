package ru.nsu.lyskov.models;

import static ru.nsu.lyskov.Constants.T_FOOD_ELEMENTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import ru.nsu.lyskov.Direction;
import ru.nsu.lyskov.models.snake.SnakeSegment;
import ru.nsu.lyskov.views.rendering.PlayerSnake;
import ru.nsu.lyskov.views.rendering.Renderable;
import ru.nsu.lyskov.views.rendering.StandardFood;
import ru.nsu.lyskov.views.rendering.StandardMap;

public class GameModel implements Renderable {
    private static final Random RANDOM = new Random();

    private StandardMap map;
    private PlayerSnake snake;
    private final List<StandardFood> foods = new ArrayList<>();
    private int score;

    public GameModel() {
        reset();
    }

    public void reset() {
        score = 0;
        map = new StandardMap();
        snake = new PlayerSnake(RANDOM.nextInt(map.getWidth()),
                                RANDOM.nextInt(map.getHeight()),
                                Direction.RIGHT);
        foods.clear();
        for (int i = 0; i < T_FOOD_ELEMENTS; i++) {
            addNewFood();
        }
    }

    public void update() {
        snake.move();
        checkFoodCollision();
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

    private void addNewFood() {
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

        for (StandardFood food : foods) {
            if (food.getX() == x && food.getY() == y) {
                return false;
            }
        }

        return true;
    }

    private void checkFoodCollision() {
        SnakeSegment head = snake.getSegments().getFirst();

        for (int i = 0; i < foods.size(); i++) {
            StandardFood food = foods.get(i);
            if (head.getX() == food.getX() && head.getY() == food.getY()) {
                snake.grow();
                incrementScore();
                foods.remove(i);
                addNewFood();
                break;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        map.render(gc);
        for (StandardFood food : foods) {
            food.render(gc);
        }
        snake.render(gc);
    }

}
