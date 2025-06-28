package ru.nsu.lyskov.models.snake;

import ru.nsu.lyskov.Direction;

/**
 * Непроверяемое исключение, выбрасываемое при попытке движения в недопустимом направлении.
 */
public class InvalidMoveDirectionException extends RuntimeException {
    public InvalidMoveDirectionException(Direction current, Direction attempted) {
        super(String.format("Cannot turn from %s to %s - opposite direction",
                            current, attempted
        ));
    }
}
