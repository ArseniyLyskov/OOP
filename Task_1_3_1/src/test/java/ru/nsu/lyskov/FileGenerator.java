package ru.nsu.lyskov;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileGenerator {
    public static final String FILE_NAME = "test.txt";
    public static final String LARGE_FILE_NAME = "large_test.txt";
    private static final double PATTERN_PROBABILITY = 0.001;

    public static void generateFile(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(FILE_NAME), StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }

    public static void generateLargeFile(long sizeInChars, String pattern) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(LARGE_FILE_NAME),
                StandardCharsets.UTF_8
        ))) {

            /*Random random = new Random();
            long charsWritten = 0;

            while (bytesWritten < sizeInChars) {
                if (bytesWritten % patternFrequency == 0 && (bytesWritten + pattern.length()) < sizeInChars) {
                    writer.write(pattern);
                    bytesWritten += pattern.length();
                } else {
                    char randomChar = alphabet[random.nextInt(alphabet.length)];
                    writer.write(randomChar);
                    bytesWritten++;
                }
            }*/

        }
    }

    public static void deleteFile() throws IOException {
        Files.deleteIfExists(Path.of(FILE_NAME));
    }
}
