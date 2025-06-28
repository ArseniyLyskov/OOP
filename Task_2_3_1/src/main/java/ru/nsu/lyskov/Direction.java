package ru.nsu.lyskov;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Проверяет, является ли текущее направление противоположным переданному.
     *
     * @param other направление для проверки
     * @return true если направления противоположны (UP-DOWN, LEFT-RIGHT)
     */
    public boolean isOpposite(Direction other) {
        return (this == UP && other == DOWN) ||
                (this == DOWN && other == UP) ||
                (this == LEFT && other == RIGHT) ||
                (this == RIGHT && other == LEFT);
    }
}