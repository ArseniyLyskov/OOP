package ru.nsu.lyskov;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Класс для анализа производительности различных методов проверки наличия составных чисел в
 * массиве.
 *
 * <p>Позволяет измерять время выполнения алгоритмов и строить графики их эффективности.</p>
 */
public class PerformanceAnalyzer {
    private static final String FileName = "Analysis chart.png";

    /**
     * Возвращает количество доступных процессоров в системе.
     *
     * @return количество доступных процессоров.
     */
    public static int getAvailableProcessorsCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Измеряет время выполнения переданной задачи.
     *
     * @param task выполняемая задача.
     * @return время выполнения в миллисекундах.
     */
    private static long measureExecutionTime(Runnable task) {
        long startTime = System.currentTimeMillis();
        task.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * Генерирует массив простых чисел заданного размера.
     *
     * @param size количество простых чисел в массиве.
     * @return массив простых чисел.
     */
    private static int[] generatePrimes(int size) {
        List<Integer> primes = new ArrayList<>();
        int num = 2;
        while (primes.size() < size) {
            if (!AbstractCompositeChecker.isComposite(num)) {
                primes.add(num);
            }
            num++;
        }
        return primes.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Создает график анализа производительности различных методов проверки составных чисел.
     *
     * @param arraySize размер массива чисел для проверки.
     * @return объект {@link JFreeChart}, представляющий график.
     */
    private static JFreeChart getAnalysisChart(int arraySize) {
        int[] array = generatePrimes(arraySize);
        int xDotsCount = getAvailableProcessorsCount() * 2;
        long streamExecutionTime =
                measureExecutionTime(() -> CompositeChecker.streamCompositeCheck(array));
        long sequentialExecutionTime =
                measureExecutionTime(() -> CompositeChecker.sequentialCompositeCheck(array));

        XYSeries streamLine = new XYSeries("Stream checker");
        XYSeries threadLine = new XYSeries("Thread checker");
        XYSeries sequentialLine = new XYSeries("Sequential checker");
        for (int x = 1; x <= xDotsCount; x++) {
            streamLine.add(x, streamExecutionTime);

            int finalX = x;
            long threadExecutionTime =
                    measureExecutionTime(
                            () -> CompositeChecker.threadCompositeCheck(array, finalX));
            threadLine.add(x, threadExecutionTime);

            sequentialLine.add(x, sequentialExecutionTime);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(streamLine);
        dataset.addSeries(threadLine);
        dataset.addSeries(sequentialLine);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Проверка наличия составного числа в массиве из " + arraySize + " чисел"
                        + "\nКоличество доступных процессоров: " + getAvailableProcessorsCount(),
                "Количество потоков для ThreadCompositeChecker, шт",
                "Время выполнения программ, мс",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(setupAndGetRenderer());

        return chart;
    }

    /**
     * Отображает график анализа производительности в отдельном окне.
     *
     * @param arraySize размер массива чисел для проверки.
     */
    public static void showAnalysisChart(int arraySize) {
        JFrame frame = new JFrame("График");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(getAnalysisChart(arraySize)));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Сохраняет график анализа производительности в PNG-файл.
     *
     * @param arraySize размер массива чисел для проверки.
     */
    public static void saveAnalysisChart(int arraySize) {
        try {
            ChartUtils.saveChartAsPNG(new File(FileName), getAnalysisChart(arraySize), 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Настраивает и возвращает рендерер для графика.
     *
     * @return настроенный {@link XYLineAndShapeRenderer}.
     */
    private static XYLineAndShapeRenderer setupAndGetRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShapesVisible(2, false);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesPaint(2, Color.WHITE);
        for (int i = 0; i < 3; i++) {
            renderer.setSeriesStroke(i, new BasicStroke(4.0f));
        }
        return renderer;
    }

    /**
     * Точка входа в программу. Создает и сохраняет график анализа производительности.
     * Рекомендуется пользоваться {@code psvm} в данном классе, а не методами тестового класса для
     * получения корректной информации.
     *
     * @param args аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        saveAnalysisChart(1_000_000);
    }
}
