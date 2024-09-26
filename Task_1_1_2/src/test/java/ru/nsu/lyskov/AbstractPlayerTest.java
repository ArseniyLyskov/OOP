package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AbstractPlayerTest {
    private final TestPlayer player = new TestPlayer();

    private static class TestPlayer extends AbstractPlayer {
    }

    @Test
    void test() {
        Card card = new Card(1, 2);
        player.takeCard(card);
        player.takeCard(card);
        assertEquals(12, player.getScore());

        card = new Card(3, 4);
        player.takeCard(card);
        card = new Card(12, 4);
        player.takeCard(card);
        assertEquals(4, player.getHand().size());
        assertEquals(15, player.getScore());

        player.foldCards();
        assertEquals(0, player.getScore());
    }
}