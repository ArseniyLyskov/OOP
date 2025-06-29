package ru.nsu.lyskov.views.rendering;

import static ru.nsu.lyskov.Constants.CELL_SIDE;
import static ru.nsu.lyskov.Constants.M_COLUMNS;
import static ru.nsu.lyskov.Constants.N_ROWS;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.lyskov.models.map.AbstractMap;

public class StandardMap extends AbstractMap {
    private final Color color1 = Color.rgb(50, 50, 50);
    private final Color color2 = Color.rgb(40, 40, 40);

    public StandardMap() {
        super(M_COLUMNS, N_ROWS);
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                Color cellColor = (x + y) % 2 == 0 ? color1 : color2;
                gc.setFill(cellColor);
                gc.fillRect(x * CELL_SIDE, y * CELL_SIDE, CELL_SIDE, CELL_SIDE);
            }
        }
    }
}
