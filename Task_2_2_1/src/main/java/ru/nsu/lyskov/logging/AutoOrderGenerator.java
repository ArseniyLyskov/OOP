package ru.nsu.lyskov.logging;

import static java.lang.Thread.sleep;
import static ru.nsu.lyskov.Constants.AUTO_ORDER_GENERATOR_INTERVAL_MS;
import static ru.nsu.lyskov.orders.OrderStatus.DELIVERED;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.lyskov.orders.PizzaOrder;

public class AutoOrderGenerator implements OrderAcceptor {
    private final List<PizzaOrder> generatedOrders = new ArrayList<>();

    @Override
    public PizzaOrder acceptOrder(int orderId) throws InterruptedException {
        sleep(AUTO_ORDER_GENERATOR_INTERVAL_MS);
        PizzaOrder order = new PizzaOrder(orderId);
        generatedOrders.add(order);
        return order;
    }

    public synchronized boolean areAllOrdersDelivered() {
        return generatedOrders.stream()
                .allMatch(order -> order.getStatus() == DELIVERED);
    }
}