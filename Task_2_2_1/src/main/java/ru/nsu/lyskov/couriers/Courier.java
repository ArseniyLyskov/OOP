package ru.nsu.lyskov.couriers;

import static ru.nsu.lyskov.Constants.COURIER_ORDER_COLLECTION_TIME_MS;
import static ru.nsu.lyskov.Constants.COURIER_SINGLE_ORDER_DELIVERY_TIME_MS;

import java.util.List;
import ru.nsu.lyskov.ConcurrentLatch;
import ru.nsu.lyskov.orders.OrderStatus;
import ru.nsu.lyskov.orders.PizzaOrder;
import ru.nsu.lyskov.storage.Storage;

public class Courier implements Runnable {
    private final ConcurrentLatch completionLatch;
    private final int capacity;
    private final Storage storage;

    public Courier(int capacity, Storage storage, ConcurrentLatch completionLatch) {
        this.capacity = capacity;
        this.storage = storage;
        this.completionLatch = completionLatch;
    }

    @Override
    public void run() {
        try {
            while (!storage.isEmpty() || !storage.isAdditionCompleted()) {
                List<PizzaOrder> orders = storage.takePizzas(capacity);
                deliverPizzas(orders);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            completionLatch.countDown();
        }
    }

    private void deliverPizzas(List<PizzaOrder> orders) throws InterruptedException {
        if (orders.isEmpty()) return;

        orders.forEach(order -> order.setStatus(OrderStatus.ON_DELIVERY));
        Thread.sleep(COURIER_ORDER_COLLECTION_TIME_MS +
                             (long) orders.size() * COURIER_SINGLE_ORDER_DELIVERY_TIME_MS);
        orders.forEach(order -> order.setStatus(OrderStatus.DELIVERED));
    }
}