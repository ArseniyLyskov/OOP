package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки функциональности класса {@link AdjacencyMatrixGraph}.
 * <p>
 * Этот класс использует JUnit 5 для выполнения модульных тестов, обеспечивая правильность работы
 * методов добавления и удаления вершин и рёбер, а также получения соседей, чтения графа из файла,
 * выполнения топологической сортировки и других операций над графом.
 * </p>
 */
class AdjacencyMatrixGraphTest {

    private AdjacencyMatrixGraph graph; // Экземпляр графа для тестирования

    /**
     * Метод, выполняемый перед каждым тестом. Инициализирует новый граф с 4 вершинами.
     */
    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph(4); // Инициализируем граф с 4 вершинами
    }

    /**
     * Тестирует добавление вершины в граф. Проверяет, что новая вершина была успешно добавлена.
     * Попытка добавить существующую вершину не должна вызывать ошибок.
     */
    @Test
    void testAddVertex() {
        graph.addVertex(2); // Добавляем вершину, которая уже существует
        graph.addVertex(4); // Добавляем новую вершину
        assertNotNull(graph.getNeighbors(4)); // Проверяем, что новая вершина существует
    }

    /**
     * Тестирует добавление ребра в граф. Проверяет, что добавленное ребро правильно отражается в
     * списке соседей. Убедитесь, что обратного ребра нет.
     */
    @Test
    void testAddEdge() {
        graph.addEdge(0, 1);
        assertTrue(graph.getNeighbors(0).contains(1)); // Проверяем, что 1 является соседом 0
        assertFalse(graph.getNeighbors(1).contains(0)); // Проверяем, что обратного ребра нет
    }

    /**
     * Тестирует удаление ребра из графа. Проверяет, что удаленное ребро больше не присутствует в
     * списке соседей.
     */
    @Test
    void testRemoveEdge() {
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertFalse(graph.getNeighbors(0).contains(1)); // Проверяем, что 1 больше не сосед 0
    }

    /**
     * Тестирует удаление вершины из графа. Удаляет вершину и проверяет, что соседние вершины
     * больше не содержат её.
     */
    @Test
    void testRemoveVertex() {
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.removeVertex(0);
        assertFalse(graph.getNeighbors(1).contains(0)); // Проверяем, что 0 не является соседом 1
        assertFalse(graph.getNeighbors(2).contains(0)); // Проверяем, что 0 не является соседом 2
    }

    /**
     * Тестирует получение соседей для вершины. Проверяет, что количество соседей и их значения
     * соответствуют ожиданиям.
     */
    @Test
    void testGetNeighbors() {
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(2, neighbors.size()); // Проверяем, что количество соседей равно 2
        assertTrue(neighbors.contains(1)); // Проверяем, что 1 является соседом 0
        assertTrue(neighbors.contains(2)); // Проверяем, что 2 является соседом 0
    }

    /**
     * Тестирует топологическую сортировку графа. Проверяет порядок отсортированных вершин после
     * добавления рёбер.
     */
    @Test
    void testTopologicalSort() throws GraphCycleException {
        graph = new AdjacencyMatrixGraph(3); // Создаем граф с 3 вершинами
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        List<Integer> sorted = graph.topologicalSort();
        assertEquals(List.of(1, 2, 0), sorted); // Проверяем порядок топологической сортировки
    }

    /**
     * Тестирует чтение графа из файла. Проверяет, что рёбра корректно добавлены после чтения из
     * файла.
     */
    @Test
    void testReadFromFile() throws IOException {
        String fileName = "InputGraphAdjacencyMatrix.txt"; // Имя временного файла
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("4 7\n");
            writer.write("0 1 0 1\n");
            writer.write("0 0 1 1\n");
            writer.write("0 1 0 0\n");
            writer.write("1 0 1 0\n");
        }

        graph.readFromFile(fileName); // Читаем граф из файла

        // Проверяем, что рёбра корректно добавлены
        assertTrue(graph.getNeighbors(0).contains(1));
        assertFalse(graph.getNeighbors(0).contains(2));
        assertTrue(graph.getNeighbors(0).contains(3));
        assertTrue(graph.getNeighbors(3).contains(0));
        assertFalse(graph.getNeighbors(3).contains(1));
    }

    /**
     * Тестирует метод equals для сравнения двух графов. Проверяет, что два графа с одинаковыми
     * рёбрами считаются равными.
     */
    @Test
    void testEquals() {
        AdjacencyMatrixGraph graph1 = new AdjacencyMatrixGraph(4);
        AdjacencyMatrixGraph graph2 = new AdjacencyMatrixGraph(4);

        graph1.addEdge(1, 2); // Добавляем ребро в первый граф
        graph2.addEdge(1, 2); // Добавляем то же ребро во второй граф

        assertEquals(graph1, graph2); // Проверяем, что графы равны
    }

    /**
     * Тестирует строковое представление графа. Проверяет, что строковое представление графа
     * соответствует ожидаемому формату.
     */
    @Test
    void testToString() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        String expectedOutput =
                "Adjacency Matrix:\n"
                        + "0 1 0 0 \n"
                        + "0 0 1 0 \n"
                        + "0 0 0 0 \n"
                        + "0 0 0 0";
        assertEquals(expectedOutput, graph.toString().trim()); // Проверяем строковое представление
    }
}
