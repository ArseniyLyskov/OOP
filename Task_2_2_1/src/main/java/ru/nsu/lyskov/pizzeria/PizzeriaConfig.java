package ru.nsu.lyskov.pizzeria;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Класс представляет конфигурацию пиццерии, считываемую из JSON-файла.
 */
public class PizzeriaConfig {
    private int storageCapacity;
    private int workTimeSeconds;
    private final List<Integer> bakerBakingTime = new ArrayList<>();
    private final List<Integer> courierCapacities = new ArrayList<>();

    /**
     * Загружает конфигурацию пиццерии из указанного JSON-файла.
     *
     * @param path путь к JSON-файлу с конфигурацией
     * @return объект {@code PizzeriaConfig}, инициализированный данными из файла
     * @throws Exception если файл не найден, не читается или имеет неверный формат
     */
    public static PizzeriaConfig fromFile(String path) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject config = new JSONObject(content);

        PizzeriaConfig pizzeriaConfig = new PizzeriaConfig();
        pizzeriaConfig.storageCapacity = config.getInt("storage_capacity");
        pizzeriaConfig.workTimeSeconds = config.getInt("work_time_seconds");

        JSONArray bakers = config.getJSONArray("bakers");
        for (int i = 0; i < bakers.length(); i++) {
            pizzeriaConfig.bakerBakingTime.add(bakers.getInt(i));
        }

        JSONArray couriers = config.getJSONArray("couriers");
        for (int i = 0; i < couriers.length(); i++) {
            pizzeriaConfig.courierCapacities.add(couriers.getInt(i));
        }

        return pizzeriaConfig;
    }

    /**
     * Возвращает вместимость склада пиццерии.
     *
     * @return вместимость склада
     */
    public int getStorageCapacity() {
        return storageCapacity;
    }

    /**
     * Возвращает общее время работы пиццерии в секундах.
     *
     * @return время работы в секундах
     */
    public int getWorkTimeSeconds() {
        return workTimeSeconds;
    }

    /**
     * Возвращает список времён приготовления пиццы для каждого пекаря.
     *
     * @return список времён (в секундах) для пекарей
     */
    public List<Integer> getBakerBakingTime() {
        return bakerBakingTime;
    }

    /**
     * Возвращает список "вместимостей" курьеров (сколько пицц каждый из них может взять за раз).
     *
     * @return список вместимостей курьеров
     */
    public List<Integer> getCourierCapacities() {
        return courierCapacities;
    }
}
