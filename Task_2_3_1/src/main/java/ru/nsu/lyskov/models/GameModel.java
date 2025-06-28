package ru.nsu.lyskov.models;

public class GameModel {
    public static final int ROWS = 30;
    public static final int COLUMNS = 50;

    private int score;

    public GameModel() {
        reset();
    }

    public void reset() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }
}
