package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DealerTest {
    private final Dealer dealer = new Dealer();

    @Test
    void test() {
        Deck deck = new Deck();
        deck.addFullDeck();
        dealer.takeCard(deck.removeCard(true));
        dealer.takeCard(deck.removeCard(false));
        assertFalse(dealer.isCardOpened());
        dealer.openClosedCard();
        assertTrue(dealer.isCardOpened());
    }

    @Test
    void testException() {
        try {
            dealer.openClosedCard();
        } catch (RuntimeException ignored) {
            System.out.println("Got expected exception!");
        }
    }
}