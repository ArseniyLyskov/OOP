package ru.nsu.lyskov.models.snake;

import ru.nsu.lyskov.Direction;

public class SnakeSegment {
    private final int x, y;
    private final Direction from, to;
    private SnakeSegmentType type;

    public SnakeSegment(int x, int y, Direction from, Direction to, SnakeSegmentType type) {
        this.x = x;
        this.y = y;
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirectionFrom() {
        return from;
    }

    public Direction getDirectionTo() {
        return to;
    }

    public SnakeSegmentType getType() {
        return type;
    }

    public void changeType(SnakeSegmentType type) {
        this.type = type;
    }

}
