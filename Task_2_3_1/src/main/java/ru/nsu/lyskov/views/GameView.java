package ru.nsu.lyskov.views;

import static ru.nsu.lyskov.Constants.CELL_SIDE;
import static ru.nsu.lyskov.Constants.M_COLUMNS;
import static ru.nsu.lyskov.Constants.N_ROWS;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import ru.nsu.lyskov.models.GameModel;

public class GameView {
    private final int CANVAS_WIDTH;
    private final int CANVAS_HEIGHT;
    private final GraphicsContext gc;
    private final Label scoreLabel;
    private final Label statusLabel;
    private Timeline blinkTimeline;

    public GameView(Canvas canvas, Label scoreLabel, Label statusLabel) {
        this.gc = canvas.getGraphicsContext2D();
        this.scoreLabel = scoreLabel;
        this.statusLabel = statusLabel;
        CANVAS_WIDTH = M_COLUMNS * CELL_SIDE;
        CANVAS_HEIGHT = N_ROWS * CELL_SIDE;

        canvas.setWidth(CANVAS_WIDTH);
        canvas.setHeight(CANVAS_HEIGHT);
    }

    public void showWinStatus() {
        statusLabel.setVisible(true);
        showStatus("🎉 SIGMA 🎉", "#00ff00");
    }

    public void showLoseStatus() {
        statusLabel.setVisible(true);
        showStatus("💀 NON SIGMA 💀", "#ff3333");
    }

    public void reset() {
        scoreLabel.setText("0");
        statusLabel.setVisible(false);
        if (blinkTimeline != null) {
            blinkTimeline.stop();
        }
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public void redraw(GameModel model) {
        model.render(gc);
    }

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
