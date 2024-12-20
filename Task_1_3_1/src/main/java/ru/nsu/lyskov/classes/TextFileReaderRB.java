package ru.nsu.lyskov.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import ru.nsu.lyskov.exceptions.IllegalFunctionReturnException;
import ru.nsu.lyskov.interfaces.FileBufferProcessor;

public class TextFileReaderRB {

    public static void readFile(
            String filePath, int ringBufferCapacity,
            FileBufferProcessor processingFunction)
            throws IOException {

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {

            RingBuffer<Character> ringBuffer = new RingBuffer<>(ringBufferCapacity);
            int c;
            while ((c = reader.read()) != -1) {
                ringBuffer.put((char) c);
                if (!ringBuffer.isFull()) {
                    continue;
                }
                applyProcessingFunction(ringBuffer, processingFunction);
            }
            while (!ringBuffer.isEmpty()) {
                applyProcessingFunction(ringBuffer, processingFunction);
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
