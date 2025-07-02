package ru.nsu.lyskov.orders;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {
    private final Queue<PizzaOrder> queue = new LinkedList<>();
    private boolean isOrderAcceptingCompleted = false;

    public synchronized void addOrder(PizzaOrder order) {
        if (isOrderAcceptingCompleted) {
            throw new IllegalStateException("Order accepting completed");
        }
        queue.add(order);
        order.setStatus(OrderStatus.ACCEPTED);
        notifyAll();
    }

    public synchronized PizzaOrder takeOrder() throws InterruptedException {
        while (isEmpty() && !isOrderAcceptingCompleted) {
            wait();
        }
        if (isEmpty()) {
            throw new InterruptedException("There are no orders and there will be no new ones");
        }
        return queue.poll();
    }

    public synchronized void completeOrderAccept() {
        isOrderAcceptingCompleted = true;
        notifyAll();
    }

    public synchronized boolean isOrderAcceptingCompleted() {
        return isOrderAcceptingCompleted;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}