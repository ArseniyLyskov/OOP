package ru.nsu.lyskov.logging;

import java.time.Duration;
import java.time.Instant;
import ru.nsu.lyskov.orders.OrderStatus;
import ru.nsu.lyskov.orders.PizzaOrder;

public class PizzeriaLogger {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static Instant startTime = null;

    public static void logOpening() {
        startTime = Instant.now();
        System.out.printf("%-8s | %sTHE PIZZERIA IS OPEN!%s%n",
                          formatDuration(), ANSI_RED, ANSI_RESET
        );
    }

    public static void logOrder(PizzaOrder order) {
        String highlightColor = order.getStatus() == OrderStatus.FAILED ? ANSI_RED : ANSI_GREEN;
        System.out.printf("%-8s | ID: %s%-2d%s | STATUS: %s%s%s%n",
                          formatDuration(),
                          highlightColor, order.getId(), ANSI_RESET,
                          highlightColor, order.getStatus().getDescription(), ANSI_RESET
        );
    }

    public static void logStoppingOrderReceiving() {
        System.out.printf("%-8s | %sORDERS ARE NO LONGER ACCEPTED!%s%n",
                          formatDuration(), ANSI_RED, ANSI_RESET
        );
    }

    public static void logClosing() {
        System.out.printf("%-8s | %sTHE PIZZERIA IS CLOSED!%s%n",
                          formatDuration(), ANSI_RED, ANSI_RESET
        );
    }

    private static String formatDuration() {
        checkSettingUp();
        Duration elapsed = Duration.between(startTime, Instant.now());
        long minutes = elapsed.toMinutes();
        int seconds = elapsed.toSecondsPart();
        int millis = elapsed.toMillisPart();

        return String.format("%02d:%02d.%03d", minutes, seconds, millis);
    }

    private static void checkSettingUp() {
        if (startTime == null) {
            throw new AssertionError("First you need to log the opening");
        }
    }
}