package ru.nsu.lyskov;

/**
 * Класс, представляющий цитату в формате Markdown. Цитата начинается с символа ">" перед текстом.
 */
public class Blockquote extends Element {
    private final String content;

    /**
     * Конструктор для создания цитаты.
     *
     * @param content текст цитаты
     */
    public Blockquote(String content) {
        this.content = content;
    }

    /**
     * Цитата в Markdown начинается с "> ".
     *
     * @return строка, представляющая цитату в формате Markdown
     */
    @Override
    public String toString() {
        return "> " + content;
    }
}
