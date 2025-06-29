package ru.nsu.lyskov.models.snake;

import static ru.nsu.lyskov.Constants.M_COLUMNS;
import static ru.nsu.lyskov.Constants.N_ROWS;

import java.util.LinkedList;
import ru.nsu.lyskov.Direction;
import ru.nsu.lyskov.views.rendering.Renderable;

public abstract class AbstractSnake implements Renderable {
    private final LinkedList<SnakeSegment> segments = new LinkedList<>();
    private Direction direction;

    public AbstractSnake(int startX, int startY, Direction initialDirection) {
        this.direction = initialDirection;
        addHead(new SnakeSegment(
                startX, startY, initialDirection,
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
        boolean wasSingleSegment = isSingleSegment();
        SnakeSegment oldHead = getHead();
        SnakeSegment newHead = calculateNewHead(oldHead);
        addHead(newHead);

        if (!wasSingleSegment) {
            oldHead.changeType(SnakeSegmentType.getBodyType(
                    oldHead.getDirection(),
                    newHead.getDirection()
            ));

            SnakeSegment newTail = getNextSegment(getTail());
            SnakeSegment beforeNewTail = getNextSegment(newTail);
            newTail.changeType(SnakeSegmentType.getTailType(beforeNewTail.getDirection()));
        }

        removeTail();
    }

    public void grow() {
        SnakeSegment oldTail = getTail();
        SnakeSegment newTail = calculateGrownTail(oldTail);

        if (isSingleSegment()) {
            oldTail.changeType(SnakeSegmentType.getHeadType(oldTail.getDirection()));
        } else {
            oldTail.changeType(SnakeSegmentType.getBodyType(
                    oldTail.getDirection(),
                    getNextSegment(oldTail).getDirection()
            ));
        }

        addTail(newTail);
    }

    private SnakeSegment calculateNewHead(SnakeSegment oldHead) {
        int newX = oldHead.getX();
        int newY = oldHead.getY();

        switch (direction) {
            case UP -> newY = (newY - 1 + N_ROWS) % N_ROWS;
            case DOWN -> newY = (newY + 1) % N_ROWS;
            case LEFT -> newX = (newX - 1 + M_COLUMNS) % M_COLUMNS;
            case RIGHT -> newX = (newX + 1) % M_COLUMNS;
        }

        return isSingleSegment() ?
                new SnakeSegment(
                        newX, newY, direction,
                        SnakeSegmentType.getSingleSegmentType(direction)
                ) :
                new SnakeSegment(
                        newX, newY, direction,
                        SnakeSegmentType.getHeadType(direction)
                );
    }

    private SnakeSegment calculateGrownTail(SnakeSegment oldTail) {
        int newX = oldTail.getX();
        int newY = oldTail.getY();

        switch (oldTail.getDirection()) {
            case UP -> newY = (newY + 1) % N_ROWS;
            case DOWN -> newY = (newY - 1 + N_ROWS) % N_ROWS;
            case LEFT -> newX = (newX + 1) % M_COLUMNS;
            case RIGHT -> newX = (newX - 1 + M_COLUMNS) % M_COLUMNS;
        }

        return new SnakeSegment(
                newX, newY, oldTail.getDirection(),
                SnakeSegmentType.getTailType(oldTail.getDirection())
        );
    }

    private boolean isSingleSegment() {
        return segments.size() == 1;
    }

    private void addHead(SnakeSegment segment) {
        segments.addFirst(segment);
    }

    private void addTail(SnakeSegment segment) {
        segments.addLast(segment);
    }

    private void removeTail() {
        segments.removeLast();
    }

    private SnakeSegment getNextSegment(SnakeSegment segment) {
        return segments.get(segments.indexOf(segment) - 1);
    }

    private SnakeSegment getHead() {
        return segments.getFirst();
    }

    private SnakeSegment getTail() {
        return segments.getLast();
    }

    public LinkedList<SnakeSegment> getSegments() {
        return segments;
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

}