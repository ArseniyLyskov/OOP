package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IncidenceMatrixGraphTest {

    private IncidenceMatrixGraph graph;

    @BeforeEach
    public void setUp() {
        // Инициализация графа с 5 вершинами и 5 рёбрами
        graph = new IncidenceMatrixGraph(5, 5);
    }

    @Test
    public void testAddVertex() {
        graph.addVertex(5); // Добавляем новую вершину
        assertEquals(0, graph.getNeighbors(5).size());
    }

    @Test
    public void testRemoveVertex() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.removeVertex(1);
        // После удаления 1-й вершины, 0-й не должен иметь соседей
        assertTrue(graph.getNeighbors(0).isEmpty());
    }

    @Test
    public void testAddEdge() {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertTrue(neighbors.contains(1));
    }

    @Test
    public void testRemoveEdge() {
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        // 1 не должен быть соседом 0 после удаления
        assertFalse(graph.getNeighbors(0).contains(1));
    }

    @Test
    public void testGetNeighbors() {
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertTrue(neighbors.contains(1));
        assertTrue(neighbors.contains(2));
    }

    @Test
    public void testReadFromFile() {
        String fileName = "InputGraphIncidenceMatrix.txt";
        // Создаем файл для теста
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("3 3\n"); // 3 вершины и 3 ребра
            writer.write("1 0 1\n");
            writer.write("1 1 0\n");
            writer.write("0 1 1\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        graph.readFromFile(fileName);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertTrue(neighbors.contains(2)); // Проверяем, что 0-й имеет соседа 2
        assertTrue(neighbors.contains(1)); // Проверяем, что 0-й имеет соседа 1
    }

    @Test
    public void testTopologicalSort() {
        graph = new IncidenceMatrixGraph(4, 4);
        graph.addEdge(3, 2);
        graph.addEdge(3, 1);
        graph.addEdge(2, 1);
        graph.addEdge(0, 3);
        List<Integer> sortedList = graph.topologicalSort();
        assertEquals(List.of(0, 3, 2, 1), sortedList);
    }

    @Test
    public void testToString() {
        // Инициализация графа с 5 вершинами и 5 рёбрами
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(4, 2);

        String expectedOutput = "Incidence Matrix:\n" +
                "1 0 0 0 0 \n" +  // Вершина 0 инцидентна 1 ребру
                "1 1 0 0 0 \n" +  // Вершина 1 инцидентна 1 и 2 рёбрам
                "0 1 1 0 0 \n" +  // Вершина 2 инцидентна 1 и 4 рёбрам
                "0 0 0 0 0 \n" +  // Вершина 3 не инцидентна ни одному ребру
                "0 0 1 0 0";   // Вершина 4 инцидентна 2 ребру
        assertEquals(expectedOutput, graph.toString().trim());
    }

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
