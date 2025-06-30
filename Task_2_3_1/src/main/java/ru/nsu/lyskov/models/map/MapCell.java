package ru.nsu.lyskov.models.map;

/**
 * Класс, представляющий клетку игровой карты.
 */
public class MapCell {
    private final int x, y;

    /**
     * Создает новую клетку карты с указанными координатами.
     *
     * @param x координата X
     * @param y координата Y
     */
    public MapCell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}