package ru.nsu.lyskov.bakers;

import ru.nsu.lyskov.ConcurrentLatch;
import ru.nsu.lyskov.orders.OrderQueue;
import ru.nsu.lyskov.orders.OrderStatus;
import ru.nsu.lyskov.orders.PizzaOrder;
import ru.nsu.lyskov.storage.Storage;

public class Baker implements Runnable {
    private final ConcurrentLatch completionLatch;
    private final int bakingTime;
    private final OrderQueue orderQueue;
    private final Storage storage;

    public Baker(int bakingTime, OrderQueue orderQueue, Storage storage,
                 ConcurrentLatch completionLatch) {
        this.bakingTime = bakingTime;
        this.orderQueue = orderQueue;
        this.storage = storage;
        this.completionLatch = completionLatch;
    }

    @Override
    public void run() {
        try {
            while (!orderQueue.isEmpty() || !orderQueue.isOrderAcceptingCompleted()) {
                PizzaOrder order = orderQueue.takeOrder();
                preparePizza(order);
                storage.addPizza(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            completionLatch.countDown();
        }
    }

    private void preparePizza(PizzaOrder order) throws InterruptedException {
        order.setStatus(OrderStatus.PREPARATION_STARTED);
        Thread.sleep(bakingTime);
        order.setStatus(OrderStatus.READY);
    }
}