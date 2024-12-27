package ru.nsu.lyskov;

/**
 * Класс, представляющий ссылку в формате Markdown. Ссылка включает текст и URL, на который она
 * ведет.
 */
public class Link extends Element {
    private final String text; // Текст ссылки
    private final String url; // URL, на который ведет ссылка

    /**
     * Конструктор для создания ссылки.
     *
     * @param text текст ссылки
     * @param url  URL, на который ведет ссылка
     */
    public Link(String text, String url) {
        this.text = text;
        this.url = url;
    }

    /**
     * Формат ссылки в Markdown: [текст](URL).
     *
     * @return строка, представляющая ссылку в формате Markdown
     */
    @Override
    public String toString() {
        return "[" + text + "](" + url + ")";
    }
}
