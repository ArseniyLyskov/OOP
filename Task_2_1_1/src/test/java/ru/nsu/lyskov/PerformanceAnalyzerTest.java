package ru.nsu.lyskov;

import org.junit.jupiter.api.Test;

class PerformanceAnalyzerTest {
    @Test
    void testOnMillionElements() {
        PerformanceAnalyzer.saveAnalysisChart(1_000_000);
    }
}