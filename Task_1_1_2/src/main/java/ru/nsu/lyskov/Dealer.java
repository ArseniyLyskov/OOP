package ru.nsu.lyskov;

public class Dealer extends AbstractPlayer {
    public Card openClosedCard() {
        for (Card card : getHand()) {
            if (!card.isOpen()) {
                card.open();
                return card;
            }
        }
        return null;
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
