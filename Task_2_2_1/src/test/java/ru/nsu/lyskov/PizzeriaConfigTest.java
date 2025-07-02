package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.lyskov.logging.AutoOrderGenerator;
import ru.nsu.lyskov.pizzeria.PizzeriaConfig;
import ru.nsu.lyskov.pizzeria.PizzeriaSimulator;

class PizzeriaConfigTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldLoadConfigFromFile() throws Exception {
        Path testFile = tempDir.resolve("test_config.json");
        try (InputStream is = getClass().getResourceAsStream("/test_config.json")) {
            Files.copy(Objects.requireNonNull(is), testFile);
        }
        PizzeriaConfig config = PizzeriaConfig.fromFile(testFile.toString());

        assertEquals(5, config.getStorageCapacity());
        assertEquals(10, config.getWorkTimeSeconds());
        assertEquals(List.of(2, 3), config.getBakerBakingTime());
        assertEquals(List.of(1, 2), config.getCourierCapacities());

        PizzeriaSimulator simulator = new PizzeriaSimulator(config, new AutoOrderGenerator());
        simulator.run();
    }

    @Test
    void shouldThrowExceptionWhenFileNotFound() {
        assertThrows(NoSuchFileException.class,
                     () -> PizzeriaConfig.fromFile("non_existent_config.json")
        );
    }
}