package ru.nsu.lyskov;

import java.util.List;

public interface Graph {
    void addVertex(int v);

    void removeVertex(int v);

    void addEdge(int v1, int v2);

    void removeEdge(int v1, int v2);

    List<Integer> getNeighbors(int v);

    void readFromFile(String fileName);

    boolean equals(Object obj);

    String toString();

    List<Integer> topologicalSort();
}