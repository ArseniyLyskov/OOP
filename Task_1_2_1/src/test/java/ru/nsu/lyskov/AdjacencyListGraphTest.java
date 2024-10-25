package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки функциональности класса {@link AdjacencyListGraph}.
 * <p>
 * Этот класс использует JUnit 5 для выполнения модульных тестов, обеспечивая правильность работы
 * методов добавления и удаления вершин и рёбер, получения соседей, чтения графа из файла,
 * выполнения топологической сортировки и других операций над графом.
 * </p>
 */
class AdjacencyListGraphTest {

    private AdjacencyListGraph graph; // Экземпляр графа для тестирования

    /**
     * Метод, выполняемый перед каждым тестом. Инициализирует новый граф.
     */
    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph(); // Инициализируем новый граф
    }

    /**
     * Тестирует добавление вершины в граф. Проверяет, что новая вершина была успешно добавлена и
     * не имеет соседей.
     */
    @Test
    void testAddVertex() {
        graph.addVertex(1);
        assertTrue(graph.getNeighbors(1).isEmpty()); // Проверяем, что соседи вершины 1 пусты
    }

    /**
     * Тестирует добавление ребра в граф. Проверяет, что добавленное ребро правильно отражается в
     * списке соседей. Убедитесь, что обратного ребра нет, так как граф ориентированный.
     */
    @Test
    void testAddEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        assertTrue(graph.getNeighbors(1).contains(2)); // Проверяем, что 2 является соседом 1
        assertFalse(graph.getNeighbors(2).contains(1)); // Проверяем, что обратного ребра нет
    }

    /**
     * Тестирует удаление ребра из графа. Проверяет, что удаленное ребро больше не присутствует в
     * списке соседей.
     */
    @Test
    void testRemoveEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.removeEdge(1, 2);
        assertFalse(graph.getNeighbors(1).contains(2)); // Проверяем, что 2 больше не сосед 1
    }

    /**
     * Тестирует удаление вершины из графа. Удаляет вершину и проверяет, что соседние вершины
     * больше не содержат её. Проверяет, что список соседей удаленной вершины равен null.
     */
    @Test
    void testRemoveVertex() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.removeVertex(1);
        assertFalse(graph.getNeighbors(2).contains(1)); // Проверяем, что 1 не является соседом 2
        assertNull(graph.getNeighbors(1)); // Проверяем, что список соседей 1 равен null
    }

    /**
     * Тестирует получение соседей для вершины. Проверяет, что количество соседей и их значения
     * соответствуют ожиданиям.
     */
    @Test
    void testGetNeighbors() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        List<Integer> neighbors = graph.getNeighbors(1);
        assertEquals(1, neighbors.size()); // Проверяем, что количество соседей равно 1
        assertTrue(neighbors.contains(2)); // Проверяем, что 2 является соседом 1
    }

    /**
     * Тестирует топологическую сортировку графа. Проверяет порядок отсортированных вершин после
     * добавления рёбер.
     */
    @Test
    void testTopologicalSort() throws GraphCycleException {
        graph.addVertex(2);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        List<Integer> sorted = graph.topologicalSort();
        assertEquals(List.of(0, 1, 2, 3), sorted); // Проверяем порядок топологической сортировки
    }

    /**
     * Тестирует чтение графа из файла. Проверяет, что рёбра корректно добавлены после чтения из
     * файла.
     */
    @Test
    void testReadFromFile() throws IOException {
        // Создаем временный файл с графом
        String fileName = "InputGraphAdjacencyList.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("4 4\n");
            writer.write("0 1\n");
            writer.write("0 2\n");
            writer.write("1 2\n");
            writer.write("2 3\n");
        }

        graph.readFromFile(fileName); // Читаем граф из файла

        // Проверяем, что рёбра корректно добавлены
        assertEquals(2, graph.getNeighbors(0).size());
        assertTrue(graph.getNeighbors(0).contains(1));
        assertTrue(graph.getNeighbors(0).contains(2));

        assertTrue(graph.getNeighbors(1).contains(2));
        assertTrue(graph.getNeighbors(2).contains(3));
        assertFalse(graph.getNeighbors(2).contains(0));
    }

    /**
     * Тестирует метод equals для сравнения двух графов. Проверяет, что два графа с одинаковыми
     * рёбрами считаются равными.
     */
    @Test
    void testEquals() {
        AdjacencyListGraph graph1 = new AdjacencyListGraph();
        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addEdge(1, 2); // Добавляем ребро в первый граф

        AdjacencyListGraph graph2 = new AdjacencyListGraph();
        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addEdge(1, 2); // Добавляем то же ребро во второй граф

        assertEquals(graph1, graph2); // Проверяем, что графы равны
    }

    /**
     * Тестирует строковое представление графа. Проверяет, что строковое представление графа
     * соответствует ожидаемому формату.
     */
    @Test
    void testToString() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        String expectedOutput =
                "Adjacency List:\n"
                        + "{1=[2], 2=[]}"; // Ожидаемое строковое представление графа
        assertEquals(expectedOutput, graph.toString().trim()); // Проверяем строковое представление
    }
}
