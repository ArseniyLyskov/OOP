package ru.nsu.lyskov;

public final class Constants {
    public static final int COURIER_ORDER_COLLECTION_TIME_MS = 500;
    public static final int COURIER_SINGLE_ORDER_DELIVERY_TIME_MS = 1000;
    public static final int AUTO_ORDER_GENERATOR_INTERVAL_MS = 1000;

    private Constants() {
        throw new AssertionError("Cannot instantiate utility class");
    }
}
