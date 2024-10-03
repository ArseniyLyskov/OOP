package ru.nsu.lyskov;

/**
 * Класс дилера в блэкджеке. Помимо поведения, присущего
 * любому игроку, дилер имеет на начальной раздаче закрытую карту.
 */
public class Dealer extends AbstractPlayer {
    /**
     * Особый ход дилера - открытие закрытой карты.
     *
     * @return Возвращает карту, которая лежала "рубашкой вверх".
     */
    public Card openClosedCard() {
        for (Card card : getHand()) {
            if (!card.isOpen()) {
                card.open();
                return card;
            }
        }
        throw new RuntimeException("Tried to open card, but dealer don't have cards");
    }

    /**
     * Проверка наличия у дилера закрытой карты.
     *
     * @return Возвращает true, если у дилера всё ещё закрыта карта.
     */
    public boolean isCardOpened() {
        for (Card card : getHand()) {
            if (!card.isOpen()) {
                return false;
            }
        }
        return true;
    }
}
