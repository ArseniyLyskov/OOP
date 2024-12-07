package ru.nsu.lyskov;

import java.io.*;
import java.util.*;

public class RingBufferBMH {

    public static List<Integer> find(String fileName, String target) throws IOException {
        List<Integer> result = new ArrayList<>();
        int targetLength = target.length();
        final int bufferSize = 1024; // Размер буфера
        char[] ringBuffer = new char[bufferSize + targetLength]; // Кольцевой буфер
        int[] shiftTable = buildShiftTable(target); // Таблица смещений
        int ringStart = 0, ringEnd = 0; // Границы данных в буфере
        int indexInFile = 0; // Индекс текущего символа в файле

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))) {
            int charsRead;
            while ((charsRead = reader.read(ringBuffer, ringEnd, Math.min(bufferSize - ringEnd, bufferSize))) > 0) {
                ringEnd = (ringEnd + charsRead) % bufferSize;
                processBuffer(ringBuffer, ringStart, ringEnd, bufferSize, target, shiftTable, result, targetLength, indexInFile);
                indexInFile += charsRead;

                // Обновление ringStart, когда подстрока может быть разбита на два буфера
                ringStart = (indexInFile - targetLength + 1) % bufferSize;
            }
        }

        // Обработка оставшихся символов в буфере
        processBuffer(ringBuffer, ringStart, ringEnd, bufferSize, target, shiftTable, result, targetLength, indexInFile);

        return result;
    }

    private static void processBuffer(char[] ringBuffer, int ringStart, int ringEnd, int bufferSize, String target,
                                      int[] shiftTable, List<Integer> result, int targetLength, int indexInFile) {
        int i = ringStart + targetLength - 1; // Начинаем с конца первого окна
        int processed = 0; // Количество обработанных символов
        int totalToProcess = (ringEnd - ringStart + bufferSize) % bufferSize; // Общий размер данных для обработки

        while (processed < totalToProcess) {
            int j = targetLength - 1;

            // Сравнение символов с шаблоном
            while (j >= 0 && ringBuffer[(i - (targetLength - 1) + j + bufferSize) % bufferSize] == target.charAt(j)) {
                j--;
            }

            // Если совпадение найдено
            if (j < 0) {
                result.add(indexInFile + (i - targetLength + 1 + bufferSize) % bufferSize);
                i = (i + 1) % bufferSize; // Сдвигаем i на один символ вперёд
                processed++;
            } else {
                // Используем таблицу смещений для пропуска
                int shift = shiftTable[Character.codePointAt(ringBuffer, i % bufferSize)];
                i = (i + shift) % bufferSize; // Смещаем индекс
                processed += shift; // Увеличиваем обработанные символы
            }
        }
    }

    private static int[] buildShiftTable(String target) {
        int targetLength = target.length();
        int[] table = new int[256];
        Arrays.fill(table, targetLength); // По умолчанию смещение = длина шаблона
        for (int i = 0; i < targetLength - 1; i++) {
            table[target.charAt(i)] = targetLength - 1 - i;
        }
        return table;
    }

}
