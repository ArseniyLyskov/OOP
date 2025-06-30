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

/**
 * Класс, представляющий игровую модель "Змейки". Содержит логику игры, включая змейку, еду,
 * подсчет очков и обработку столкновений. Реализует интерфейс Renderable для отрисовки игрового
 * состояния.
 */
public class GameModel implements Renderable {
    private static final Random RANDOM = new Random();

    private StandardMap map;
    private PlayerSnake snake;
    private final List<AbstractFood> foods = new ArrayList<>();
    private int score;

    /**
     * Конструктор игровой модели.
     */
    public GameModel() {
        reset();
    }

    /**
     * Сбрасывает состояние игры к начальному.
     */
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

    /**
     * Возвращает объект змейки.
     *
     * @return текущая змейка
     */
    public PlayerSnake getSnake() {
        return snake;
    }

    /**
     * Возвращает текущий счёт игры.
     *
     * @return количество очков
     */
    public int getScore() {
        return score;
    }

    /**
     * Увеличивает счёт игры на 1.
     */
    public void incrementScore() {
        score++;
    }

    /**
     * Добавляет стандартную еду на карту в случайную незанятую позицию.
     */
    private void addStandardFood() {
        int x, y;
        do {
            x = RANDOM.nextInt(map.getWidth());
            y = RANDOM.nextInt(map.getHeight());
        } while (!isPositionValid(x, y));

        foods.add(new StandardFood(x, y));
    }

    /**
     * Проверяет, является ли позиция (x, y) незанятой для размещения еды.
     *
     * @param x абсцисса
     * @param y ордината
     * @return {@code true} если позиция свободна, {@code false} если занята
     */
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

    /**
     * Проверяет столкновение головы змейки с едой. При столкновении со стандартной едой
     * увеличивает змейку, счет и заменяет съеденную еду.
     *
     * @return true если произошло столкновение с едой, false в противном случае
     */
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

    /**
     * Проверяет столкновение головы змейки с ее телом.
     *
     * @return true если произошло столкновение с телом, false в противном случае
     */
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

    /**
     * Отрисовывает текущее состояние игры на графическом контексте.
     *
     * @param gc графический контекст для отрисовки
     */
    @Override
    public void render(GraphicsContext gc) {
        map.render(gc);
        for (AbstractFood food : foods) {
            food.render(gc);
        }
        snake.render(gc);
    }
}