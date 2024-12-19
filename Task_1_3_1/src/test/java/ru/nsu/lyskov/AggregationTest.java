package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.nsu.lyskov.TextFileReaderRBTest.FILE_NAME;
import static ru.nsu.lyskov.TextFileReaderRBTest.createTestFile;
import static ru.nsu.lyskov.TextFileReaderRBTest.deleteTestFile;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.classes.BoyerMooreHorspoolAlgorithm;
import ru.nsu.lyskov.classes.TextFileReaderRB;

public class AggregationTest {

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
    void closeTestFile() throws IOException {
        deleteTestFile();
    }

    private List<Integer> parameterizedTest(
            int bufferCapacity, String content, String target) throws IOException {

        BoyerMooreHorspoolAlgorithm algorithm = new BoyerMooreHorspoolAlgorithm(target);
        createTestFile(content);
        TextFileReaderRB.readFile(FILE_NAME, bufferCapacity, algorithm::getStringPatternShift);
        return algorithm.getResult();
    }
}
