package ru.nsu.lyskov.logic;

import static ru.nsu.lyskov.LoggingParameters.LOG_FILE_READING;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import ru.nsu.lyskov.exceptions.IllegalFunctionReturnException;
import ru.nsu.lyskov.interfaces.FileBufferProcessor;

/**
 * Класс, реализующий чтение текстового файла с использованием кольцевого буфера и функционального
 * интерфейса для его обработки.
 */
public class TextFileReader {

    /**
     * Чтение файла.
     *
     * @param filePath           путь к файлу.
     * @param ringBufferCapacity ёмкость кольцевого буфера, используемого для чтения.
     * @param processingFunction функция-обработчик буфера.
     * @throws IOException возможное IO-исключение.
     */
    public static void readFile(
            String filePath, int ringBufferCapacity,
            FileBufferProcessor processingFunction)
            throws IOException {

        long fileSize = Files.size(Paths.get(filePath));
        long bytesRead = 0;
        int logCount = 0;
        RingBuffer<Character> ringBuffer = new RingBuffer<>(ringBufferCapacity);
        int c;

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            while ((c = reader.read()) != -1) {
                ringBuffer.put((char) c);

                bytesRead++;
                if (LOG_FILE_READING && bytesRead > logCount * fileSize * 0.01) {
                    System.out.printf(
                            "\rRead from file: %d%%",
                            (int) ((double) bytesRead / fileSize * 100)
                    );
                    System.out.flush();
                    logCount++;
                }

                if (!ringBuffer.isFull()) {
                    continue;
                }
                applyProcessingFunction(ringBuffer, processingFunction);
            }
            while (!ringBuffer.isEmpty()) {
                applyProcessingFunction(ringBuffer, processingFunction);
            }
            if (LOG_FILE_READING) {
                System.out.print("\rFile reading complete!\n");
            }
        }
    }

    private static void applyProcessingFunction(RingBuffer<Character> ringBuffer,
                                                FileBufferProcessor processingFunction) {
        int charSkipCount = processingFunction.processBuffer(ringBuffer);
        if (charSkipCount <= 0) {
            throw new IllegalFunctionReturnException(
                    "The buffer processing function returned a value <= 0.");
        }
        for (int i = 0; i < charSkipCount; i++) {
            if (ringBuffer.isEmpty()) {
                throw new IllegalFunctionReturnException(
                        "The buffer handling function returned a value greater than "
                                + "the number of characters in the buffer.");
            }
            ringBuffer.pop();
        }
    }
}
