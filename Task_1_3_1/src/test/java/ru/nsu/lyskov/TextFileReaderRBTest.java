package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.classes.TextFileReaderRB;
import ru.nsu.lyskov.exceptions.InvalidBufferProcessorReturn;
import ru.nsu.lyskov.interfaces.BufferInterface;
import ru.nsu.lyskov.interfaces.FileBufferProcessor;

class TextFileReaderRBTest {
    public static final String FILE_NAME = "test.txt";

    public static void createTestFile(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FILE_NAME), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }

    public static void deleteTestFile() throws IOException {
        Files.deleteIfExists(Path.of(FILE_NAME));
    }

    @Test
    void invalidBufferProcessorReturnExceptionTest() throws IOException {
        createTestFile("test");
        assertThrows(InvalidBufferProcessorReturn.class, () ->
                TextFileReaderRB.readFile(FILE_NAME, 5,
                                          (BufferInterface<Character> buffer) -> 0
                ));
    }

    @Test
    void fileReadingViaRingBufferTest() throws IOException {
        int ringBufferCapacity = 5;
        String content = "abeccacbadbabbad";

        StringBuilder readCheck = new StringBuilder();
        Counter bufferReloadingCount = new Counter();
        createTestFile(content);

        FileBufferProcessor processingFunction = (BufferInterface<Character> buffer) -> {
            for (int i = 0; i < buffer.getSize(); i++) {
                readCheck.append(buffer.peek(i));
                System.out.print(buffer.peek(i) + " ");
            }
            System.out.println();
            bufferReloadingCount.increment();
            return buffer.getSize();
        };

        TextFileReaderRB.readFile(FILE_NAME, ringBufferCapacity, processingFunction);

        assertEquals(content, readCheck.toString());
        assertEquals(
                Math.ceil((double) content.length() / ringBufferCapacity),
                bufferReloadingCount.getI()
        );
    }

    @AfterEach
    void closeTestFile() throws IOException {
        deleteTestFile();
    }

    private class Counter {
        private int i = 0;

        void increment() {
            i++;
        }

        int getI() {
            return i;
        }
    }
}