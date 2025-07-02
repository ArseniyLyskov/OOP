package ru.nsu.lyskov.storage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import ru.nsu.lyskov.orders.OrderStatus;
import ru.nsu.lyskov.orders.PizzaOrder;

public class Storage {
    private final Queue<PizzaOrder> pizzaOrders = new LinkedList<>();
    private final int capacity;
    private boolean isAdditionCompleted = false;

    public Storage(int capacity) {
        this.capacity = capacity;
    }

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

    public synchronized void completeAddition() {
        isAdditionCompleted = true;
        notifyAll();
    }

    public synchronized boolean isAdditionCompleted() {
        return isAdditionCompleted;
    }

    public synchronized boolean isEmpty() {
        return pizzaOrders.isEmpty();
    }

    private synchronized boolean isFull() {
        return pizzaOrders.size() >= capacity;
    }
}