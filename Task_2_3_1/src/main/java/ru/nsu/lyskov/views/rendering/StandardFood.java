package ru.nsu.lyskov.views.rendering;

import static ru.nsu.lyskov.Constants.CELL_SIDE;

import java.util.Objects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.nsu.lyskov.models.food.AbstractFood;

/**
 * Класс для отрисовки стандартной еды (яблока) в игре "Змейка".
 */
public class StandardFood extends AbstractFood {
    private static final Image APPLE_IMAGE = new Image(
            Objects.requireNonNull(StandardFood.class.getResourceAsStream(
                    "/ru/nsu/lyskov/images/food/apple.png"))
    );

    /**
     * Создает стандартную еду (яблоко) с указанными координатами.
     *
     * @param x координата X на игровом поле
     * @param y координата Y на игровом поле
     */
    public StandardFood(int x, int y) {
        super(x, y);
    }

    /**
     * Отрисовывает яблоко на графическом контексте. Изображение масштабируется под размер клетки
     * игрового поля.
     *
     * @param gc графический контекст для отрисовки
     */
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