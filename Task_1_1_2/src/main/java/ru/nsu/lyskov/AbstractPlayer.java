package ru.nsu.lyskov;

import java.util.LinkedList;

/**
 * Абстрактный класс с полями и методами, общими для любого игрока в
 * блэкджек (дилера, пользователя софта, имитируемого игрока).
 */
public abstract class AbstractPlayer {
    /**
     * hand - рука (карты игрока).
     * score - количество очков, набранное игроком. (Сумма очков его карт).
     */
    private final LinkedList<Card> hand = new LinkedList<>();
    private int score;

    /**
     * Метод добавления карты в руку игрока.
     * Установка полученной карте количества очков
     * (например, а зависимости от других карт игрока туз принимает
     * разное количество очков: 1 либо 11). Обновление суммы очков игрока.
     *
     * @param card Карта, которую получает игрок.
     */
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

    /**
     * Стандартный геттер руки игрока.
     *
     * @return Возвращает связный список всех карт игрока.
     */
    public LinkedList<Card> getHand() {
        return hand;
    }

    /**
     * Геттер суммы очков игрока с ленивым подсчётом.
     *
     * @return Возвращает суммарное количество очков всех карт игрока.
     */
    public int getScore() {
        updateScore();
        return score;
    }

    /**
     * Игрок удаляет все свои карты.
     */
    public void foldCards() {
        hand.clear();
    }

    /**
     * Метод перерасчёта количества очков у игрока с
     * обновлением ценности тузов (1 или 11 очков за туз).
     */
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
