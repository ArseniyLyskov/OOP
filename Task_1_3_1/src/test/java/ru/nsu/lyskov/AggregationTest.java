package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.nsu.lyskov.FileGenerator.FILE_NAME;
import static ru.nsu.lyskov.FileGenerator.deleteFile;
import static ru.nsu.lyskov.FileGenerator.generateFile;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.classes.BoyerMooreHorspoolAlgorithm;
import ru.nsu.lyskov.classes.RingBuffer;
import ru.nsu.lyskov.classes.TextFileReaderRB;
import ru.nsu.lyskov.interfaces.BufferInterface;

public class AggregationTest {

    @Test
    void invalidCapacityExceptionTest() {
        int bufferCapacity = 2;
        String content = "12345", target = "123";
        assertThrows(IllegalArgumentException.class,
                     () -> parameterizedTest(bufferCapacity, content, target)
        );
    }

    @Test
    void emptyContentTest() throws IOException {
        int bufferCapacity = 10;
        String content = "", target = "anything";
        assertEquals(List.of(), parameterizedTest(bufferCapacity, content, target));
    }

    @Test
    void emptyTargetTest() {
        int bufferCapacity = 10;
        String content = "something", target = "";
        assertThrows(IllegalArgumentException.class,
                     () -> parameterizedTest(bufferCapacity, content, target)
        );
    }

    @Test
    void smallBufferTest() throws IOException {
        int bufferCapacity = 5;
        String content = "abeccacbadbabbad", target = "abbad";
        assertEquals(List.of(11), parameterizedTest(bufferCapacity, content, target));
    }

    @Test
    void bigBufferTest() throws IOException {
        int bufferCapacity = 20;
        String content = "aaaaa", target = "aaa";
        assertEquals(List.of(0, 1, 2), parameterizedTest(bufferCapacity, content, target));
    }

    @Test
    void taskTest() throws IOException {
        int bufferCapacity = 5;
        String content = "абракадабра", target = "бра";
        assertEquals(List.of(1, 8), parameterizedTest(bufferCapacity, content, target));
    }

    @Test
    void differentCharactersTest() throws IOException {
        int bufferCapacity = 4;
        String content = " !@¶Ǣ∑ʩЋ∑∑֍ޘࡤ⅚␀☂∑ヰ鿜", target = "∑";
        assertEquals(List.of(5, 8, 9, 16), parameterizedTest(bufferCapacity, content, target));
    }

    @AfterEach
    void afterEach() throws IOException {
        deleteFile();
    }

    private List<Integer> parameterizedTest(
            int bufferCapacity, String content, String target) throws IOException {

        RingBuffer<Character> ringBuffer = new RingBuffer<>(bufferCapacity);
        BoyerMooreHorspoolAlgorithm algorithm =
                new BoyerMooreHorspoolAlgorithm(ringBuffer, target);

        generateFile(content);
        TextFileReaderRB.readFile(
                FILE_NAME, ringBuffer,
                (BufferInterface<Character> buffer) -> algorithm.getStringPatternShift()
        );

        return algorithm.getResult();
    }
}
