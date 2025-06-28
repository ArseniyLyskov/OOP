package ru.nsu.lyskov.models.snake;

import java.util.LinkedList;
import ru.nsu.lyskov.Direction;

public abstract class AbstractSnake {
    private final LinkedList<SnakeSegment> segments = new LinkedList<>();
    private Direction direction;

    public AbstractSnake(int startX, int startY, Direction initialDirection) {
        this.direction = initialDirection;
        segments.add(new SnakeSegment(
                startX, startY,
                initialDirection, initialDirection,
                SnakeSegmentType.getSingleSegmentType(initialDirection)
        ));
    }

    /**
     * Проверяет, возможен ли поворот в указанное направление. Для односегментной змейки всегда
     * возвращает true.
     *
     * @param newDirection направление для проверки
     * @return true если поворот допустим
     */
    public boolean canTurn(Direction newDirection) {
        return isSingleSegment() || !direction.isOpposite(newDirection);
    }

    public void move() {
        SnakeSegment oldHead = segments.getFirst();
        SnakeSegment newHead = calculateNewHead(oldHead);

        if (!isSingleSegment()) {
            oldHead.changeType(SnakeSegmentType.getBodyType(
                    oldHead.getDirectionFrom(),
                    oldHead.getDirectionTo()
            ));

            SnakeSegment newTail = segments.get(segments.size() - 2);
            newTail.changeType(SnakeSegmentType.getTailType(newTail.getDirectionTo()));
        }

        segments.addFirst(newHead);
        segments.removeLast();
    }

    public void grow() {
        SnakeSegment oldTail = segments.getLast();

        if (isSingleSegment()) {
            oldTail.changeType(SnakeSegmentType.getHeadType(oldTail.getDirectionTo()));
        } else {
            oldTail.changeType(SnakeSegmentType.getBodyType(
                    oldTail.getDirectionFrom(),
                    oldTail.getDirectionTo()
            ));
        }

        SnakeSegment newTail = calculateGrownTail(oldTail);
        segments.addLast(newTail);
    }

    private SnakeSegment calculateNewHead(SnakeSegment oldHead) {
        int newX = oldHead.getX();
        int newY = oldHead.getY();

        switch (direction) {
            case UP -> newY--;
            case DOWN -> newY++;
            case LEFT -> newX--;
            case RIGHT -> newX++;
        }

        return isSingleSegment() ?
                new SnakeSegment(
                        newX, newY,
                        oldHead.getDirectionTo(), direction,
                        SnakeSegmentType.getSingleSegmentType(direction)
                ) :
                new SnakeSegment(
                        newX, newY,
                        oldHead.getDirectionTo(), direction,
                        SnakeSegmentType.getHeadType(direction)
                );
    }

    private SnakeSegment calculateGrownTail(SnakeSegment oldTail) {
        int newX = oldTail.getX();
        int newY = oldTail.getY();

        switch (oldTail.getDirectionFrom()) {
            case UP -> newY++;
            case DOWN -> newY--;
            case LEFT -> newX++;
            case RIGHT -> newX--;
        }

        return new SnakeSegment(
                newX,
                newY,
                oldTail.getDirectionFrom(),
                oldTail.getDirectionFrom(),
                SnakeSegmentType.getTailType(oldTail.getDirectionFrom())
        );
    }

    private boolean isSingleSegment() {
        return segments.size() == 1;
    }

    public int getSnakeLength() {
        return segments.size();
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Устанавливает новое направление движения с проверкой допустимости.
     *
     * @param newDirection новое направление
     * @throws InvalidMoveDirectionException если поворот невозможен
     */
    public void setDirection(Direction newDirection) {
        if (!canTurn(newDirection)) {
            throw new InvalidMoveDirectionException(direction, newDirection);
        }
        this.direction = newDirection;
    }

    public abstract void render();
}