package ru.nsu.lyskov;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class LargeFileGenerator {
    public static void generateLargeFile(String fileName, long sizeInBytes, String pattern,
                                         int patternFrequency) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
            Random random = new Random();
            long bytesWritten = 0;
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

            while (bytesWritten < sizeInBytes) {
                if (bytesWritten % patternFrequency == 0 && (bytesWritten + pattern.length()) < sizeInBytes) {
                    writer.write(pattern);
                    bytesWritten += pattern.length();
                } else {
                    char randomChar = alphabet[random.nextInt(alphabet.length)];
                    writer.write(randomChar);
                    bytesWritten++;
                }
            }
        }
    }
}
