package ru.nsu.lyskov;

public class Dealer extends AbstractPlayer {
    public Card openClosedCard() {
        for (Card card : getHand()) {
            if (!card.isOpen()) {
                card.open();
                return card;
            }
        }
        throw new RuntimeException("Tried to open card, but dealer don't have cards");
    }

    public boolean isCardOpened() {
        for (Card card : getHand()) {
            if (!card.isOpen()) {
                return false;
            }
        }
        return true;
    }
}
