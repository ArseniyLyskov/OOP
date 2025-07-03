package ru.nsu.lyskov.bakers;

import ru.nsu.lyskov.ConcurrentLatch;
import ru.nsu.lyskov.orders.OrderQueue;
import ru.nsu.lyskov.orders.OrderStatus;
import ru.nsu.lyskov.orders.PizzaOrder;
import ru.nsu.lyskov.storage.Storage;

/**
 * Класс, представляющий пекаря в пиццерии. Пекарь получает заказы из очереди, "готовит" пиццы и
 * помещает их на склад.
 */
public class Baker implements Runnable {
    private final ConcurrentLatch completionLatch;
    private final int bakingTime;
    private final OrderQueue orderQueue;
    private final Storage storage;

    /**
     * Конструктор пекаря.
     *
     * @param bakingTime      время приготовления одной пиццы в миллисекундах
     * @param orderQueue      очередь заказов
     * @param storage         склад для готовых пицц
     * @param completionLatch счётчик для отслеживания завершения работы
     */
    public Baker(int bakingTime, OrderQueue orderQueue, Storage storage,
                 ConcurrentLatch completionLatch) {
        this.bakingTime = bakingTime;
        this.orderQueue = orderQueue;
        this.storage = storage;
        this.completionLatch = completionLatch;
    }

    /**
     * Основной метод работы пекаря. Работает до тех пор, пока в очереди есть заказы или пока не
     * завершён приём новых заказов.
     */
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