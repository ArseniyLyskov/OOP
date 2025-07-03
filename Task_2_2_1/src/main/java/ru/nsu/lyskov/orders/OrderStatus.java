package ru.nsu.lyskov.orders;

import java.util.Map;
import java.util.Set;

/**
 * Enum, описывающий возможные статусы заказа на пиццу. Включает описание статусов и допустимые
 * переходы между ними.
 */
public enum OrderStatus {
    CREATED("Order created"),
    ACCEPTED("Order accepted"),
    PREPARATION_STARTED("Pizza preparation started"),
    READY("Pizza ready"),
    IN_STORAGE("Pizza placed in storage"),
    ON_DELIVERY("Pizza taken by courier"),
    DELIVERED("Pizza delivered"),
    FAILED("Order failed");

    private static final Map<OrderStatus, Set<OrderStatus>> ALLOWED_TRANSITIONS = Map.of(
            CREATED, Set.of(ACCEPTED),
            ACCEPTED, Set.of(PREPARATION_STARTED),
            PREPARATION_STARTED, Set.of(READY),
            READY, Set.of(IN_STORAGE),
            IN_STORAGE, Set.of(ON_DELIVERY),
            ON_DELIVERY, Set.of(DELIVERED, FAILED)
    );

    private final String description;

    /**
     * Конструктор перечисления.
     *
     * @param description текстовое описание статуса
     */
    OrderStatus(String description) {
        this.description = description;
    }

    /**
     * Проверяет допустимость перехода от текущего статуса к новому. Выбрасывает исключение, если
     * переход недопустим.
     *
     * @param newStatus новый статус, к которому предполагается переход
     * @throws IllegalStateException если переход недопустим
     */
    public void validateStatusTransition(OrderStatus newStatus) {
        if (this == DELIVERED || this == FAILED) {
            throw new IllegalStateException(
                    "Cannot change status from terminal status " + newStatus);
        }

        if (!ALLOWED_TRANSITIONS.getOrDefault(this, Set.of()).contains(newStatus)) {
            throw new IllegalStateException(
                    "Invalid transition from " + this + " to " + newStatus);
        }
    }

    /**
     * Возвращает строковое описание статуса.
     *
     * @return описание статуса
     */
    public String getDescription() {
        return description;
    }
}
