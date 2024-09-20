package ru.nsu.lyskov;

public class Card {
    private boolean isOpen;
    private final String value;
    private final String suit;

    public Card(String value, String suit) {
        isOpen = false;
        this.value = value;
        this.suit = suit;
    }

    public void open() {
        isOpen = true;
    }

    @Override
    public String toString() {
        if (!isOpen)
            return "<Закрытая карта>";
        return value + " " + suit;
    }
}
