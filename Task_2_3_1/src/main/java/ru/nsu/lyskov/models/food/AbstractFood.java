package ru.nsu.lyskov.models.food;

import ru.nsu.lyskov.views.rendering.Renderable;

/**
 * Абстрактный базовый класс для всех видов еды в игре "Змейка". Определяет общие свойства и
 * поведение для всех типов еды.
 */
public abstract class AbstractFood implements Renderable {
    private final int column;
    private final int row;

    /**
     * Создает новый объект еды с указанными координатами.
     *
     * @param x координата X на игровом поле
     * @param y координата Y на игровом поле
     */
    protected AbstractFood(int x, int y) {
        column = x;
        row = y;
    }

    /**
     * Возвращает координату X расположения еды на игровом поле.
     *
     * @return координата X
     */
    public int getX() {
        return column;
    }

    /**
     * Возвращает координату Y расположения еды на игровом поле.
     *
     * @return координата Y
     */
    public int getY() {
        return row;
    }
}