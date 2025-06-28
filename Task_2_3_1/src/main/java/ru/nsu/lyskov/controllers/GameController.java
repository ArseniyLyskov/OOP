package ru.nsu.lyskov.controllers;

import static ru.nsu.lyskov.Constants.CELL_SIDE;
import static ru.nsu.lyskov.Constants.M_COLUMNS;
import static ru.nsu.lyskov.Constants.N_ROWS;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.nsu.lyskov.models.GameModel;
import ru.nsu.lyskov.views.GameView;

public class GameController {
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label statusLabel;

    private Stage stage;
    private GameModel model;
    private GameView view;

    public void setScene(Stage stage) {
        this.stage = stage;
        model = new GameModel();
        view = new GameView(gameCanvas, scoreLabel, statusLabel);

        int canvasWidth = M_COLUMNS * CELL_SIDE;
        int canvasHeight = N_ROWS * CELL_SIDE;

        stage.setMinWidth(canvasWidth + 60);
        stage.setMinHeight(canvasHeight + 100);

        stage.setWidth(canvasWidth + 60);
        stage.setHeight(canvasHeight + 100);

        model.reset();
        view.reset();
        view.drawInitial();
    }


    @FXML
    public void onStart() {
        model.reset();
        view.reset();
        view.drawInitial();
    }
}
