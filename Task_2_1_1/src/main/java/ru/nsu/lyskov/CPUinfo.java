package ru.nsu.lyskov;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CPUinfo {
    public static void main(String[] args) {
        System.out.println("Логических ядер: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Физических ядер: " + getPhysicalCores());
    }

    private static int getPhysicalCores() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            Process process = null;
            if (os.contains("win")) {
                process = Runtime.getRuntime().exec("WMIC CPU Get NumberOfCores");
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                process = Runtime.getRuntime().exec("lscpu");
            }
            if (process == null) return -1;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.toLowerCase().contains("core(s) per socket")) {
                        return Integer.parseInt(line.replaceAll("[^0-9]", ""));
                    } else if (line.toLowerCase().contains("numberofcores")) {
                        reader.readLine(); // Пропускаем заголовок
                        return Integer.parseInt(reader.readLine().trim());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
