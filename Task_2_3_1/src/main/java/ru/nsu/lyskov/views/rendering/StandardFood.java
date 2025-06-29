package ru.nsu.lyskov.views.rendering;

import static ru.nsu.lyskov.Constants.CELL_SIDE;

import java.util.Objects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.nsu.lyskov.models.food.AbstractFood;

public class StandardFood extends AbstractFood {
    private static final Image APPLE_IMAGE = new Image(
            Objects.requireNonNull(StandardFood.class.getResourceAsStream(
                    "/ru/nsu/lyskov/images/food/apple.png"))
    );

    public StandardFood(int x, int y) {
        super(x, y);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
                APPLE_IMAGE,
                getX() * CELL_SIDE,
                getY() * CELL_SIDE,
                CELL_SIDE,
                CELL_SIDE
        );
    }
}
