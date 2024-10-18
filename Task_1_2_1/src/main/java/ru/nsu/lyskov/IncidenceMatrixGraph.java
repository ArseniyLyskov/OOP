package ru.nsu.lyskov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class IncidenceMatrixGraph implements Graph {
    private boolean[][] incidenceMatrix;
    private int numVertices;
    private int numEdges;

    public IncidenceMatrixGraph(int numVertices, int numEdges) {
        this.numVertices = numVertices;
        this.numEdges = numEdges;
        incidenceMatrix = new boolean[numVertices][numEdges];
    }

    @Override
    public void addVertex(int v) {
        // Добавление вершины
    }

    @Override
    public void removeVertex(int v) {
        // Удаление вершины
    }

    @Override
    public void addEdge(int v1, int v2) {
        // Добавление ребра
    }

    @Override
    public void removeEdge(int v1, int v2) {
        // Удаление ребра
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        // Возвращает соседей
        return null;
    }

    @Override
    public void readFromFile(String fileName) {
        // Чтение из файла
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        IncidenceMatrixGraph that = (IncidenceMatrixGraph) obj;
        return Arrays.deepEquals(incidenceMatrix, that.incidenceMatrix);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numEdges; j++) {
                sb.append(incidenceMatrix[i][j] ? "1 " : "0 ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<Integer> topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        List<Integer> sortedList = new ArrayList<>();
        while (!stack.isEmpty()) {
            sortedList.add(stack.pop());
        }
        return sortedList;
    }

    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;

        // Проходим по матрице инцидентности и ищем рёбра, инцидентные вершине v
        for (int edge = 0; edge < numEdges; edge++) {
            if (incidenceMatrix[v][edge]) {
                // Найти другую вершину, соединённую с этим ребром
                for (int u = 0; u < numVertices; u++) {
                    if (u != v && incidenceMatrix[u][edge] && !visited[u]) {
                        topologicalSortUtil(u, visited, stack);
                    }
                }
            }
        }

        stack.push(v);  // Добавляем вершину в стек после всех её соседей
    }
}