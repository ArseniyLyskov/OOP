package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.nsu.lyskov.classes.BoyerMooreHorspoolAlgorithm;
import ru.nsu.lyskov.classes.RingBuffer;

class BoyerMooreHorspoolAlgorithmTest {

    @Test
    void smallBufferTest() {
        int bufferCapacity = 5;
        String content = "abeccacbadbabbad", target = "abbad";
        assertEquals(List.of(11), parameterizedTest(bufferCapacity, content, target));
    }

    @Test
    void bigBufferTest() {
        int bufferCapacity = 20;
        String content = "aaaaa", target = "aaa";
        assertEquals(List.of(0, 1, 2), parameterizedTest(bufferCapacity, content, target));
    }

    @Test
    void taskTest() {
        int bufferCapacity = 5;
        String content = "абракадабра", target = "бра";
        assertEquals(List.of(1, 8), parameterizedTest(bufferCapacity, content, target));
    }

    @Test
    void differentCharactersTest() {
        int bufferCapacity = 4;
        String content = " !@¶Ǣ∑ʩЋ∑∑֍ޘࡤ⅚␀☂∑ヰ鿜", target = "∑";
        assertEquals(List.of(5, 8, 9, 16), parameterizedTest(bufferCapacity, content, target));
    }

    private List<Integer> parameterizedTest(
            int bufferCapacity, String content, String target) {

        int contentReadingIndex = 0, contentLength = content.length();
        RingBuffer<Character> ringBuffer = new RingBuffer<>(bufferCapacity);
        BoyerMooreHorspoolAlgorithm algorithm =
                new BoyerMooreHorspoolAlgorithm(ringBuffer, target);

        System.out.println("\nReading a line into a buffer...");
        while (contentReadingIndex < contentLength) {
            ringBuffer.put(content.charAt(contentReadingIndex));
            if (ringBuffer.isFull()) {
                int charSkipCount = algorithm.getStringPatternShift();
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
            int charSkipCount = algorithm.getStringPatternShift();
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