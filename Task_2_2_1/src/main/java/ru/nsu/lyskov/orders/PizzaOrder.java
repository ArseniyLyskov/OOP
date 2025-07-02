package ru.nsu.lyskov.orders;

import ru.nsu.lyskov.logging.PizzeriaLogger;

public class PizzaOrder {
    private final int id;
    private OrderStatus status;

    public PizzaOrder(int id) {
        this.id = id;
        setStatus(OrderStatus.CREATED);
    }

    public void setStatus(OrderStatus newStatus) {
        this.status = newStatus;
        PizzeriaLogger.logOrder(this);
    }

    public int getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }
}