package ru.nsu.lyskov;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    private final String[] cardValues = {"Туз", "Двойка", "Тройка", "Четвёрка", "Пятёрка", "Шестёрка", "Семёрка", "Восьмёрка", "Девятка", "Десятка", "Валет", "Дама", "Король"};
    private final String[] cardSuits = {"Пики", "Трефы", "Червы", "Бубны"};
    private LinkedList<Card> cards = new LinkedList<>();

    public void addFullDeck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                cards.add(new Card(cardValues[j], cardSuits[i]));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card removeCard(boolean open) {
        Card takenCard = cards.pop();
        if (open)
            takenCard.open();
        return takenCard;
    }

}
