package ru.nsu.lyskov.views.rendering;

import static ru.nsu.lyskov.Constants.CELL_SIDE;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.lyskov.Direction;
import ru.nsu.lyskov.models.snake.AbstractSnake;
import ru.nsu.lyskov.models.snake.SnakeSegment;
import ru.nsu.lyskov.models.snake.SnakeSegmentType;

public class PlayerSnake extends AbstractSnake {
    private static final Color COLOR_PLAYER = Color.GREEN;
    private static final Color COLOR_EYE = Color.WHITE;
    private static final Color COLOR_PUPIL = Color.BLACK;

    public PlayerSnake(int startX, int startY, Direction initialDirection) {
        super(startX, startY, initialDirection);
    }

    @Override
    public void render(GraphicsContext gc) {
        for (SnakeSegment segment : getSegments()) {
            renderSegment(gc, segment);
        }
    }

    public static void renderSegment(GraphicsContext gc, SnakeSegment segment) {
        gc.setFill(COLOR_PLAYER);
        double x = segment.getX() * CELL_SIDE;
        double y = segment.getY() * CELL_SIDE;
        SnakeSegmentType type = segment.getType();

        switch (type) {
            case SINGLE_UP, SINGLE_DOWN, SINGLE_LEFT, SINGLE_RIGHT -> {
                drawHead(gc, x, y, type);
            }

            case HEAD_UP, HEAD_DOWN, HEAD_LEFT, HEAD_RIGHT -> {
                drawNeck(gc, x, y, type);
                drawHead(gc, x, y, type);
            }

            case TAIL_UP, TAIL_DOWN, TAIL_LEFT, TAIL_RIGHT -> {
                drawTail(gc, x, y, type);
            }

            case BODY_VERTICAL -> drawVerticalBody(gc, x, y);
            case BODY_HORIZONTAL -> drawHorizontalBody(gc, x, y);

            case BODY_TURN_UP_RIGHT,
                 BODY_TURN_UP_LEFT,
                 BODY_TURN_DOWN_RIGHT,
                 BODY_TURN_DOWN_LEFT -> {
                drawTurn(gc, x, y, type);
            }
        }
    }

    private static void drawHead(GraphicsContext gc, double x, double y, SnakeSegmentType type) {
        int padding = CELL_SIDE / 5;
        gc.fillOval(x + padding / 2, y + padding / 2, CELL_SIDE - padding, CELL_SIDE - padding);

        gc.setFill(COLOR_EYE);
        double eyeSize = CELL_SIDE / 4;
        double eyeOffset = CELL_SIDE / 5;
        double pupilSize = CELL_SIDE / 6;
        double pupilOffset = (eyeSize - pupilSize) / 2;

        switch (type) {
            case HEAD_UP, SINGLE_UP -> {
                gc.fillOval(x + eyeOffset, y + eyeOffset,
                            eyeSize, eyeSize
                );
                gc.fillOval(x + 3 * eyeOffset, y + eyeOffset,
                            eyeSize, eyeSize
                );
                gc.setFill(COLOR_PUPIL);
                gc.fillOval(x + eyeOffset + pupilOffset, y + eyeOffset + pupilOffset,
                            pupilSize, pupilSize
                );
                gc.fillOval(x + 3 * eyeOffset + pupilOffset, y + eyeOffset + pupilOffset,
                            pupilSize, pupilSize
                );
            }
            case HEAD_DOWN, SINGLE_DOWN -> {
                gc.fillOval(x + eyeOffset, y + CELL_SIDE - 2 * eyeOffset,
                            eyeSize, eyeSize
                );
                gc.fillOval(x + 3 * eyeOffset, y + CELL_SIDE - 2 * eyeOffset,
                            eyeSize, eyeSize
                );
                gc.setFill(COLOR_PUPIL);
                gc.fillOval(x + eyeOffset + pupilOffset,
                            y + CELL_SIDE - 2 * eyeOffset + pupilOffset,
                            pupilSize, pupilSize
                );
                gc.fillOval(x + 3 * eyeOffset + pupilOffset,
                            y + CELL_SIDE - 2 * eyeOffset + pupilOffset,
                            pupilSize, pupilSize
                );
            }
            case HEAD_LEFT, SINGLE_LEFT -> {
                gc.fillOval(x + eyeOffset, y + eyeOffset,
                            eyeSize, eyeSize
                );
                gc.fillOval(x + eyeOffset, y + 3 * eyeOffset,
                            eyeSize, eyeSize
                );
                gc.setFill(COLOR_PUPIL);
                gc.fillOval(x + eyeOffset + pupilOffset, y + eyeOffset + pupilOffset,
                            pupilSize, pupilSize
                );
                gc.fillOval(x + eyeOffset + pupilOffset, y + 3 * eyeOffset + pupilOffset,
                            pupilSize, pupilSize
                );
            }
            case HEAD_RIGHT, SINGLE_RIGHT -> {
                gc.fillOval(x + CELL_SIDE - 2 * eyeOffset, y + eyeOffset,
                            eyeSize, eyeSize
                );
                gc.fillOval(x + CELL_SIDE - 2 * eyeOffset, y + 3 * eyeOffset,
                            eyeSize, eyeSize
                );
                gc.setFill(COLOR_PUPIL);
                gc.fillOval(x + CELL_SIDE - 2 * eyeOffset + pupilOffset,
                            y + eyeOffset + pupilOffset,
                            pupilSize, pupilSize
                );
                gc.fillOval(x + CELL_SIDE - 2 * eyeOffset + pupilOffset,
                            y + 3 * eyeOffset + pupilOffset,
                            pupilSize, pupilSize
                );
            }
        }
        gc.setFill(COLOR_PLAYER);

    }

