package ru.nsu.lyskov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdjacencyListGraph implements Graph {
    private final Map<Integer, List<Integer>> adjList;

    public AdjacencyListGraph() {
        adjList = new HashMap<>();
    }

    @Override
    public void addVertex(int v) {
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    @Override
    public void removeVertex(int v) {
        adjList.remove(v); // Удаляем вершину из графа
        // Удаляем из смежных списков других вершин
        adjList.values().forEach(neighbors -> neighbors.remove(Integer.valueOf(v)));
    }

    @Override
    public void addEdge(int v1, int v2) {
        adjList.get(v1).add(v2);
    }

    @Override
    public void removeEdge(int v1, int v2) {
        adjList.get(v1).remove(Integer.valueOf(v2));
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        return adjList.get(v); // Вернёт null, если вершина отсутствует
    }

    @Override
    public void readFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Чтение первой строки: количество вершин и рёбер
            String[] firstLine = br.readLine().split(" ");
            int numVertices = Integer.parseInt(firstLine[0]);
            int numEdges = Integer.parseInt(firstLine[1]);

            // Инициализация списка смежности
            for (int i = 0; i < numVertices; i++) {
                addVertex(i); // Добавляем вершины в граф
            }

            // Чтение следующих строк — это описание рёбер
            for (int i = 0; i < numEdges; i++) {
                String[] edge = br.readLine().split(" ");
                int v1 = Integer.parseInt(edge[0]);  // Первая вершина ребра
                int v2 = Integer.parseInt(edge[1]);  // Вторая вершина ребра

                // Добавляем рёбра в список смежности
                adjList.get(v1).add(v2);
                // Если граф неориентированный, добавляем ребро в обе стороны
                // adjList.get(v2).add(v1);
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AdjacencyListGraph that = (AdjacencyListGraph) obj;
        return Objects.equals(adjList, that.adjList);
    }

    @Override
    public String toString() {
        return adjList.toString();
    }

    @Override
    public List<Integer> topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        Set<Integer> visited = new HashSet<>();

        for (Integer v : adjList.keySet()) {
            if (!visited.contains(v)) {
                topologicalSortUtil(v, visited, stack);
            }
        }

        List<Integer> sortedList = new ArrayList<>();
        while (!stack.isEmpty()) {
            sortedList.add(stack.pop());
        }
        return sortedList;
    }

    private void topologicalSortUtil(int v, Set<Integer> visited, Stack<Integer> stack) {
        visited.add(v);

        for (Integer neighbor : getNeighbors(v)) {
            if (!visited.contains(neighbor)) {
                topologicalSortUtil(neighbor, visited, stack);
            }
        }

        stack.push(v);
    }
}