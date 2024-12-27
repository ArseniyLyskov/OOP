package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.nsu.lyskov.FileGenerator.LARGE_TEST_FILE_NAME;
import static ru.nsu.lyskov.FileGenerator.TEST_FILE_NAME;
import static ru.nsu.lyskov.FileGenerator.deleteFile;
import static ru.nsu.lyskov.FileGenerator.generateFile;
import static ru.nsu.lyskov.FileGenerator.generateLargeFile;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.logic.BoyerMooreHorspoolAlgorithm;
import ru.nsu.lyskov.logic.TextFileReader;

public class AggregationTest {

    @Test
    void invalidCapacityExceptionTest() {
        int bufferCapacity = 2;
        String content = "12345";
        String target = "123";
        assertThrows(IllegalArgumentException.class,
                     () -> parameterizedSmallTest(bufferCapacity, content, target)
        );
    }

    @Test
    void emptyContentTest() throws IOException {
        int bufferCapacity = 10;
        String content = "";
        String target = "anything";
        assertEquals(List.of(), parameterizedSmallTest(bufferCapacity, content, target));
    }

    @Test
    void emptyTargetTest() {
        int bufferCapacity = 10;
        String content = "something";
        String target = "";
        assertThrows(IllegalArgumentException.class,
                     () -> parameterizedSmallTest(bufferCapacity, content, target)
        );
    }

    @Test
    void smallBufferTest() throws IOException {
        int bufferCapacity = 5;
        String content = "abeccacbadbabbad";
        String target = "abbad";
        assertEquals(List.of(11L), parameterizedSmallTest(bufferCapacity, content, target));
    }

    @Test
    void bigBufferTest() throws IOException {
        int bufferCapacity = 20;
        String content = "aaaaa";
        String target = "aaa";
        assertEquals(List.of(0L, 1L, 2L), parameterizedSmallTest(bufferCapacity, content, target));
    }

    @Test
    void taskTest() throws IOException {
        int bufferCapacity = 5;
        String content = "абракадабра";
        String target = "бра";
        assertEquals(List.of(1L, 8L), parameterizedSmallTest(bufferCapacity, content, target));
    }

    @Test
    void differentCharactersTest() throws IOException {
        int bufferCapacity = 4;
        String content = " !@¶Ǣ∑ʩЋ∑∑֍ޘࡤ⅚␀☂∑ヰ鿜";
        String target = "∑";
        assertEquals(
                List.of(5L, 8L, 9L, 16L),
                parameterizedSmallTest(bufferCapacity, content, target)
        );
    }

    @Disabled("Large test.")
    @Test
    void largeFileTest() throws IOException {
        long sizeInChars = 1024L * 1024 * 1024 * 15;
        int bufferCapacity = 1024 * 1024;
        String pattern = " !qwerty 123456789 !\"£$%^&*()-= <>:{}@?,.;[]'/ S0me_rAnDDom Str|ng! ";
        parameterizedTest(sizeInChars, bufferCapacity, pattern);
    }

    @Test
    void mediumFileTest() throws IOException {
        long sizeInChars = 1024L * 1024 * 50;
        int bufferCapacity = 1024 * 1024;
        String pattern = " !qwerty 123456789 !\"£$%^&*()-= <>:{}@?,.;[]'/ S0me_rAnDDom Str|ng! ";
        parameterizedTest(sizeInChars, bufferCapacity, pattern);
    }

    @Test
    void invalidSymbolInPatternExceptionTest() {
        long sizeInChars = 1024L * 1024 * 10;
        int bufferCapacity = 1024;
        String pattern = " !qwerty 123456789 !\"£$%^&*∑()-= <>:{}@?,.;[]'/ S0me_rAnDDom Str|ng! ";
        assertThrows(
                IllegalArgumentException.class,
                () -> parameterizedTest(sizeInChars, bufferCapacity, pattern)
        );
    }

    @Test
    void invalidTargetSubstringExceptionTest() {
        long sizeInChars = 1024L * 1024 * 10;
        int bufferCapacity = 1024;
        String pattern = "aaaaa";
        assertThrows(
                IllegalArgumentException.class,
                () -> parameterizedTest(sizeInChars, bufferCapacity, pattern)
        );
    }

    @AfterEach
    void afterEach() throws IOException {
        deleteFile();
    }

    private List<Long> parameterizedSmallTest(
            int bufferCapacity, String content, String target) throws IOException {

        BoyerMooreHorspoolAlgorithm algorithm = new BoyerMooreHorspoolAlgorithm(target);
        generateFile(content);
        TextFileReader.readFile(TEST_FILE_NAME, bufferCapacity, algorithm::calculateWindowShift);

        return algorithm.getResult();
    }

    private void parameterizedTest(
            long sizeInChars, int bufferCapacity, String target) throws IOException {

        List<Long> expectedResult = generateLargeFile(sizeInChars, target);
        BoyerMooreHorspoolAlgorithm algorithm = new BoyerMooreHorspoolAlgorithm(target);
        TextFileReader.readFile(
                LARGE_TEST_FILE_NAME,
                bufferCapacity, algorithm::calculateWindowShift
        );
        System.out.println("Substring occurrence indices: " + algorithm.getResult());
        assertEquals(expectedResult, algorithm.getResult());
    }
}
