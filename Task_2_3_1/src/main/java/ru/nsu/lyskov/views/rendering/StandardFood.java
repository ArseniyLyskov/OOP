package ru.nsu.lyskov.views.rendering;

import static ru.nsu.lyskov.Constants.CELL_SIDE;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.lyskov.models.food.AbstractFood;

public class StandardFood extends AbstractFood {
    public static final Color COLOR_STANDARD_FOOD = Color.RED;

    public StandardFood(int x, int y) {
        super(x, y);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(COLOR_STANDARD_FOOD);
        gc.fillOval(getX() * CELL_SIDE, getY() * CELL_SIDE, CELL_SIDE, CELL_SIDE);
    }
}
