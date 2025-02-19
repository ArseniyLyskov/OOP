package ru.nsu.lyskov;

import org.junit.jupiter.api.Test;

class PerformanceAnalyzerTest {
    private static final int arraySize = 10_000;

    @Test
    void testSave() {
        PerformanceAnalyzer.saveAnalysisChart(arraySize);
    }

    @Test
    void testShow() {
        PerformanceAnalyzer.showAnalysisChart(arraySize);
    }
}