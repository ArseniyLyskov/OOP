package ru.nsu.lyskov.models.map;

import ru.nsu.lyskov.views.rendering.Renderable;

/**
 * Абстрактный базовый класс для игровых карт. Предоставляет базовую функциональность для создания
 * и управления игровым полем.
 */
public abstract class AbstractMap implements Renderable {
    private final MapCell[][] mapCells;
    private final int width;
    private final int height;

    /**
     * Создает новую карту с указанными размерами.
     *
     * @param width  ширина карты в клетках
     * @param height высота карты в клетках
     * @throws IllegalArgumentException если ширина или высота меньше или равны 0
     */
    public AbstractMap(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Map dimensions must be positive");
        }

        this.width = width;
        this.height = height;
        this.mapCells = new MapCell[width][height];

        initializeMap();
    }

    /**
     * Инициализирует карту, создавая клетки для всех координат. Вызывается автоматически при
     * создании карты.
     */
    private void initializeMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                mapCells[x][y] = new MapCell(x, y);
            }
        }
    }

    /**
     * Возвращает ширину карты в клетках.
     *
     * @return ширина карты
     */
    public int getWidth() {
        return width;
    }

    /**
     * Возвращает высоту карты в клетках.
     *
     * @return высота карты
     */
    public int getHeight() {
        return height;
    }
}