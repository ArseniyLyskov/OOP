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

public class GameView {
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

        canvas.setWidth(M_COLUMNS * CELL_SIDE);
        canvas.setHeight(N_ROWS * CELL_SIDE);
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
