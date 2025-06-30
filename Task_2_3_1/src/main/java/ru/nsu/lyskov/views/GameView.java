package ru.nsu.lyskov.views;

import static ru.nsu.lyskov.Constants.CELL_SIDE;
import static ru.nsu.lyskov.Constants.L_WIN_SCORE;
import static ru.nsu.lyskov.Constants.M_COLUMNS;
import static ru.nsu.lyskov.Constants.N_ROWS;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.util.Duration;
import ru.nsu.lyskov.models.GameModel;
import ru.nsu.lyskov.views.rendering.StandardMap;

/**
 * Класс, отвечающий за визуальное представление игры "Змейка". Управляет отрисовкой игрового поля,
 * отображением счёта и статуса игры.
 */
public class GameView {
    private final GraphicsContext gc;
    private final Label scoreLabel;
    private final Label statusLabel;
    private Timeline blinkTimeline;

    /**
     * Конструктор класса GameView.
     *
     * @param canvas      холст для отрисовки игрового поля
     * @param scoreLabel  текстовое поле для отображения счёта
     * @param statusLabel текстовое поле для отображения статуса игры
     */
    public GameView(Canvas canvas, Label scoreLabel, Label statusLabel) {
        this.gc = canvas.getGraphicsContext2D();
        this.scoreLabel = scoreLabel;
        this.statusLabel = statusLabel;

        canvas.setWidth(M_COLUMNS * CELL_SIDE);
        canvas.setHeight(N_ROWS * CELL_SIDE);
    }

    /**
     * Отображает сообщение о победе в игре.
     */
    public void showWinStatus() {
        statusLabel.setVisible(true);
        showStatus("🎉 SIGMA 🎉", "#00ff00");
    }

    /**
     * Отображает сообщение о проигрыше в игре.
     */
    public void showLoseStatus() {
        statusLabel.setVisible(true);
        showStatus("💀 NON SIGMA 💀", "#ff3333");
    }

    /**
     * Сбрасывает состояние представления к начальному.
     */
    public void reset() {
        scoreLabel.setText("0 / " + L_WIN_SCORE);
        statusLabel.setVisible(false);
        if (blinkTimeline != null) {
            blinkTimeline.stop();
        }
        new StandardMap().render(gc);
    }

    /**
     * Перерисовывает игровое поле в соответствии с текущей моделью.
     *
     * @param model игровая модель, содержащая текущее состояние игры
     */
    public void redraw(GameModel model) {
        model.render(gc);
    }

    /**
     * Отображает статусное сообщение с заданным текстом и цветом. Запускает анимацию мигания
     * сообщения.
     *
     * @param message текст сообщения
     * @param color   цвет текста в формате HEX
     */
    private void showStatus(String message, String color) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: " + color + ";");
        statusLabel.setVisible(true);

        if (blinkTimeline != null) {
            blinkTimeline.stop();
        }

        blinkTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> statusLabel.setVisible(false)),
                new KeyFrame(Duration.seconds(1.0), e -> statusLabel.setVisible(true))
        );
        blinkTimeline.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline.play();
    }
}