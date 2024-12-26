package ru.nsu.lyskov;

import static ru.nsu.lyskov.LoggingParameters.LOG_FILE_GENERATING;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class FileGenerator {
    public static final String TEST_FILE_NAME = "test.txt";
    public static final String LARGE_TEST_FILE_NAME = "large_test.txt";
    private static final char UNIQUE_CHARACTER = '∑';
    private static final double PATTERN_PROBABILITY = 0.00000001;

    public static void generateFile(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(TEST_FILE_NAME), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }

    public static void deleteFile() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_NAME));
        Files.deleteIfExists(Path.of(LARGE_TEST_FILE_NAME));
    }

    public static List<Long> generateLargeFile(long sizeInChars, String pattern)
            throws IOException {
        List<Long> subStringIndexes = new ArrayList<>();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(LARGE_TEST_FILE_NAME),
                StandardCharsets.UTF_8
        ))) {

            Random random = new Random();
            int patternLength = pattern.length();
            long charsWritten = 0, logCount = 0;

            ArrayList<Character> characterList = getCharacterList(pattern);
            characterList.remove(random.nextInt(characterList.size()));
            characterList.add(UNIQUE_CHARACTER);

            while (charsWritten < sizeInChars) {
                if (charsWritten + patternLength < sizeInChars
                        && PATTERN_PROBABILITY >= random.nextDouble()) {
                    writer.write(pattern);
                    subStringIndexes.add(charsWritten);
                    charsWritten += patternLength;
                } else {
                    char randomChar = characterList.get(random.nextInt(characterList.size()));
                    writer.write(randomChar);
                    charsWritten++;
                }

                if (LOG_FILE_GENERATING
                        && charsWritten > logCount * sizeInChars * 0.01) {
                    System.out.printf(
                            "\rWritten to file: %d%%",
                            (int) ((double) charsWritten / sizeInChars * 100)
                    );
                    System.out.flush();
                    logCount++;
                }
            }
            if (LOG_FILE_GENERATING) {
                System.out.print("\rFile generated!\n");
            }
        }
        return subStringIndexes;
    }

    private static ArrayList<Character> getCharacterList(String pattern) {
        LinkedHashSet<Character> uniquePatternCharacters = new LinkedHashSet<>();
        for (char c : pattern.toCharArray()) {
            uniquePatternCharacters.add(c);
        }
        ArrayList<Character> characterList = new ArrayList<>(uniquePatternCharacters);
        if (characterList.contains(UNIQUE_CHARACTER)) {
            throw new IllegalArgumentException(UNIQUE_CHARACTER
                                                       + " is a special symbol for testing, "
                                                       + "you can't use it.");
        }
        return characterList;
    }

}
