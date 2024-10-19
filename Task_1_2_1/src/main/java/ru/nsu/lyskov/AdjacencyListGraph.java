package ru.nsu.lyskov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Класс для представления графа с использованием списка смежности.
 * <p>
 * Реализует интерфейс {@link Graph} и поддерживает основные операции над графами,
 * такие как добавление и удаление вершин и рёбер, получение соседей вершин,
 * чтение графа из файла и выполнение топологической сортировки.
 * </p>
 */
public class AdjacencyListGraph implements Graph {
    private final Map<Integer, List<Integer>> adjList;

    /**
     * Конструктор, создающий новый граф с пустым списком смежности.
     */
    public AdjacencyListGraph() {
        adjList = new HashMap<>();
    }

    /**
     * Добавляет новую вершину в граф.
     *
     * @param v номер вершины, которую нужно добавить
     */
    @Override
    public void addVertex(int v) {
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    /**
     * Удаляет вершину из графа.
     *
     * @param v номер вершины, которую нужно удалить
     */
    @Override
    public void removeVertex(int v) {
        adjList.remove(v); // Удаляем вершину из графа
        // Удаляем из смежных списков других вершин
        adjList.values().forEach(neighbors -> neighbors.remove(Integer.valueOf(v)));
    }

    /**
     * Добавляет направленное ребро между двумя вершинами.
     *
     * @param v1 номер первой вершины
     * @param v2 номер второй вершины
     */
    @Override
    public void addEdge(int v1, int v2) {
        adjList.get(v1).add(v2);
    }

    /**
     * Удаляет направленное ребро между двумя вершинами.
     *
     * @param v1 номер первой вершины
     * @param v2 номер второй вершины
     */
    @Override
    public void removeEdge(int v1, int v2) {
        adjList.get(v1).remove(Integer.valueOf(v2));
    }

    /**
     * Возвращает список соседей заданной вершины.
     *
     * @param v номер вершины
     * @return список соседей указанной вершины, или null, если вершина отсутствует
     */
    @Override
    public List<Integer> getNeighbors(int v) {
        return adjList.get(v); // Вернёт null, если вершина отсутствует
    }

    /**
     * Считывает граф из файла.
     *
     * @param fileName имя файла, из которого будет прочитан граф
     */
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

    /**
     * Сравнивает два графа на равенство.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны, иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AdjacencyListGraph that = (AdjacencyListGraph) obj;
        return Objects.equals(adjList, that.adjList);
    }

    /**
     * Возвращает строковое представление графа.
     *
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        return adjList.toString();
    }

    /**
     * Выполняет топологическую сортировку графа.
     *
     * @return список вершин в порядке топологической сортировки
     */
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

    /**
     * Рекурсивный вспомогательный метод для выполнения топологической сортировки графа.
     *
     * @param v       Текущая вершина, которую нужно посетить.
     * @param visited Набор посещенных вершин, чтобы избежать циклических ссылок и бесконечной рекурсии.
     * @param stack   Стек, в который добавляются вершины после посещения всех их соседей,
     *                чтобы получить порядок их обработки в топологической сортировке.
     */
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
