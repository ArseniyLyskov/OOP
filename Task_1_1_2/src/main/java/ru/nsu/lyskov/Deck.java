package ru.nsu.lyskov;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Класс колоды карт.
 */
public class Deck {
    private final LinkedList<Card> cards = new LinkedList<>();

    /**
     * Добавление в колоду полной стандартной
     * колоды из 52 карт, с 4 мастями.
     */
    public void addFullDeck() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 13; j++) {
                cards.add(new Card(j, i));
            }
        }
    }

    /**
     * Перетасовка колоды.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Взятие карты из колоды.
     *
     * @param open Флаг, указывающий на то, что карту можно
     *             открыть (не оставлять рубашкой вниз).
     * @return Возвращает карту, взятую из колоды.
     */
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
