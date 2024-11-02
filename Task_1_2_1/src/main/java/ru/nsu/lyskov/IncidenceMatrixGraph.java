package ru.nsu.lyskov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private int[][] incidenceMatrix;     // Матрица инцидентности
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
        this.edgeCount = 0;
    }

    /**
     * Добавляет новую вершину в граф.
     *
     * @param v номер вершины, которую нужно добавить
     */
    @Override
    public void addVertex(int v) {
        if (v >= numVertices) {
            int[][] newMatrix = new int[v + 1][numEdges];
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
        for (int i = 0; i < numEdges; i++) {
            incidenceMatrix[v][i] = 0;
        }

        // Сдвигаем строки для удаления вершины
        for (int i = v; i < numVertices - 1; i++) {
            incidenceMatrix[i] = incidenceMatrix[i + 1];
        }

        // Уменьшаем размерность массива
        numVertices--;
        incidenceMatrix = Arrays.copyOf(incidenceMatrix, numVertices);
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
        incidenceMatrix[v1][edgeCount] = 1;  // Начало направления
        incidenceMatrix[v2][edgeCount] = -1;   // Конец направления
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
            if (incidenceMatrix[v1][i] == 1 && incidenceMatrix[v2][i] == -1) {
                for (int j = 0; j < numVertices; j++) {
                    incidenceMatrix[j][i] = 0; // Удаляем ребро
                }
                break;
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
            // Проверка на исходящее ребро
            if (incidenceMatrix[v][edge] == 1) {
                for (int i = 0; i < numVertices; i++) {
                    if (incidenceMatrix[i][edge] == -1 && !neighbors.contains(i)) {
                        neighbors.add(i); // Добавляем вершину-конец направления
                    }
                }
            }
            // Проверка на входящее ребро
            else if (incidenceMatrix[v][edge] == -1) {
                for (int i = 0; i < numVertices; i++) {
                    if (incidenceMatrix[i][edge] == 1 && !neighbors.contains(i)) {
                        neighbors.add(i); // Добавляем вершину-начало направления
                    }
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
            incidenceMatrix = new int[numVertices][numEdges];
            edgeCount = 0;

            // Чтение каждой строки, представляющей одну строку матрицы инцидентности
            for (int i = 0; i < numVertices; i++) {
                String[] row = br.readLine().split(" ");
                for (int j = 0; j < numEdges; j++) {
                    incidenceMatrix[i][j] = Integer.parseInt(row[j]);
                }
            }
        } catch (IOException e) {
            System.out.println("File read error: " + e.getMessage());
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
                sb.append(incidenceMatrix[i][j]).append(" ");
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
    private boolean topologicalSortUtil(int v, boolean[] visited, boolean[] recStack,
                                        Stack<Integer> stack) {
        visited[v] = true;
        recStack[v] = true;

        List<Integer> neighbors = getOutgoingNeighbors(v); // Используем только исходящие вершины

        for (int u : neighbors) {
            if (!visited[u]) {
                if (!topologicalSortUtil(u, visited, recStack, stack)) {
                    return false; // Цикл обнаружен
                }
            } else if (recStack[u]) {
                return false; // Цикл обнаружен
            }
        }

        recStack[v] = false;
        stack.push(v);
        return true;
    }

    /**
     * Возвращает список соседей, к которым можно перейти из указанной вершины.
     *
     * @param v номер вершины
     * @return список соседей указанной вершины, к которым можно перейти из указанной вершины, или
     * null, если вершина отсутствует
     */
    public List<Integer> getOutgoingNeighbors(int v) {
        List<Integer> outgoingNeighbors = new ArrayList<>();
        if (v >= numVertices) {
            return outgoingNeighbors;
        }
        for (int edge = 0; edge < numEdges; edge++) {
            if (incidenceMatrix[v][edge] == 1) { // Если есть исходящее ребро из v
                for (int i = 0; i < numVertices; i++) {
                    if (incidenceMatrix[i][edge] == -1) { // Найти конечную вершину для
                        // исходящего ребра
                        outgoingNeighbors.add(i);
                    }
                }
            }
        }
        return outgoingNeighbors;
    }

}
