package ru.nsu.lyskov.views.rendering;

import static ru.nsu.lyskov.Constants.CELL_SIDE;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.lyskov.Direction;
import ru.nsu.lyskov.models.snake.AbstractSnake;
import ru.nsu.lyskov.models.snake.SnakeSegment;

public class PlayerSnake extends AbstractSnake {
    public static final Color COLOR_PLAYER = Color.GREEN;

    public PlayerSnake(int startX, int startY, Direction initialDirection) {
        super(startX, startY, initialDirection);
    }

    @Override
    public void render(GraphicsContext gc) {
        for (SnakeSegment segment : getSegments()) {
            renderSegment(gc, segment);
        }
    }

    private void renderSegment(GraphicsContext gc, SnakeSegment segment) {
        double x = segment.getX() * CELL_SIDE;
        double y = segment.getY() * CELL_SIDE;

        gc.setFill(COLOR_PLAYER);
        gc.fillOval(x, y, CELL_SIDE, CELL_SIDE);
    }
}
