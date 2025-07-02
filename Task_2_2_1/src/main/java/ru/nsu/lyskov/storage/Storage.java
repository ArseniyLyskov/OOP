package ru.nsu.lyskov.storage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import ru.nsu.lyskov.orders.OrderStatus;
import ru.nsu.lyskov.orders.PizzaOrder;

/**
 * Класс, представляющий хранилище готовых пицц.
 */
public class Storage {
    private final Queue<PizzaOrder> pizzaOrders = new LinkedList<>();
    private final int capacity;
    private boolean isAdditionCompleted = false;

    /**
     * Создаёт хранилище с заданной вместимостью.
     *
     * @param capacity максимальное количество пицц, которое может храниться
     */
    public Storage(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Добавляет пиццу в хранилище. Если хранилище заполнено, поток ожидает освобождения места,
     * кроме случая, когда приём пицц завершён.
     *
     * @param order заказ пиццы для добавления
     * @throws InterruptedException если приём пицц завершён или поток прерван
     */
    public synchronized void addPizza(PizzaOrder order) throws InterruptedException {
        while (isFull() && !isAdditionCompleted) {
            wait();
        }
        if (isAdditionCompleted) {
            throw new InterruptedException("Receiving completed");
        }
        pizzaOrders.add(order);
        order.setStatus(OrderStatus.IN_STORAGE);
        notifyAll();
    }

    /**
     * Забирает из хранилища пиццы в количестве до {@code maxCount}. Если хранилище пустое, поток
     * ожидает появления пицц, если приём пицц не завершён.
     *
     * @param maxCount максимальное количество пицц для забирания
     * @return список взятых пицц
     * @throws InterruptedException если хранилище пусто и приём пицц завершён, или поток прерван
     */
    public synchronized List<PizzaOrder> takePizzas(int maxCount) throws InterruptedException {
        while (isEmpty() && !isAdditionCompleted) {
            wait();
        }
        if (isEmpty()) {
            throw new InterruptedException("No more pizzas");
        }

        List<PizzaOrder> taken = new ArrayList<>();
        while (!isEmpty() && taken.size() < maxCount) {
            taken.add(pizzaOrders.poll());
        }

        notifyAll();
        return taken;
    }

    /**
     * Помечает, что приём пицц в хранилище завершён. Пробуждает все ожидающие потоки.
     */
    public synchronized void completeAddition() {
        isAdditionCompleted = true;
        notifyAll();
    }

    /**
     * Проверяет, завершён ли приём пицц в хранилище.
     *
     * @return {@code true}, если приём пицц завершён, иначе {@code false}
     */
    public synchronized boolean isAdditionCompleted() {
        return isAdditionCompleted;
    }

    /**
     * Проверяет, пусто ли хранилище.
     *
     * @return {@code true}, если в хранилище нет пицц, иначе {@code false}
     */
    public synchronized boolean isEmpty() {
        return pizzaOrders.isEmpty();
    }

    private synchronized boolean isFull() {
        return pizzaOrders.size() >= capacity;
    }
}
