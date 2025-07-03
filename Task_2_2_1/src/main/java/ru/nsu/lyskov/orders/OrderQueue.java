package ru.nsu.lyskov.orders;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Класс представляет потокобезопасную очередь заказов пиццы.
 */
public class OrderQueue {
    private final Queue<PizzaOrder> queue = new LinkedList<>();
    private boolean isOrderAcceptingCompleted = false;

    /**
     * Добавляет заказ в очередь.
     *
     * @param order заказ на пиццу, который необходимо добавить
     * @throws IllegalStateException если приём заказов завершён
     */
    public synchronized void addOrder(PizzaOrder order) {
        if (isOrderAcceptingCompleted) {
            throw new IllegalStateException("Order accepting completed");
        }
        queue.add(order);
        order.setStatus(OrderStatus.ACCEPTED);
        notifyAll();
    }

    /**
     * Извлекает заказ из очереди. Если очередь пуста и приём заказов ещё не завершён — поток
     * ожидает.
     *
     * @return следующий заказ из очереди
     * @throws InterruptedException если новых заказов больше не будет
     */
    public synchronized PizzaOrder takeOrder() throws InterruptedException {
        while (isEmpty() && !isOrderAcceptingCompleted) {
            wait();
        }
        if (isEmpty()) {
            throw new InterruptedException("There are no orders and there will be no new ones");
        }
        return queue.poll();
    }

    /**
     * Завершает приём заказов. Пробуждает все потоки, ожидающие новые заказы.
     */
    public synchronized void completeOrderAccept() {
        isOrderAcceptingCompleted = true;
        notifyAll();
    }

    /**
     * Проверяет, завершён ли приём заказов.
     *
     * @return {@code true}, если приём заказов завершён, иначе {@code false}
     */
    public synchronized boolean isOrderAcceptingCompleted() {
        return isOrderAcceptingCompleted;
    }

    /**
     * Проверяет, пуста ли очередь заказов.
     *
     * @return {@code true}, если очередь пуста, иначе {@code false}
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
