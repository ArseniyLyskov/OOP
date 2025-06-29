package ru.nsu.lyskov.models.food;

import ru.nsu.lyskov.views.rendering.Renderable;

public abstract class AbstractFood implements Renderable {
    private final int x, y;

    protected AbstractFood(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
