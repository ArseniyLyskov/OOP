package ru.nsu.lyskov.orders;

import java.util.Map;
import java.util.Set;

public enum OrderStatus {
    CREATED("Order created"),
    ACCEPTED("Order accepted"),
    PREPARATION_STARTED("Pizza preparation started"),
    READY("Pizza ready"),
    IN_STORAGE("Pizza placed in storage"),
    ON_DELIVERY("Pizza taken by courier"),
    DELIVERED("Pizza delivered"),
    FAILED("Order failed");

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

    private static final Map<OrderStatus, Set<OrderStatus>> ALLOWED_TRANSITIONS = Map.of(
            CREATED, Set.of(ACCEPTED),
            ACCEPTED, Set.of(PREPARATION_STARTED),
            PREPARATION_STARTED, Set.of(READY),
            READY, Set.of(IN_STORAGE),
            IN_STORAGE, Set.of(ON_DELIVERY),
            ON_DELIVERY, Set.of(DELIVERED, FAILED)
    );

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}