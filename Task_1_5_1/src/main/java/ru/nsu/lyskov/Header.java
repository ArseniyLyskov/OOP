package ru.nsu.lyskov;

/**
 * Класс, представляющий заголовок в Markdown. Заголовок равен количеству "#" перед текстом
 * заголовка, может быть от 1 до 6 уровня.
 */
public class Header extends Element {
    private final String content; // Содержимое заголовка
    private final int level; // Уровень заголовка (от 1 до 6)

    /**
     * Конструктор для создания заголовка с заданным содержимым и уровнем.
     *
     * @param content текст заголовка
     * @param level   уровень заголовка (от 1 до 6)
     * @throws IllegalArgumentException если уровень заголовка не в пределах от 1 до 6
     */
    public Header(String content, int level) {
        if (level < 1 || level > 6) {
            throw new IllegalArgumentException("Header level must be between 1 and 6.");
        }
        this.content = content;
        this.level = level;
    }

    /**
     * Преобразует заголовок в строку в формате Markdown.
     *
     * @return строка, представляющая заголовок в формате Markdown
     */
    @Override
    public String toString() {
        return "#".repeat(level) + " " + content;
    }
}
