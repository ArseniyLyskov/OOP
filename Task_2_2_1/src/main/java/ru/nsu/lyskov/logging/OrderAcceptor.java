package ru.nsu.lyskov.logging;

import ru.nsu.lyskov.orders.PizzaOrder;

public interface OrderAcceptor {
    PizzaOrder acceptOrder(int orderId) throws InterruptedException;
}