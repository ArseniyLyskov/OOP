package ru.nsu.lyskov;

import org.junit.jupiter.api.Test;

/**
 * Набор тестов для проверки функциональности класса {@link PerformanceAnalyzer}. Не рекомендуется
 * полагаться на информацию по анализу производительности, полученную в результате тестирования,
 * так как она отличается от фактической. Вместо этого рекомендуется использование {@code psvm}
 * методов в классе {@link PerformanceAnalyzer}.
 */
class PerformanceAnalyzerTest {
    /**
     * Размер массива, используемый в тестах для анализа производительности. Размер массива
     * небольшой для быстрого тестирования (стоит иметь в виду, что по этой причине
     * последовательные вычисления могут быть даже быстрее многопоточных).
     */
    private static final int arraySize = 10_000;

    /**
     * Тестирует сохранение графика анализа производительности в файл.
     */
    @Test
    void testSave() {
        PerformanceAnalyzer.saveAnalysisChart(arraySize);
    }

    /**
     * Тестирует отображение графика анализа производительности в окне.
     */
    @Test
    void testShow() {
        PerformanceAnalyzer.showAnalysisChart(arraySize);
    }
}
