package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.logic.BoyerMooreHorspoolAlgorithm;
import ru.nsu.lyskov.logic.RingBuffer;

class BoyerMooreHorspoolAlgorithmTest {

    @Test
    void invalidCapacityExceptionTest() {
        int bufferCapacity = 2;
        String content = "12345";
        String target = "123";
        assertThrows(
                IllegalArgumentException.class,
                () -> parameterizedTest(bufferCapacity, content, target)
        );
    }

    @Test
    void emptyContentTest() {
        int bufferCapacity = 10;
        String content = "";
        String target = "anything";
        assertEquals(List.of(), parameterizedTest(bufferCapacity, content, target));
    }

    @Test
    void emptyTargetTest() {
        int bufferCapacity = 10;
        String content = "something";
        String target = "";
        assertThrows(
                IllegalArgumentException.class,
                () -> parameterizedTest(bufferCapacity, content, target)
        );
    }

    @Test
    void smallBufferTest() {
        int bufferCapacity = 5;
        String content = "abeccacbadbabbad";
        String target = "abbad";
        assertEquals(List.of(11L), parameterizedTest(bufferCapacity, content, target));
    }

    @Test
    void bigBufferTest() {
        int bufferCapacity = 20;
        String content = "aaaaa";
        String target = "aaa";
        assertEquals(List.of(0L, 1L, 2L), parameterizedTest(bufferCapacity, content, target));
    }

    @Test
    void taskTest() {
        int bufferCapacity = 5;
        String content = "абракадабра";
        String target = "бра";
        assertEquals(List.of(1L, 8L), parameterizedTest(bufferCapacity, content, target));
    }

    @Test
    void differentCharactersTest() {
        int bufferCapacity = 4;
        String content = " !@¶Ǣ∑ʩЋ∑∑֍ޘࡤ⅚␀☂∑ヰ鿜";
        String target = "∑";
        assertEquals(List.of(5L, 8L, 9L, 16L), parameterizedTest(bufferCapacity, content, target));
    }

    private List<Long> parameterizedTest(
            int bufferCapacity, String content, String target) {

        int contentReadingIndex = 0, contentLength = content.length();
        RingBuffer<Character> ringBuffer = new RingBuffer<>(bufferCapacity);
        BoyerMooreHorspoolAlgorithm algorithm = new BoyerMooreHorspoolAlgorithm(target);

        System.out.println("\nReading a line into a buffer...");
        while (contentReadingIndex < contentLength) {
            ringBuffer.put(content.charAt(contentReadingIndex));
            if (ringBuffer.isFull()) {
                int charSkipCount = algorithm.calculateWindowShift(ringBuffer);
                System.out.print("Skipped " + charSkipCount + " characters: ");
                for (int i = 0; i < charSkipCount; i++) {
                    System.out.print(ringBuffer.pop() + " ");
                }
                System.out.println();
            }
            contentReadingIndex++;
        }

        System.out.println("\nProcessing the remaining elements in the buffer...");
        while (!ringBuffer.isEmpty()) {
            int charSkipCount = algorithm.calculateWindowShift(ringBuffer);
            System.out.print("Skipped " + charSkipCount + " characters: ");
            for (int i = 0; i < charSkipCount; i++) {
                System.out.print(ringBuffer.pop() + " ");
            }
            System.out.println();
        }

        System.out.println("The \"target\" string is a substring of \"content\" at "
                                   + "the following indices: " + algorithm.getResult() + "\n");
        return algorithm.getResult();
    }

}