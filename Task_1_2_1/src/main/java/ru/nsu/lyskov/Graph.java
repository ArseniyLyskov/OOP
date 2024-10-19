package ru.nsu.lyskov;

import java.util.List;

/**
 * Интерфейс для представления графа.
 * <p>
 * Поддерживает операции над графами, такие как добавление и удаление вершин и рёбер, получение
 * соседей вершин, чтение графа из файла и выполнение топологической сортировки.
 * </p>
 */
public interface Graph {
    /**
     * Добавляет новую вершину в граф.
     *
     * @param v номер вершины, которую нужно добавить
     */
    void addVertex(int v);

    /**
     * Удаляет вершину из графа.
     *
     * @param v номер вершины, которую нужно удалить
     */
    void removeVertex(int v);

    /**
     * Добавляет направленное ребро между двумя вершинами.
     *
     * @param v1 номер первой вершины
     * @param v2 номер второй вершины
     */
    void addEdge(int v1, int v2);

    /**
     * Удаляет направленное ребро между двумя вершинами.
     *
     * @param v1 номер первой вершины
     * @param v2 номер второй вершины
     */
    void removeEdge(int v1, int v2);

    /**
     * Возвращает список соседей заданной вершины.
     *
     * @param v номер вершины
     * @return список соседей указанной вершины
     */
    List<Integer> getNeighbors(int v);

    /**
     * Считывает граф из файла.
     *
     * @param fileName имя файла, из которого будет прочитан граф
     */
    void readFromFile(String fileName);

    /**
     * Сравнивает два объекта на равенство.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны, иначе false
     */
    boolean equals(Object obj);

    /**
     * Возвращает строковое представление графа.
     *
     * @return строковое представление графа
     */
    String toString();

    /**
     * Выполняет топологическую сортировку графа.
     *
     * @return список вершин в порядке топологической сортировки
     */
    List<Integer> topologicalSort();
}
