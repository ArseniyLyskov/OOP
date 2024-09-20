package ru.nsu.lyskov;

import org.junit.jupiter.api.Test;

class DeckTest {

    @Test
    void addFullDeck() {
        Deck deck = new Deck();
        deck.addFullDeck();
        System.out.println(deck.removeCard(true).toString());
        System.out.println(deck.removeCard(false).toString());
        System.out.println(deck.removeCard(true).toString());
        deck.shuffle();
        System.out.println(deck.removeCard(true).toString());
    }
}