package ru.nsu.lyskov.logging;

import static java.lang.Thread.sleep;
import static ru.nsu.lyskov.Constants.AUTO_ORDER_GENERATOR_INTERVAL_MS;
import static ru.nsu.lyskov.orders.OrderStatus.DELIVERED;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.lyskov.orders.PizzaOrder;

/**
 * Автоматический генератор заказов для пиццерии. Позволяет проверять статус доставки всех
 * сгенерированных заказов.
 */
public class AutoOrderGenerator implements OrderAcceptor {
    private final List<PizzaOrder> generatedOrders = new ArrayList<>();

    /**
     * Создает новый заказ с указанным идентификатором после задержки. Заказ автоматически
     * добавляется во внутренний список для отслеживания.
     *
     * @param orderId уникальный идентификатор заказа
     * @return созданный объект PizzaOrder
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    @Override
    public PizzaOrder acceptOrder(int orderId) throws InterruptedException {
        sleep(AUTO_ORDER_GENERATOR_INTERVAL_MS);
        PizzaOrder order = new PizzaOrder(orderId);
        generatedOrders.add(order);
        return order;
    }

    /**
     * Проверяет, все ли сгенерированные заказы были доставлены.
     *
     * @return true если все заказы имеют статус DELIVERED, false в противном случае
     */
    public synchronized boolean areAllOrdersDelivered() {
        return generatedOrders.stream()
                .allMatch(order -> order.getStatus() == DELIVERED);
    }
}