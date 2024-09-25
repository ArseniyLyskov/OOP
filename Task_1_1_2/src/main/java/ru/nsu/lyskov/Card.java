package ru.nsu.lyskov;

public class Card {
    private boolean isOpen;
    private final int value;
    private final int suit;
    private int cardScore = 0;

    public Card(int value, int suit) {
        isOpen = false;
        this.value = value;
        this.suit = suit;
    }

    public int getCardScore() {
        return cardScore;
    }

    public void setCardScore(int cardScore) {
        this.cardScore = cardScore;
    }

    public int getValue() {
        return value;
    }

    public int getSuit() {
        return suit;
    }

    public void open() {
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
