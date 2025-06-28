package ru.nsu.lyskov.models;

public class GameModel {
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
