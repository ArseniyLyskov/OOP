package ru.nsu.lyskov;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RingBufferBMHTest {

    // Вспомогательный метод для создания файла с заданным содержимым
    private static void createTestFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
            writer.write(content);
        }
    }

    @Test
    @DisplayName("Маленький пример: 'abrakadabra' с шаблоном 'bra'")
    void testSmallExample() throws IOException {
        String fileName = "test1.txt";
        createTestFile(fileName, "abrakadabra");

        List<Integer> expected = Arrays.asList(1, 8);
        List<Integer> actual = RingBufferBMH.find(fileName, "bra");

        assertEquals(expected, actual);
        Files.deleteIfExists(Path.of(fileName));
    }

    @Test
    @DisplayName("Стыковка буферов: шаблон пересекает границу буферов")
    void testPatternCrossingBufferBoundary() throws IOException {
        String fileName = "test_boundary.txt";
        int bufferSize = 4096; // Размер буфера, используемый в алгоритме
        String prefix = "x".repeat(bufferSize - 8); // Заполняем почти весь буфер символами
        String content = "xbra" + prefix + "brabrabra"; // Шаблон пересекает границу буфера

        createTestFile(fileName, content);

        List<Integer> expected = Collections.singletonList(bufferSize - 1);
        List<Integer> actual = RingBufferBMH.find(fileName, "bra");

        assertEquals(expected, actual);
        Files.deleteIfExists(Path.of(fileName));
    }

    @Test
    @DisplayName("Большой файл: проверка на 10ГБ с частым шаблоном")
    @Disabled("Тест отключён из-за высокой нагрузки на ресурсы")
    void testLargeFile() throws IOException {
        String fileName = "large_input.txt";
        long sizeInBytes = 10L * 1024 * 1024 * 1024; // 10 ГБ
        String pattern = "pattern";
        int patternFrequency = 5000;

        LargeFileGenerator.generateLargeFile(fileName, sizeInBytes, pattern, patternFrequency);

        List<Integer> result = RingBufferBMH.find(fileName, pattern);

        // Проверяем, что количество совпадений соответствует ожиданиям
        long expectedOccurrences = sizeInBytes / patternFrequency;
        assertEquals(expectedOccurrences, result.size());

        Files.deleteIfExists(Path.of(fileName));
    }

    @Test
    @DisplayName("Файл с повторяющимся содержимым")
    void testRepeatingContent() throws IOException {
        String fileName = "test3.txt";
        createTestFile(fileName, "abcabcabcabc");

        List<Integer> expected = Arrays.asList(0, 3, 6, 9);
        List<Integer> actual = RingBufferBMH.find(fileName, "abc");

        assertEquals(expected, actual);
        Files.deleteIfExists(Path.of(fileName));
    }
}
