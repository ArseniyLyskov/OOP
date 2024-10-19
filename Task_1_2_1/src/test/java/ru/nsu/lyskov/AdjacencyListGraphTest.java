package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdjacencyListGraphTest {

    private AdjacencyListGraph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph();
    }

    @Test
    void testAddVertex() {
        graph.addVertex(1);
        assertTrue(graph.getNeighbors(1).isEmpty());
    }

    @Test
    void testAddEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        assertTrue(graph.getNeighbors(1).contains(2));
        assertFalse(graph.getNeighbors(2).contains(1)); // Ориентированный граф
    }

    @Test
    void testRemoveEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.removeEdge(1, 2);
        assertFalse(graph.getNeighbors(1).contains(2));
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.removeVertex(1);
        assertFalse(graph.getNeighbors(2).contains(1));
        assertNull(graph.getNeighbors(1));
    }

    @Test
    void testGetNeighbors() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        List<Integer> neighbors = graph.getNeighbors(1);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(2));
    }

    @Test
    void testTopologicalSort() {
        graph.addVertex(2);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        List<Integer> sorted = graph.topologicalSort();
        assertEquals(List.of(0, 1, 2, 3), sorted);
    }

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

        // Читаем граф из файла
        graph.readFromFile(fileName);

        // Проверяем, что рёбра корректно добавлены
        assertEquals(2, graph.getNeighbors(0).size());
        assertTrue(graph.getNeighbors(0).contains(1));
        assertTrue(graph.getNeighbors(0).contains(2));

        assertTrue(graph.getNeighbors(1).contains(2));
        assertTrue(graph.getNeighbors(2).contains(3));
        assertFalse(graph.getNeighbors(2).contains(0)); // Ориентированный граф
    }

    @Test
    void testEquals() {
        AdjacencyListGraph graph1 = new AdjacencyListGraph();
        AdjacencyListGraph graph2 = new AdjacencyListGraph();

        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addEdge(1, 2);

        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addEdge(1, 2);

        assertEquals(graph1, graph2);
    }

    @Test
    void testToString() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        String expected = "{1=[2], 2=[]}";
        assertEquals(expected, graph.toString());
    }
}
