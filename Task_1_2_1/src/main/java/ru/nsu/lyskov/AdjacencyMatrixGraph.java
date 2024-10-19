package ru.nsu.lyskov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class AdjacencyMatrixGraph implements Graph {
    private boolean[][] matrix;
    private int numVertices;

    public AdjacencyMatrixGraph(int numVertices) {
        this.numVertices = numVertices;
        matrix = new boolean[numVertices][numVertices];
    }

    @Override
    public void addVertex(int v) {
        if (v >= numVertices) {
            // Создаем новую увеличенную матрицу смежности
            int newSize = v + 1;
            boolean[][] newMatrix = new boolean[newSize][newSize];

            // Копируем старую матрицу в новую
            for (int i = 0; i < numVertices; i++) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, numVertices);
            }

            // Обновляем матрицу и количество вершин
            matrix = newMatrix;
            numVertices = newSize;
        }
    }


    @Override
    public void removeVertex(int v) {
        // Удаление вершины путем обнуления строки и столбца
        for (int i = 0; i < numVertices; i++) {
            matrix[v][i] = false;
            matrix[i][v] = false;
        }
    }

    @Override
    public void addEdge(int v1, int v2) {
        matrix[v1][v2] = true;
    }

    @Override
    public void removeEdge(int v1, int v2) {
        matrix[v1][v2] = false;
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            if (matrix[v][i]) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Чтение первой строки: количество вершин и рёбер
            String[] firstLine = br.readLine().split(" ");
            numVertices = Integer.parseInt(firstLine[0]);
            int numEdges = Integer.parseInt(firstLine[1]); // Количество рёбер можно использовать для валидации

            // Инициализируем матрицу смежности
            matrix = new boolean[numVertices][numVertices];

            // Чтение последующих строк — это сама матрица смежности
            for (int i = 0; i < numVertices; i++) {
                String[] line = br.readLine().split(" ");
                for (int j = 0; j < numVertices; j++) {
                    matrix[i][j] = line[j].equals("1");
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AdjacencyMatrixGraph that = (AdjacencyMatrixGraph) obj;
        return Arrays.deepEquals(matrix, that.matrix);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                sb.append(matrix[i][j] ? "1 " : "0 ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<Integer> topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[numVertices];  // Для отслеживания посещённых вершин

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

        // Проходим по строке v и ищем все вершины, в которые есть ребро (v -> i)
        for (int i = 0; i < numVertices; i++) {
            if (matrix[v][i] && !visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        stack.push(v);  // Добавляем вершину в стек после всех её соседей
    }
}