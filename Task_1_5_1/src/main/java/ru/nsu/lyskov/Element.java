package ru.nsu.lyskov;

/**
 * Абстрактный класс для элементов Markdown. Все элементы Markdown должны наследоваться от этого
 * класса.
 */
public abstract class Element {

    /**
     * Преобразует элемент в строку.
     *
     * @return строка в формате Markdown, представляющая этот элемент
     */
    @Override
    public abstract String toString();

    /**
     * Сравнивает два элемента на равенство.
     *
     * @param obj объект для сравнения
     * @return true, если элементы равны, иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Element element = (Element) obj;
        return toString().equals(element.toString());
    }

    /**
     * Возвращает хеш-код элемента на основе его строкового представления в Markdown.
     *
     * @return хеш-код элемента
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
