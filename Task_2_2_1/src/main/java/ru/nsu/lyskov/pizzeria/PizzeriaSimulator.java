package ru.nsu.lyskov.pizzeria;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.lyskov.ConcurrentLatch;
import ru.nsu.lyskov.bakers.Baker;
import ru.nsu.lyskov.couriers.Courier;
import ru.nsu.lyskov.logging.OrderAcceptor;
import ru.nsu.lyskov.logging.PizzeriaLogger;
import ru.nsu.lyskov.orders.OrderQueue;
import ru.nsu.lyskov.orders.PizzaOrder;
import ru.nsu.lyskov.storage.Storage;

/**
 * Класс симуляции работы пиццерии. Запускает пекарей и курьеров, принимает заказы в течение
 * заданного времени и управляет завершением работы.
 */
public class PizzeriaSimulator implements Runnable {
    private final ConcurrentLatch bakersLatch;
    private final ConcurrentLatch couriersLatch;
    private final OrderAcceptor acceptor;
    private final long orderAcceptingStopTime;
    private int ordersAccepted;

    private final OrderQueue orderQueue = new OrderQueue();
    private final List<Baker> bakers = new ArrayList<>();
    private final Storage storage;
    private final List<Courier> couriers = new ArrayList<>();

    /**
     * Создает новый экземпляр симулятора пиццерии на основе переданной конфигурации.
     *
     * @param config   конфигурация пиццерии
     * @param acceptor механизм принятия заказов
     */
    public PizzeriaSimulator(PizzeriaConfig config, OrderAcceptor acceptor) {
        this.acceptor = acceptor;
        int bakersCount = config.getBakerBakingTime().size();
        int couriersCount = config.getCourierCapacities().size();

        storage = new Storage(config.getStorageCapacity());
        bakersLatch = new ConcurrentLatch(bakersCount);
        couriersLatch = new ConcurrentLatch(couriersCount);

        for (int i = 0; i < bakersCount; i++) {
            bakers.add(new Baker(config.getBakerBakingTime().get(i),
                                 orderQueue, storage, bakersLatch
            ));
        }

        for (int i = 0; i < couriersCount; i++) {
            couriers.add(new Courier(config.getCourierCapacities().get(i),
                                     storage, couriersLatch
            ));
        }

        orderAcceptingStopTime = System.currentTimeMillis() + config.getWorkTimeSeconds() * 1000L;
    }

    /**
     * Запускает симуляцию работы пиццерии: запускает потоки пекарей и курьеров; принимает заказы в
     * течение установленного времени; завершает прием заказов; ожидает завершения всех пекарей и
     * курьеров.
     */
    @Override
    public void run() {
        PizzeriaLogger.logOpening();
        bakers.forEach(b -> new Thread(b).start());
        couriers.forEach(c -> new Thread(c).start());

        while (shouldAcceptOrders()) {
            try {
                PizzaOrder order = acceptor.acceptOrder(++ordersAccepted);
                orderQueue.addOrder(order);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        PizzeriaLogger.logStoppingOrderReceiving();
        orderQueue.completeOrderAccept();
        awaitBakers();
        storage.completeAddition();
        awaitCouriers();
        PizzeriaLogger.logClosing();
    }

    private boolean shouldAcceptOrders() {
        return System.currentTimeMillis() < orderAcceptingStopTime
                && !Thread.currentThread().isInterrupted();
    }

    private void awaitBakers() {
        try {
            bakersLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void awaitCouriers() {
        try {
            couriersLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