    private static void drawNeck(GraphicsContext gc, double x, double y, SnakeSegmentType type) {
        int padding = CELL_SIDE / 4;
        switch (type) {
            case HEAD_UP -> {
                gc.fillRect(x + padding, y + padding,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - padding
                );
            }
            case HEAD_DOWN -> {
                gc.fillRect(x + padding, y,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - padding
                );
            }
            case HEAD_LEFT -> {
                gc.fillRect(x + padding, y + padding,
                            CELL_SIDE - padding,
                            CELL_SIDE - 2 * padding
                );
            }
            case HEAD_RIGHT -> {
                gc.fillRect(x, y + padding,
                            CELL_SIDE - padding,
                            CELL_SIDE - 2 * padding
                );
            }
        }
    }

    private static void drawTail(GraphicsContext gc, double x, double y, SnakeSegmentType type) {
        int padding = CELL_SIDE / 4;
        switch (type) {
            case TAIL_UP -> {
                gc.fillRect(x + padding, y,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
            }
            case TAIL_DOWN -> {
                gc.fillRect(x + padding, y + 2 * padding,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
            }
            case TAIL_LEFT -> {
                gc.fillRect(x, y + padding,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
            }
            case TAIL_RIGHT -> {
                gc.fillRect(x + 2 * padding, y + padding,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
            }
        }
        gc.fillOval(x + padding, y + padding,
                    CELL_SIDE - 2 * padding,
                    CELL_SIDE - 2 * padding
        );
    }

    private static void drawVerticalBody(GraphicsContext gc, double x, double y) {
        int padding = CELL_SIDE / 4;
        gc.fillRect(x + padding, y, CELL_SIDE - 2 * padding, CELL_SIDE);
    }

    private static void drawHorizontalBody(GraphicsContext gc, double x, double y) {
        int padding = CELL_SIDE / 4;
        gc.fillRect(x, y + padding, CELL_SIDE, CELL_SIDE - 2 * padding);
    }

    private static void drawTurn(GraphicsContext gc, double x, double y, SnakeSegmentType type) {
        int padding = CELL_SIDE / 4;
        switch (type) {
            case BODY_TURN_UP_RIGHT -> {
                gc.fillRect(x + padding, y,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
                gc.fillRect(x + 2 * padding, y + padding,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
            }
            case BODY_TURN_UP_LEFT -> {
                gc.fillRect(x + padding, y,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
                gc.fillRect(x, y + padding,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
            }
            case BODY_TURN_DOWN_RIGHT -> {
                gc.fillRect(x + padding, y + 2 * padding,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
                gc.fillRect(x + 2 * padding, y + padding,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
            }
            case BODY_TURN_DOWN_LEFT -> {
                gc.fillRect(x + padding, y + 2 * padding,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
                gc.fillRect(x, y + padding,
                            CELL_SIDE - 2 * padding,
                            CELL_SIDE - 2 * padding
                );
            }
        }
        gc.fillOval(x + padding, y + padding,
                    CELL_SIDE - 2 * padding,
                    CELL_SIDE - 2 * padding
        );
    }
}
