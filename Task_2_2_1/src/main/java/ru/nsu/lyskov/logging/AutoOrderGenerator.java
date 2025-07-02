package ru.nsu.lyskov.logging;

import static java.lang.Thread.sleep;
import static ru.nsu.lyskov.Constants.AUTO_ORDER_GENERATOR_INTERVAL_MS;

import ru.nsu.lyskov.orders.PizzaOrder;

public class AutoOrderGenerator implements OrderAcceptor {
    @Override
    public PizzaOrder acceptOrder(int orderId) throws InterruptedException {
        sleep(AUTO_ORDER_GENERATOR_INTERVAL_MS);
        return new PizzaOrder(orderId);
    }
}