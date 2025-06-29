package ru.nsu.lyskov.controllers;

import static ru.nsu.lyskov.Constants.CELL_SIDE;
import static ru.nsu.lyskov.Constants.M_COLUMNS;
import static ru.nsu.lyskov.Constants.N_ROWS;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.nsu.lyskov.Direction;
import ru.nsu.lyskov.models.GameModel;
import ru.nsu.lyskov.views.GameView;

public class GameController {
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label statusLabel;

    private GameModel model;
    private GameView view;

    private AnimationTimer gameLoop;
    private boolean isRunning = false;

    public void setScene(Stage stage) {
        model = new GameModel();
        view = new GameView(gameCanvas, scoreLabel, statusLabel);

        int canvasWidth = M_COLUMNS * CELL_SIDE;
        int canvasHeight = N_ROWS * CELL_SIDE;

        stage.setMinWidth(canvasWidth + 60);
        stage.setMinHeight(canvasHeight + 100);
        stage.setWidth(canvasWidth + 60);
        stage.setHeight(canvasHeight + 100);

        stage.getScene().setOnKeyPressed(this::handleKeyPress);

        reset();
    }

    private void handleKeyPress(KeyEvent event) {
        if (!isRunning) return;

        Direction newDirection = switch (event.getCode()) {
            case UP, W -> Direction.UP;
            case DOWN, S -> Direction.DOWN;
            case LEFT, A -> Direction.LEFT;
            case RIGHT, D -> Direction.RIGHT;
            default -> null;
        };

        if (newDirection != null && !newDirection.isOpposite(model.getSnake().getDirection())) {
            model.getSnake().setDirection(newDirection);
        }
    }

    private void resetGameLoop() {
        isRunning = false;
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            private final long interval = 100_000_000;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= interval) {
                    updateGame();
                    lastUpdate = now;
                }
            }

            @Override
            public void start() {
                isRunning = true;
                super.start();
            }

            @Override
            public void stop() {
                isRunning = false;
                super.stop();
            }
        };
    }

    private void updateGame() {
        model.update();
        scoreLabel.setText(String.valueOf(model.getScore()));
        view.redraw(model);
    }

    @FXML
    public void onStart() {
        reset();
        gameLoop.start();
    }

    private void reset() {
        if (gameLoop != null)
            gameLoop.stop();

        model.reset();
        view.reset();

        resetGameLoop();
    }
}
