package ru.nsu.lyskov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import ru.nsu.lyskov.exceptions.DataOverreadingException;
import ru.nsu.lyskov.exceptions.DataOverwritingException;

public class TextFileReaderRB {

    public static void readFile(
            String filePath,
            RingBuffer<Character> ringBuffer,
            FileBufferProcessor processingFunction)
            throws IOException, DataOverwritingException, DataOverreadingException {

        BufferedReader reader =
                new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8));
        int c;
        do {
            while ((c = reader.read()) != -1 && !ringBuffer.isFull()) {
                ringBuffer.write((char) c);
            }

            int charSkipCount = processingFunction.processBuffer(ringBuffer, c == -1);
            for (int i = 0; i < charSkipCount; i++) {
                ringBuffer.read();
            }

        } while (c != -1 || !ringBuffer.isEmpty());
    }
}
