package ru.nsu.lyskov.models.snake;

import ru.nsu.lyskov.Direction;

public class SnakeSegment {
    private final int x, y;
    private final Direction direction;
    private SnakeSegmentType type;

    public SnakeSegment(int x, int y, Direction direction, SnakeSegmentType type) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public SnakeSegmentType getType() {
        return type;
    }

    public void changeType(SnakeSegmentType type) {
        this.type = type;
    }

}
