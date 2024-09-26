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

        if (value < 1 || value > 13 || suit < 1 || suit > 4)
            throw new RuntimeException("Card value not in [1..13] or card suit not in [1..4]");
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
