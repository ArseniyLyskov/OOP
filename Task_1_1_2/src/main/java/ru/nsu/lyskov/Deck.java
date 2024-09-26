package ru.nsu.lyskov;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    private final LinkedList<Card> cards = new LinkedList<>();

    public void addFullDeck() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 13; j++) {
                cards.add(new Card(j, i));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card removeCard(boolean open) {
        if (cards.isEmpty()) {
            addFullDeck();
            shuffle();
        }
        Card takenCard = cards.pop();
        if (open)
            takenCard.open();
        return takenCard;
    }

}
