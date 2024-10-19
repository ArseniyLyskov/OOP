package ru.nsu.lyskov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class IncidenceMatrixGraph implements Graph {
    private boolean[][] incidenceMatrix; // Матрица инцидентности
    private int numVertices; // Количество вершин
    private int numEdges;    // Количество рёбер
    private int edgeCount;   // Текущий индекс для рёбер

    public IncidenceMatrixGraph(int numVertices, int numEdges) {
        this.numVertices = numVertices;
        this.numEdges = numEdges;
        this.incidenceMatrix = new boolean[numVertices][numEdges];
        this.edgeCount = 0;
    }

    @Override
    public void addVertex(int v) {
        // Добавляем новую вершину, увеличиваем размерность матрицы инцидентности
        if (v >= numVertices) {
            boolean[][] newMatrix = new boolean[v + 1][numEdges];
            for (int i = 0; i < numVertices; i++) {
                newMatrix[i] = Arrays.copyOf(incidenceMatrix[i], numEdges);
            }
            incidenceMatrix = newMatrix;
            numVertices = v + 1;
        }
    }

    @Override
    public void removeVertex(int v) {
        if (v >= numVertices) return;
        for (int i = 0; i < numEdges; i++) {
            incidenceMatrix[v][i] = false; // Удаляем все инцидентные рёбра
        }
    }

    @Override
    public void addEdge(int v1, int v2) {
        if (v1 >= numVertices || v2 >= numVertices || edgeCount >= numEdges) {
            return;
        }
        incidenceMatrix[v1][edgeCount] = true;
        incidenceMatrix[v2][edgeCount] = true;
        edgeCount++;
    }

    @Override
    public void removeEdge(int v1, int v2) {
        for (int i = 0; i < numEdges; i++) {
            if (incidenceMatrix[v1][i] && incidenceMatrix[v2][i]) {
                incidenceMatrix[v1][i] = false;
                incidenceMatrix[v2][i] = false;
                break; // Удаляем первое найденное ребро между v1 и v2
            }
        }
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        List<Integer> neighbors = new ArrayList<>();
        if (v >= numVertices) return neighbors;

        for (int i = 0; i < numEdges; i++) {
            if (incidenceMatrix[v][i]) {
                // Ищем вершины, инцидентные ребру i
                for (int u = 0; u < numVertices; u++) {
                    if (u != v && incidenceMatrix[u][i]) {
                        neighbors.add(u);
                    }
                }
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
            numEdges = Integer.parseInt(firstLine[1]);

            // Инициализируем матрицу инцидентности
            incidenceMatrix = new boolean[numVertices][numEdges];

            // Чтение последующих строк — это сама матрица инцидентности
            for (int i = 0; i < numVertices; i++) {
                String[] line = br.readLine().split(" ");
                for (int j = 0; j < numEdges; j++) {
                    incidenceMatrix[i][j] = line[j].equals("1");
                }
            }

            // После успешного чтения обнуляем счётчик рёбер
            edgeCount = numEdges;

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        IncidenceMatrixGraph that = (IncidenceMatrixGraph) obj;
        return numVertices == that.numVertices &&
                numEdges == that.numEdges &&
                Arrays.deepEquals(incidenceMatrix, that.incidenceMatrix);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Incidence Matrix:\n");
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
