package ru.nsu.lyskov.logging;

import ru.nsu.lyskov.orders.PizzaOrder;

/**
 * Интерфейс для принятия заказов в системе пиццерии.
 */
public interface OrderAcceptor {
    /**
     * Принимает новый заказ с указанным идентификатором.
     *
     * @param orderId уникальный идентификатор заказа
     * @return объект {@code PizzaOrder}, представляющий принятый заказ
     * @throws InterruptedException  если поток был прерван
     * @throws IllegalStateException если возникла ошибка при создании заказа
     */
    PizzaOrder acceptOrder(int orderId) throws InterruptedException;
}