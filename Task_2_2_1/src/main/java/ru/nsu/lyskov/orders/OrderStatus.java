package ru.nsu.lyskov.orders;

public enum OrderStatus {
    CREATED("Order created"),
    ACCEPTED("Order accepted"),
    PREPARATION_STARTED("Pizza preparation started"),
    READY("Pizza ready"),
    IN_STORAGE("Pizza placed in storage"),
    ON_DELIVERY("Pizza taken by courier"),
    DELIVERED("Pizza delivered"),
    FAILED("Order failed");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}