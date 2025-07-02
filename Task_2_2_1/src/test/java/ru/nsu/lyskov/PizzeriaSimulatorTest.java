package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.nsu.lyskov.orders.OrderStatus.ACCEPTED;
import static ru.nsu.lyskov.orders.OrderStatus.CREATED;
import static ru.nsu.lyskov.orders.OrderStatus.DELIVERED;
import static ru.nsu.lyskov.orders.OrderStatus.IN_STORAGE;
import static ru.nsu.lyskov.orders.OrderStatus.ON_DELIVERY;
import static ru.nsu.lyskov.orders.OrderStatus.PREPARATION_STARTED;
import static ru.nsu.lyskov.orders.OrderStatus.READY;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.lyskov.logging.AutoOrderGenerator;
import ru.nsu.lyskov.logging.PizzeriaLogger;
import ru.nsu.lyskov.orders.PizzaOrder;
import ru.nsu.lyskov.pizzeria.PizzeriaConfig;
import ru.nsu.lyskov.pizzeria.PizzeriaSimulator;

/**
 * Тестовый класс для проверки корректной работы симулятора пиццерии и связанных компонентов.
 */
class PizzeriaSimulatorTest {
    /**
     * Временная директория для хранения файлов конфигурации во время теста.
     */
    @TempDir
    Path tempDir;

    /**
     * Тест, проверяющий полное выполнение симуляции с использованием тестового конфигурационного
     * файла. Проверяется, что все заказы успешно доставлены.
     *
     * @throws Exception если произошла ошибка при чтении файла конфигурации или запуске
     *                   симулятора
     */
    @Test
    void testSimulation() throws Exception {
        Path testFile = tempDir.resolve("test_config.json");
        try (InputStream is = getClass().getResourceAsStream("/test_config.json")) {
            Files.copy(Objects.requireNonNull(is), testFile);
        }

        PizzeriaConfig config = PizzeriaConfig.fromFile(testFile.toString());
        AutoOrderGenerator generator = new AutoOrderGenerator();
        PizzeriaSimulator simulator = new PizzeriaSimulator(config, generator);
        simulator.run();

        assertTrue(generator.areAllOrdersDelivered());
    }

    /**
     * Тест, проверяющий выброс исключения {@link NoSuchFileException}, если файл конфигурации не
     * найден.
     */
    @Test
    void testNoSuchFile() {
        assertThrows(NoSuchFileException.class,
                     () -> PizzeriaConfig.fromFile("non_existent_config.json")
        );
    }

    /**
     * Тест, проверяющий корректность переходов между статусами заказов. Проверяется, что
     * допустимые переходы не вызывают ошибок, а недопустимые вызывают исключения.
     */
    @Test
    void testOrderStatusTransitions() {
        PizzeriaLogger.logOpening();
        PizzaOrder order1 = new PizzaOrder(1);
        PizzaOrder order2 = new PizzaOrder(2);

        assertDoesNotThrow(() -> {
            order1.setStatus(ACCEPTED);
            order1.setStatus(PREPARATION_STARTED);
            order1.setStatus(READY);
            order1.setStatus(IN_STORAGE);
            order1.setStatus(ON_DELIVERY);
            order1.setStatus(DELIVERED);
        });

        assertThrows(IllegalStateException.class, () -> order1.setStatus(DELIVERED));

        assertThrows(IllegalStateException.class, () -> order2.setStatus(CREATED));
    }
}