package ru.nsu.lyskov.orders;

import ru.nsu.lyskov.logging.PizzeriaLogger;

/**
 * Класс представляет заказ на пиццу. Содержит идентификатор заказа и текущий статус. Обеспечивает
 * потокобезопасное обновление статуса с логированием.
 */
public class PizzaOrder {
    private final int id;
    private volatile OrderStatus status;
    private final Object statusLock = new Object();

    /**
     * Создаёт новый заказ.
     *
     * @param id уникальный идентификатор заказа
     */
    public PizzaOrder(int id) {
        this.id = id;
        status = OrderStatus.CREATED;
        PizzeriaLogger.logOrder(this);
    }

    /**
     * Устанавливает новый статус заказа. Перед установкой выполняется проверка допустимости
     * перехода между статусами.
     *
     * @param newStatus новый статус заказа
     */
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

    /**
     * Возвращает идентификатор заказа.
     *
     * @return идентификатор заказа
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает текущий статус заказа.
     *
     * @return текущий статус заказа
     */
    public OrderStatus getStatus() {
        return status;
    }
}
