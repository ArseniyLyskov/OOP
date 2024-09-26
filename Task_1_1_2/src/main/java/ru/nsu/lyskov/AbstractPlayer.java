package ru.nsu.lyskov;

import java.util.LinkedList;

public abstract class AbstractPlayer {
    private final LinkedList<Card> hand = new LinkedList<>();
    private int score;

    public void takeCard(Card card) {
        hand.add(card);
        if (card.getValue() >= 2 && card.getValue() <= 10)
            card.setCardScore(card.getValue());
        else if (card.getValue() >= 11 && card.getValue() <= 13)
            card.setCardScore(10);
        else //if (card.getValue() == 1)
            card.setCardScore(score <= 10 ? 11 : 1);
        updateScore();
    }

    public LinkedList<Card> getHand() {
        return hand;
    }

    public int getScore() {
        updateScore();
        return score;
    }

    public void foldCards() {
        hand.clear();
    }

    private void updateScore() {
        score = 0;
        for (Card card : hand)
            if (card.getValue() >= 2)
                score += card.getCardScore();
        for (Card card : hand) {
            if (card.getValue() == 1) {
                int newAceScore = score <= 10 ? 11 : 1;
                score += newAceScore;
                card.setCardScore(newAceScore);
            }
        }
    }

}
