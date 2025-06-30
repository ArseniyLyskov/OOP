package ru.nsu.lyskov.views.rendering;

import static ru.nsu.lyskov.Constants.CELL_SIDE;
import static ru.nsu.lyskov.Constants.M_COLUMNS;
import static ru.nsu.lyskov.Constants.N_ROWS;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.lyskov.models.map.AbstractMap;

/**
 * Класс для отрисовки стандартной игровой карты в виде шахматной сетки. Наследует от AbstractMap и
 * реализует визуализацию клеток в шахматном порядке.
 */
public class StandardMap extends AbstractMap {
    private final Color color1 = Color.rgb(50, 50, 50);
    private final Color color2 = Color.rgb(40, 40, 40);

    /**
     * Создает стандартную карту с размерами, указанными в Constants. Использует значения M_COLUMNS
     * (ширина) и N_ROWS (высота).
     */
    public StandardMap() {
        super(M_COLUMNS, N_ROWS);
    }

    /**
     * Отрисовывает игровую карту в виде шахматной сетки. Чередует два цвета клеток (color1 и
     * color2) в шахматном порядке.
     *
     * @param gc графический контекст для отрисовки
     */
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