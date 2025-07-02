package ru.nsu.lyskov.logging;

import java.util.Scanner;
import ru.nsu.lyskov.orders.PizzaOrder;

public class KeyboardOrderGenerator implements OrderAcceptor {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public PizzaOrder acceptOrder(int orderId) {
        System.out.println("Press \"Enter\" to create an order with ID " + orderId);
        scanner.nextLine();
        return new PizzaOrder(orderId);
    }
}