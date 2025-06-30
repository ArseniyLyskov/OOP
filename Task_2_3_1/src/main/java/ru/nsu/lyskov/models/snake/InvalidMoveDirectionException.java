package ru.nsu.lyskov.models.snake;

import ru.nsu.lyskov.Direction;

/**
 * Непроверяемое исключение, выбрасываемое при попытке движения в недопустимом направлении.
 */
public class InvalidMoveDirectionException extends RuntimeException {
    /**
     * Создаёт новое исключение с описанием недопустимого изменения направления.
     *
     * @param current   текущее направление движения змейки
     * @param attempted попытка установить противоположное направление
     */
    public InvalidMoveDirectionException(Direction current, Direction attempted) {
        super(String.format("Cannot turn from %s to %s - opposite direction",
                            current, attempted
        ));
    }
}