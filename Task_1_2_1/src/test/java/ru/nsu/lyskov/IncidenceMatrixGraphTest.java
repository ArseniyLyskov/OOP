package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки функциональности класса {@link IncidenceMatrixGraph}.
 * <p>
 * Этот класс использует JUnit 5 для выполнения модульных тестов, обеспечивая правильность работы
 * методов добавления и удаления вершин и рёбер, а также чтения графа из файла, выполнения
 * топологической сортировки и других операций над графом.
 * </p>
 */
public class IncidenceMatrixGraphTest {

    private IncidenceMatrixGraph graph; // Экземпляр графа для тестирования

    /**
     * Метод, выполняемый перед каждым тестом. Инициализирует новый граф с 5 вершинами и 5
     * рёбрами.
     */
    @BeforeEach
    public void setUp() {
        graph = new IncidenceMatrixGraph(5, 5); // Инициализация графа с 5 вершинами и 5 рёбрами
    }

    /**
     * Тестирует добавление новой вершины в граф. После добавления новой вершины, проверяет, что
     * список её соседей пуст.
     */
    @Test
    public void testAddVertex() {
        graph.addVertex(5); // Добавляем новую вершину
        assertEquals(0, graph.getNeighbors(5).size()); // Проверяем, что список соседей пуст
    }

    /**
     * Тестирует удаление вершины из графа. Удаляет вершину и проверяет, что соседи её родительской
     * вершины теперь пусты.
     */
    @Test
    public void testRemoveVertex() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.removeVertex(1);
        assertTrue(graph.getNeighbors(0).isEmpty()); // Проверяем, что 0-й не имеет соседей
    }

    /**
     * Тестирует добавление ребра в граф. Добавляет ребро и проверяет, что оно присутствует в
     * списке соседей.
     */
    @Test
    public void testAddEdge() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertTrue(neighbors.contains(1)); // Проверяем, что 1 является соседом 0
    }

    /**
     * Тестирует удаление ребра из графа. Удаляет ребро и проверяет, что оно больше не присутствует
     * в списке соседей.
     */
    @Test
    public void testRemoveEdge() {
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertFalse(graph.getNeighbors(0).contains(1)); // Проверяем, что 1 не является соседом 0
    }

    /**
     * Тестирует получение соседей для вершины. Добавляет несколько рёбер и проверяет, что список
     * соседей содержит все ожидаемые вершины.
     */
    @Test
    public void testGetNeighbors() {
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertTrue(neighbors.contains(1)); // Проверяем, что 1 является соседом 0
        assertTrue(neighbors.contains(2)); // Проверяем, что 2 является соседом 0
    }

    /**
     * Тестирует чтение графа из файла. Создаёт файл с данными графа и проверяет, что соседи
     * полученной вершины соответствуют ожиданиям.
     */
    @Test
    public void testReadFromFile() throws IOException {
        String fileName = "InputGraphIncidenceMatrix.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("3 3\n"); // 3 вершины и 3 рёбра
            writer.write("1 0 1\n");
            writer.write("1 1 0\n");
            writer.write("0 1 1\n");
        }

        graph.readFromFile(fileName);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertTrue(neighbors.contains(2)); // Проверяем, что 0-й имеет соседа 2
        assertTrue(neighbors.contains(1)); // Проверяем, что 0-й имеет соседа 1
    }

    /**
     * Тестирует топологическую сортировку графа. Добавляет рёбра и проверяет порядок
     * отсортированных вершин.
     */
    @Test
    public void testTopologicalSort() {
        graph = new IncidenceMatrixGraph(4, 4);
        graph.addEdge(3, 2);
        graph.addEdge(3, 1);
        graph.addEdge(2, 1);
        graph.addEdge(0, 3);
        List<Integer> sortedList = graph.topologicalSort();
        // Проверяем порядок топологической сортировки
        assertEquals(List.of(0, 3, 2, 1), sortedList);
    }

    /**
     * Тестирует строковое представление графа. Проверяет, что строковое представление графа
     * соответствует ожидаемому формату.
     */
    @Test
    public void testToString() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(4, 2);

        String expectedOutput =
                "Incidence Matrix:\n"
                        + "1 0 0 0 0 \n"
                        + "1 1 0 0 0 \n"
                        + "0 1 1 0 0 \n"
                        + "0 0 0 0 0 \n"
                        + "0 0 1 0 0";
        assertEquals(expectedOutput, graph.toString().trim()); // Проверяем строковое представление
    }

    /**
     * Тестирует метод equals для сравнения двух графов. Создаёт два графа с одинаковыми рёбрами и
     * один граф с различием, проверяет, что методы правильно идентифицируют равенство и
     * неравенство графов.
     */
    @Test
    public void testEquals() {
        IncidenceMatrixGraph graph1 = new IncidenceMatrixGraph(3, 3);
        graph1.addEdge(0, 1);
        graph1.addEdge(1, 2);

        IncidenceMatrixGraph graph2 = new IncidenceMatrixGraph(3, 3);
        graph2.addEdge(0, 1);
        graph2.addEdge(1, 2);

        IncidenceMatrixGraph graph3 = new IncidenceMatrixGraph(3, 3);
        graph3.addEdge(0, 2); // Различное ребро

        assertEquals(graph1, graph2); // graph1 и graph2 должны быть равны
        assertNotEquals(graph1, graph3); // graph1 и graph3 не должны быть равны
    }

}
