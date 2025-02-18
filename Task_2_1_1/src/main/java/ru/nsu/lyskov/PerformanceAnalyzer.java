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

public class PerformanceAnalyzer {
    private static final String FileName = "Analysis chart.png";

    public static int getAvailableProcessorsCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    private static long measureExecutionTime(Runnable task) {
        long startTime = System.currentTimeMillis();
        task.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

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
        for (int x = 1; x <= xDotsCount; x ++) {
            streamLine.add(x, streamExecutionTime);

            int finalX = x;
            long threadExecutionTime =
                    measureExecutionTime(() -> CompositeChecker.threadCompositeCheck(array, finalX));
            threadLine.add(x, threadExecutionTime);

            sequentialLine.add(x, sequentialExecutionTime);
        }

        // Объединяем серии в dataset
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(streamLine);
        dataset.addSeries(threadLine);
        dataset.addSeries(sequentialLine);

        // Создаём график
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Проверка наличия составного числа в массиве из " + arraySize + " чисел",
                "Количество потоков для ThreadCompositeChecker, шт",
                "Время выполнения программ, мс",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        // Делаем точки на кривой видимыми
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(setupAndGetRenderer());

        return chart;
    }

    public static void showAnalysisChart(int arraySize) {
        // Отображаем окно
        JFrame frame = new JFrame("График");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(getAnalysisChart(arraySize)));
        frame.pack();
        frame.setVisible(true);
    }

    public static void saveAnalysisChart(int arraySize) {
        try {
            ChartUtils.saveChartAsPNG(new File(FileName), getAnalysisChart(arraySize), 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}
