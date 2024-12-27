package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.nsu.lyskov.FileGenerator.TEST_FILE_NAME;
import static ru.nsu.lyskov.FileGenerator.deleteFile;
import static ru.nsu.lyskov.FileGenerator.generateFile;

import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.exceptions.IllegalFunctionReturnException;
import ru.nsu.lyskov.interfaces.BufferInterface;
import ru.nsu.lyskov.interfaces.FileBufferProcessor;
import ru.nsu.lyskov.logic.TextFileReader;

class TextFileReaderTest {

    @Test
    void bufferProcessorReturns0Test() throws IOException {
        generateFile("test");
        assertThrows(IllegalFunctionReturnException.class, () ->
                TextFileReader.readFile(TEST_FILE_NAME, 5,
                                        (BufferInterface<Character> buffer) -> 0
                ));
    }

    @Test
    void bufferProcessorReturnsTooMuchTest() throws IOException {
        generateFile("tes");
        assertThrows(IllegalFunctionReturnException.class, () ->
                TextFileReader.readFile(TEST_FILE_NAME, 4,
                                        (BufferInterface<Character> buffer) -> 2
                ));
    }

    @Test
    void fileReadingViaRingBufferTest() throws IOException {
        int ringBufferCapacity = 5;
        String content = "abeccacbadbabbad";

        StringBuilder readCheck = new StringBuilder();
        Counter bufferReloadingCount = new Counter();
        generateFile(content);

        FileBufferProcessor processingFunction = (BufferInterface<Character> buffer) -> {
            for (int i = 0; i < buffer.getSize(); i++) {
                readCheck.append(buffer.peek(i));
                System.out.print(buffer.peek(i) + " ");
            }
            System.out.println();
            bufferReloadingCount.increment();
            return buffer.getSize();
        };

        TextFileReader.readFile(TEST_FILE_NAME, ringBufferCapacity, processingFunction);

        assertEquals(content, readCheck.toString());
        assertEquals(
                Math.ceil((double) content.length() / ringBufferCapacity),
                bufferReloadingCount.getI()
        );
    }

    @AfterEach
    void afterEach() throws IOException {
        deleteFile();
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