package ru.nsu.lyskov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphTest {

    private AdjacencyMatrixGraph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph(4); // Инициализируем граф с 4 вершинами
    }

    @Test
    void testAddVertex() {
        graph.addVertex(2); // Добавляем вершину, которая уже существует
        graph.addVertex(4); // Добавляем новую вершину
        assertNotNull(graph.getNeighbors(4)); // Проверяем, что новая вершина существует
    }

    @Test
    void testAddEdge() {
        graph.addEdge(0, 1);
        assertTrue(graph.getNeighbors(0).contains(1));
        assertFalse(graph.getNeighbors(1).contains(0)); // Проверяем, что обратного ребра нет
    }

    @Test
    void testRemoveEdge() {
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertFalse(graph.getNeighbors(0).contains(1));
    }

    @Test
    void testRemoveVertex() {
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.removeVertex(0);
        assertFalse(graph.getNeighbors(1).contains(0));
        assertFalse(graph.getNeighbors(2).contains(0));
    }

    @Test
    void testGetNeighbors() {
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(1));
        assertTrue(neighbors.contains(2));
    }

    @Test
    void testTopologicalSort() {
        graph = new AdjacencyMatrixGraph(3); // Создаем граф с 3 вершинами
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        List<Integer> sorted = graph.topologicalSort();
        assertEquals(List.of(1, 2, 0), sorted);
    }

    @Test
    void testReadFromFile() throws IOException {
        // Создаем временный файл с графом
        String fileName = "InputGraphAdjacencyMatrix.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("4 7\n");
            writer.write("0 1 0 1\n");
            writer.write("0 0 1 1\n");
            writer.write("0 1 0 0\n");
            writer.write("1 0 1 0\n");
        }

        // Читаем граф из файла
        graph.readFromFile(fileName);

        // Проверяем, что рёбра корректно добавлены
        assertTrue(graph.getNeighbors(0).contains(1));
        assertFalse(graph.getNeighbors(0).contains(2));
        assertTrue(graph.getNeighbors(0).contains(3));
        assertTrue(graph.getNeighbors(3).contains(0));
        assertFalse(graph.getNeighbors(3).contains(1));
    }

    @Test
    void testEquals() {
        AdjacencyMatrixGraph graph1 = new AdjacencyMatrixGraph(4);
        AdjacencyMatrixGraph graph2 = new AdjacencyMatrixGraph(4);

        graph1.addEdge(1, 2);
        graph2.addEdge(1, 2);

        assertEquals(graph1, graph2);
    }

    @Test
    void testToString() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        String expected = """
                0 1 0 0\s
                0 0 1 0\s
                0 0 0 0\s
                0 0 0 0\s
                """;
        assertEquals(expected, graph.toString());
    }
}
