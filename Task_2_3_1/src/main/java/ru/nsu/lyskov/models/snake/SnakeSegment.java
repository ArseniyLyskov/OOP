package ru.nsu.lyskov.models.snake;

import ru.nsu.lyskov.Direction;

/**
 * Класс, представляющий сегмент змейки.
 */
public class SnakeSegment {
    private final int x, y;
    private final Direction direction;
    private SnakeSegmentType type;

    /**
     * Создает новый сегмент змейки с указанными параметрами.
     *
     * @param x         координата X сегмента на игровом поле
     * @param y         координата Y сегмента на игровом поле
     * @param direction направление сегмента
     * @param type      тип сегмента (из перечисления SnakeSegmentType)
     */
    public SnakeSegment(int x, int y, Direction direction, SnakeSegmentType type) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.type = type;
    }

    /**
     * Возвращает координату X сегмента.
     *
     * @return координата X
     */
    public int getX() {
        return x;
    }

    /**
     * Возвращает координату Y сегмента.
     *
     * @return координата Y
     */
    public int getY() {
        return y;
    }

    /**
     * Возвращает направление сегмента.
     *
     * @return направление сегмента
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Возвращает тип сегмента.
     *
     * @return текущий тип сегмента
     */
    public SnakeSegmentType getType() {
        return type;
    }

    /**
     * Изменяет тип сегмента. Используется при преобразовании сегментов (например, при повороте
     * змейки).
     *
     * @param type новый тип сегмента
     */
    public void changeType(SnakeSegmentType type) {
        this.type = type;
    }
}