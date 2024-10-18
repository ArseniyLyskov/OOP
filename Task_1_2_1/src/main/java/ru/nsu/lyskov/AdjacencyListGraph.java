package ru.nsu.lyskov;

import java.util.*;

public class AdjacencyListGraph implements Graph {
    private Map<Integer, List<Integer>> adjList;

    public AdjacencyListGraph() {
        adjList = new HashMap<>();
    }

    @Override
    public void addVertex(int v) {
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    @Override
    public void removeVertex(int v) {
        adjList.values().forEach(e -> e.remove(Integer.valueOf(v)));
        adjList.remove(v);
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
        return adjList.getOrDefault(v, new ArrayList<>());
    }

    @Override
    public void readFromFile(String fileName) {
        // Чтение из файла
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