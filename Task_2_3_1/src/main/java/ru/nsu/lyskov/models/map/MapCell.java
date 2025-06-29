package ru.nsu.lyskov.models.map;

public class MapCell {
    private final int x, y;

    public MapCell(int x, int y) {
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
