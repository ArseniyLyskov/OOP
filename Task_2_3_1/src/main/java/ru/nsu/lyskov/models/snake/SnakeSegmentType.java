package ru.nsu.lyskov.models.snake;

import ru.nsu.lyskov.Direction;

public enum SnakeSegmentType {
    SINGLE_UP,
    SINGLE_DOWN,
    SINGLE_LEFT,
    SINGLE_RIGHT,

    HEAD_UP,
    HEAD_DOWN,
    HEAD_LEFT,
    HEAD_RIGHT,

    TAIL_UP,
    TAIL_DOWN,
    TAIL_LEFT,
    TAIL_RIGHT,

    BODY_VERTICAL,
    BODY_HORIZONTAL,

    BODY_TURN_UP_RIGHT,
    BODY_TURN_UP_LEFT,
    BODY_TURN_DOWN_RIGHT,
    BODY_TURN_DOWN_LEFT;

    public static SnakeSegmentType getSingleSegmentType(Direction direction) {
        return switch (direction) {
            case UP -> SINGLE_UP;
            case DOWN -> SINGLE_DOWN;
            case LEFT -> SINGLE_LEFT;
            case RIGHT -> SINGLE_RIGHT;
        };
    }

    public static SnakeSegmentType getHeadType(Direction direction) {
        return switch (direction) {
            case UP -> HEAD_UP;
            case DOWN -> HEAD_DOWN;
            case LEFT -> HEAD_LEFT;
            case RIGHT -> HEAD_RIGHT;
        };
    }

    public static SnakeSegmentType getTailType(Direction direction) {
        return switch (direction) {
            case UP -> TAIL_UP;
            case DOWN -> TAIL_DOWN;
            case LEFT -> TAIL_LEFT;
            case RIGHT -> TAIL_RIGHT;
        };
    }

    /**
     * Определяет тип сегмента тела на основе направлений движения.
     *
     * @param from Направление, откуда приходит змея (к текущему сегменту).
     * @param to   Направление, куда змея уходит (от текущего сегмента).
     * @return Тип сегмента тела (прямой или поворотный).
     * @throws IllegalArgumentException Если направления противоречивы (например, UP -> DOWN).
     */
    public static SnakeSegmentType getBodyType(Direction from, Direction to) {
        if (from == to) {
            return (from == Direction.UP || from == Direction.DOWN)
                    ? BODY_VERTICAL
                    : BODY_HORIZONTAL;
        }

        return switch (from) {
            case UP -> switch (to) {
                case LEFT -> BODY_TURN_UP_LEFT;
                case RIGHT -> BODY_TURN_UP_RIGHT;
                default -> throw new IllegalArgumentException("Invalid turn: UP -> " + to);
            };
            case DOWN -> switch (to) {
                case LEFT -> BODY_TURN_DOWN_LEFT;
                case RIGHT -> BODY_TURN_DOWN_RIGHT;
                default -> throw new IllegalArgumentException("Invalid turn: DOWN -> " + to);
            };
            case LEFT -> switch (to) {
                case UP -> BODY_TURN_UP_RIGHT;
                case DOWN -> BODY_TURN_DOWN_RIGHT;
                default -> throw new IllegalArgumentException("Invalid turn: LEFT -> " + to);
            };
            case RIGHT -> switch (to) {
                case UP -> BODY_TURN_UP_LEFT;
                case DOWN -> BODY_TURN_DOWN_LEFT;
                default -> throw new IllegalArgumentException("Invalid turn: RIGHT -> " + to);
            };
        };
    }
}
