package ru.nsu.lyskov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Класс для представления графа с использованием матрицы смежности.
 * <p>
 * Реализует интерфейс {@link Graph} и поддерживает основные операции над графами, такие как
 * добавление и удаление вершин и рёбер, получение соседей вершин, чтение графа из файла и
 * выполнение топологической сортировки.
 * </p>
 */
public class AdjacencyMatrixGraph implements Graph {
    private boolean[][] matrix;  // Матрица смежности
    private int numVertices;     // Количество вершин в графе

    /**
     * Конструктор, создающий новый граф с заданным количеством вершин.
     *
     * @param numVertices количество вершин в графе
     */
    public AdjacencyMatrixGraph(int numVertices) {
        this.numVertices = numVertices;
        matrix = new boolean[numVertices][numVertices];
    }

    /**
     * Добавляет новую вершину в граф.
     *
     * @param v номер вершины, которую нужно добавить
     */
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

    /**
     * Удаляет вершину из графа.
     *
     * @param v номер вершины, которую нужно удалить
     */
    @Override
    public void removeVertex(int v) {
        // Удаление вершины путем обнуления строки и столбца
        for (int i = 0; i < numVertices; i++) {
            matrix[v][i] = false;
            matrix[i][v] = false;
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
        matrix[v1][v2] = true;  // Добавляем ребро между v1 и v2
    }

    /**
     * Удаляет направленное ребро между двумя вершинами.
     *
     * @param v1 номер первой вершины
     * @param v2 номер второй вершины
     */
    @Override
    public void removeEdge(int v1, int v2) {
        matrix[v1][v2] = false; // Удаляем ребро между v1 и v2
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
        for (int i = 0; i < numVertices; i++) {
            if (matrix[v][i]) {
                neighbors.add(i); // Добавляем соседнюю вершину
            }
        }
        return neighbors; // Возвращаем список соседей
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
            // Количество рёбер можно использовать для валидации
            int numEdges = Integer.parseInt(firstLine[1]);

            // Инициализируем матрицу смежности
            matrix = new boolean[numVertices][numVertices];

            // Чтение последующих строк — это сама матрица смежности
            for (int i = 0; i < numVertices; i++) {
                String[] line = br.readLine().split(" ");
                for (int j = 0; j < numVertices; j++) {
                    matrix[i][j] = line[j].equals("1"); // Заполняем матрицу
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
        AdjacencyMatrixGraph that = (AdjacencyMatrixGraph) obj;
        return Arrays.deepEquals(matrix, that.matrix); // Сравнение матриц
    }

    /**
     * Возвращает строковое представление графа.
     *
     * @return строковое представление графа
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Adjacency Matrix:\n");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                sb.append(matrix[i][j] ? "1 " : "0 "); // Формируем строку для отображения матрицы
            }
            sb.append("\n");
        }
        return sb.toString(); // Возвращаем строковое представление матрицы
    }

    /**
     * Выполняет топологическую сортировку графа.
     *
     * @return список вершин в порядке топологической сортировки
     */
    @Override
    public List<Integer> topologicalSort() throws GraphCycleException {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[numVertices];
        boolean[] recStack = new boolean[numVertices];

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

        for (int i = 0; i < numVertices; i++) {
            if (matrix[v][i]) {
                if (!visited[i]) {
                    if (!topologicalSortUtil(i, visited, recStack, stack)) {
                        return false;
                    }
                } else if (recStack[i]) {
                    return false;
                }
            }
        }

        recStack[v] = false;
        stack.push(v);
        return true;
    }
}
