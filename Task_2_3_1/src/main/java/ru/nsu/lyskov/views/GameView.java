package ru.nsu.lyskov.views;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import javafx.util.Duration;
import ru.nsu.lyskov.models.GameModel;

public class GameView {
    public static final int CELL_SIDE = 10;

    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Label scoreLabel;
    private final Label statusLabel;
    private Timeline blinkTimeline;

    public GameView(Canvas canvas, Label scoreLabel, Label statusLabel) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.scoreLabel = scoreLabel;
        this.statusLabel = statusLabel;

        canvas.setWidth(GameModel.COLUMNS * CELL_SIDE);
        canvas.setHeight(GameModel.ROWS * CELL_SIDE);
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
        clear();
    }

    public void clear() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawInitial() {
        clear();
    }
}
