package ru.nsu.lyskov.models.map;

import ru.nsu.lyskov.views.rendering.Renderable;

public abstract class AbstractMap implements Renderable {
    private final MapCell[][] mapCells;
    private final int width;
    private final int height;

    public AbstractMap(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Map dimensions must be positive");
        }

        this.width = width;
        this.height = height;
        this.mapCells = new MapCell[width][height];

        initializeMap();
    }

    private void initializeMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                mapCells[x][y] = new MapCell(x, y);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}