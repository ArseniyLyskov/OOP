package ru.nsu.lyskov.pizzeria;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class PizzeriaConfig {
    private int storageCapacity;
    private int workTimeSeconds;
    private final List<Integer> bakerBakingTime = new ArrayList<>();
    private final List<Integer> courierCapacities = new ArrayList<>();

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

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public int getWorkTimeSeconds() {
        return workTimeSeconds;
    }

    public List<Integer> getBakerBakingTime() {
        return bakerBakingTime;
    }

    public List<Integer> getCourierCapacities() {
        return courierCapacities;
    }
}