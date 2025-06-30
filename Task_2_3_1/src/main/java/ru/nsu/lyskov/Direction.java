package ru.nsu.lyskov;

/**
 * Enum, представляющий возможные направления движения.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Проверяет, является ли текущее направление противоположным переданному. Противоположными
     * считаются пары: UP-DOWN и LEFT-RIGHT.
     *
     * @param other направление для сравнения с текущим
     * @return {@code true} если направления противоположны, {@code false} в противном случае
     */
    public boolean isOpposite(Direction other) {
        return (this == UP && other == DOWN) ||
                (this == DOWN && other == UP) ||
                (this == LEFT && other == RIGHT) ||
                (this == RIGHT && other == LEFT);
    }
}