package ru.nsu.lyskov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Класс для представления графа с использованием матрицы инцидентности.
 * <p>
 * Реализует интерфейс {@link Graph} и поддерживает основные операции над графами, такие как
 * добавление и удаление вершин и рёбер, получение соседей вершин, чтение графа из файла и
 * выполнение топологической сортировки.
 * </p>
 */
public class IncidenceMatrixGraph implements Graph {
    private int[][] incidenceMatrix; // Матрица инцидентности
    private int numVertices;             // Количество вершин
    private int numEdges;                // Количество рёбер
    private int edgeCount;               // Текущий индекс для рёбер

    /**
     * Конструктор, создающий новый граф с заданным количеством вершин и рёбер.
     *
     * @param numVertices количество вершин в графе
     * @param numEdges    количество рёбер в графе
     */
    public IncidenceMatrixGraph(int numVertices, int numEdges) {
        this.numVertices = numVertices;
        this.numEdges = numEdges;
        this.incidenceMatrix = new int[numVertices][numEdges];
        this.edgeDirections = new HashMap<>();
        this.edgeCount = 0;
    }

    /**
     * Добавляет новую вершину в граф.
     *
     * @param v номер вершины, которую нужно добавить
     */
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

    /**
     * Удаляет вершину из графа.
     *
     * @param v номер вершины, которую нужно удалить
     */
    @Override
    public void removeVertex(int v) {
        if (v >= numVertices) {
            return;
        }

        // Удаляем все рёбра, инцидентные вершине v
        List<Integer> edgesToRemove = new ArrayList<>();
        for (int i = 0; i < numEdges; i++) {
            if (incidenceMatrix[v][i]) {
                edgesToRemove.add(i); // Помечаем для удаления
                incidenceMatrix[v][i] = false;
            }
        }

        // Удаляем рёбра из edgeDirections
        for (Integer edge : edgesToRemove) {
            edgeDirections.remove(edge);
        }
    }

    /**
     * Добавляет направленное ребро между двумя вершинами.
     *
     * @param v1 номер первой вершины
     * @param v2 номер второй вершины
     */
    @Override
    public void addEdge(int v1, int v2) {
        if (v1 >= numVertices || v2 >= numVertices || edgeCount >= numEdges) {
            return;
        }
        incidenceMatrix[v1][edgeCount] = -1;
        incidenceMatrix[v2][edgeCount] = 1;
        edgeCount++;
    }

    /**
     * Удаляет направленное ребро между двумя вершинами.
     *
     * @param v1 номер первой вершины
     * @param v2 номер второй вершины
     */
    @Override
    public void removeEdge(int v1, int v2) {
        for (int i = 0; i < numEdges; i++) {
            if (incidenceMatrix[v1][i] && incidenceMatrix[v2][i]) {
                incidenceMatrix[v1][i] = false;
                incidenceMatrix[v2][i] = false;
                edgeDirections.remove(i);
                break; // Удаляем первое найденное ребро между v1 и v2
            }
        }
    }

    /**
     * Возвращает список соседей заданной вершины.
     *
     * @param v номер вершины
     * @return список соседей указанной вершины, или null, если вершина отсутствует
     */
    @Override
    public List<Integer> getNeighbors(int v) {
        List<Integer> neighbors = new ArrayList<>();
        if (v >= numVertices) {
            return neighbors;
        }
        for (int edge = 0; edge < numEdges; edge++) {
            if (incidenceMatrix[v][edge]) {
                Integer[] direction = edgeDirections.get(edge);
                // Если текущая вершина v является началом ребра, добавляем конечную вершину
                if (direction != null && direction[0] == v) {
                    neighbors.add(direction[1]); // Добавляем конечную вершину
                }
            }
        }
        return neighbors;
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
            numVertices = Integer.parseInt(firstLine[0]);
            numEdges = Integer.parseInt(firstLine[1]);

            // Инициализация матрицы инцидентности и счётчика рёбер
            incidenceMatrix = new boolean[numVertices][numEdges];
            edgeDirections.clear();
            edgeCount = 0;

            // Чтение рёбер
            for (int i = 0; i < numVertices; i++) {
                String[] line = br.readLine().split(" ");
                for (int j = 0; j < numEdges; j++) {
                    if (line[j].equals("1")) {
                        incidenceMatrix[i][j] = true;
                        if (!edgeDirections.containsKey(j)) {
                            edgeDirections.put(j,
                                               new Integer[]{i, -1}
                            ); // Помечаем начало направления
                        } else {
                            edgeDirections.get(j)[1] = i; // Заполняем конец направления
                        }
                    }
                }
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
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IncidenceMatrixGraph that = (IncidenceMatrixGraph) obj;
        return numVertices == that.numVertices
                && numEdges == that.numEdges
                && Arrays.deepEquals(incidenceMatrix, that.incidenceMatrix);
    }

    /**
     * Возвращает строковое представление графа.
     *
     * @return строковое представление графа
     */
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

    /**
     * Выполняет топологическую сортировку графа.
     *
     * @return список вершин в порядке топологической сортировки
     * @throws GraphCycleException если в графе обнаружен цикл, топологическая сортировка
     *                             невозможна
     */
    @Override
    public List<Integer> topologicalSort() throws GraphCycleException {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[numVertices];
        boolean[] recStack = new boolean[numVertices]; // Для отслеживания циклов

        for (int i = 0; i < numVertices; i++) {
            if (!visited[i]) {
                if (!topologicalSortUtil(i, visited, recStack, stack)) {
                    throw new GraphCycleException("The graph contains a cycle, topological "
                                                          + "sorting is not possible");
                }
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
     * @param v        Текущая вершина, которую нужно посетить.
     * @param visited  Массив для отслеживания посещенных вершин.
     * @param recStack Массив для отслеживания текущего пути рекурсии для выявления циклов.
     * @param stack    Стек, в который добавляются вершины после посещения всех их соседей, чтобы
     *                 получить порядок их обработки в топологической сортировке.
     */
    private boolean topologicalSortUtil(int v, boolean[] visited, boolean[] recStack, Stack<Integer> stack) {
        visited[v] = true;
        recStack[v] = true;

        List<Integer> neighbors = getNeighbors(v); // Получаем соседей текущей вершины

        for (int u : neighbors) {
            // Если соседняя вершина еще не посещена, продолжаем рекурсивный обход
            if (!visited[u]) {
                if (!topologicalSortUtil(u, visited, recStack, stack)) {
                    return false; // Цикл обнаружен
                }
            } else if (recStack[u]) {
                return false; // Цикл обнаружен
            }
        }

        recStack[v] = false; // Выход из текущей вершины
        stack.push(v); // Добавляем вершину в стек сортировки
        return true;
    }
}
