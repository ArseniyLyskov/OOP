package ru.nsu.lyskov.controllers;

import static javafx.scene.input.KeyCode.ENTER;
import static ru.nsu.lyskov.Constants.CELL_SIDE;
import static ru.nsu.lyskov.Constants.L_WIN_SCORE;
import static ru.nsu.lyskov.Constants.M_COLUMNS;
import static ru.nsu.lyskov.Constants.N_ROWS;
import static ru.nsu.lyskov.Constants.SPEED_BASE_INTERVAL;
import static ru.nsu.lyskov.Constants.SPEED_INCREASE_FACTOR;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.nsu.lyskov.Direction;
import ru.nsu.lyskov.models.GameModel;
import ru.nsu.lyskov.views.GameView;

public class GameController {
    @FXML
    private Button startButton;
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
    private Direction pendingDirection = null;
    private long currentInterval = SPEED_BASE_INTERVAL;

    public void setScene(Stage stage) {
        model = new GameModel();
        view = new GameView(gameCanvas, scoreLabel, statusLabel);

        int canvasWidth = M_COLUMNS * CELL_SIDE;
        int canvasHeight = N_ROWS * CELL_SIDE;

        stage.setMinWidth(canvasWidth + 60);
        stage.setMinHeight(canvasHeight + 100);
        stage.setWidth(canvasWidth + 60);
        stage.setHeight(canvasHeight + 100);

        startButton.setFocusTraversable(false);
        stage.getScene().setOnKeyPressed(this::handleKeyPress);

        reset();
    }

    private void handleKeyPress(KeyEvent event) {
        if (!isRunning) {
            if (event.getCode() == ENTER || event.getCode().isWhitespaceKey()) {
                onStart();
            }
            return;
        }

        Direction newDirection = switch (event.getCode()) {
            case UP, W -> Direction.UP;
            case DOWN, S -> Direction.DOWN;
            case LEFT, A -> Direction.LEFT;
            case RIGHT, D -> Direction.RIGHT;
            default -> null;
        };

        if (newDirection != null && model.getSnake().canTurn(newDirection)) {
            pendingDirection = newDirection;
        }
    }

    private void resetGameLoop() {
        isRunning = false;
        currentInterval = SPEED_BASE_INTERVAL;

        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= currentInterval) {
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
        if (pendingDirection != null) {
            model.getSnake().setDirection(pendingDirection);
            pendingDirection = null;
        }

        model.getSnake().move();
        boolean foodCollision = model.checkFoodCollision();
        view.redraw(model);

        if (model.checkSelfCollision()) {
            view.showLoseStatus();
            gameLoop.stop();
            return;
        }

        if (foodCollision) {
            scoreLabel.setText(model.getScore() + " / " + L_WIN_SCORE);
            currentInterval = (long) (currentInterval * SPEED_INCREASE_FACTOR);
            if (model.getScore() >= L_WIN_SCORE) {
                view.showWinStatus();
                gameLoop.stop();
            }
        }
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
        pendingDirection = null;

        resetGameLoop();
    }
}
