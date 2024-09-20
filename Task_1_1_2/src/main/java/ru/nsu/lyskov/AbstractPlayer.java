package ru.nsu.lyskov;

import java.util.LinkedList;

public abstract class AbstractPlayer {
    private LinkedList<Card> cards = new LinkedList<>();
    private int score;

    public void takeCard(Card card) {
        cards.add(card);
    }

    public void foldCards() {
        cards.clear();
    }

    //TODO: Localization
}
