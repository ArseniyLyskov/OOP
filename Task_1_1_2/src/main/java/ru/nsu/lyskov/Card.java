package ru.nsu.lyskov;

/**
 * Класс карты. Поля:
 * isOpen - видимость пользователю (рубашкой вверх или вниз),
 * value - ценность карты (туз, двойка, тройка, ..., король),
 * suit - масть карты (черви, пики, ...),
 * cardScore - количество очков, которое карта приносит пользователю.
 * (карты от двойки до десятки приносят соответствующее количество очков,
 * валет, дама, король - десять очков, туз - может давать как 1 очко, так и 11).
 */
public class Card {
    private boolean isOpen;
    private final int value;
    private final int suit;
    private int cardScore = 0;

    /**
     * Конструктор класса, проверяющий корректность введённых данных.
     *
     * @param value Ценность карты
     * @param suit  Масть карты
     */
    public Card(int value, int suit) {
        isOpen = false;
        this.value = value;
        this.suit = suit;

        if (value < 1 || value > 13 || suit < 1 || suit > 4) {
            throw new RuntimeException("Card value not in [1..13] or card suit not in [1..4]");
        }
    }

    public int getCardScore() {
        return cardScore;
    }

    public void setCardScore(int cardScore) {
        this.cardScore = cardScore;
    }

    public int getValue() {
        return value;
    }

    public int getSuit() {
        return suit;
    }

    public void open() {
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
