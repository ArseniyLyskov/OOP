package ru.nsu.lyskov.orders;

import ru.nsu.lyskov.logging.PizzeriaLogger;

public class PizzaOrder {
    private final int id;
    private volatile OrderStatus status;
    private final Object statusLock = new Object();

    public PizzaOrder(int id) {
        this.id = id;
        status = OrderStatus.CREATED;
        PizzeriaLogger.logOrder(this);
    }

    public void setStatus(OrderStatus newStatus) {
        synchronized (statusLock) {
            try {
                status.validateStatusTransition(newStatus);
                status = newStatus;
            } catch (IllegalStateException e) {
                status = OrderStatus.FAILED;
                throw e;
            } finally {
                PizzeriaLogger.logOrder(this);
            }
        }
    }

    public int getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }
}