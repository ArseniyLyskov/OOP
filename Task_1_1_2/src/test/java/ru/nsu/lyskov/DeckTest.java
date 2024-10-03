package ru.nsu.lyskov;

import org.junit.jupiter.api.Test;

class DeckTest {
    private final Deck deck = new Deck();

    @Test
    void test() {
        deck.removeCard(true);
        deck.addFullDeck();
        deck.addFullDeck();
        deck.shuffle();
        deck.removeCard(false);
        deck.removeCard(true);
    }

    @Test
    void testCardException() {
        try {
            new Card(-1, -5);
        } catch (RuntimeException e) {
            System.out.println("Got expected exception 1/4");
        }
        try {
            new Card(3, -4);
        } catch (RuntimeException e) {
            System.out.println("Got expected exception 2/4");
        }
        try {
            new Card(15, 1);
        } catch (RuntimeException e) {
            System.out.println("Got expected exception 3/4");
        }
        try {
            new Card(10, 5);
        } catch (RuntimeException e) {
            System.out.println("Got expected exception 4/4");
        }
    }
}