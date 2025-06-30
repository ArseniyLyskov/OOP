package ru.nsu.lyskov.models.map;

/**
 * Класс, представляющий клетку игровой карты.
 */
public class MapCell {
    private final int column;
    private final int row;

    /**
     * Создает новую клетку карты с указанными координатами.
     *
     * @param x координата X
     * @param y координата Y
     */
    public MapCell(int x, int y) {
        column = x;
        row = y;
    }
}