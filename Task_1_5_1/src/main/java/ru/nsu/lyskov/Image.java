package ru.nsu.lyskov;

/**
 * Класс, представляющий изображение в формате Markdown. Изображение включает альтернативный текст
 * и URL-адрес.
 */
public class Image extends Element {
    private final String altText;
    private final String url;

    /**
     * Конструктор для создания изображения.
     *
     * @param altText альтернативный текст для изображения
     * @param url     URL изображения
     */
    public Image(String altText, String url) {
        this.altText = altText;
        this.url = url;
    }

    /**
     * Формат изображения в Markdown: ![альт. текст](URL).
     *
     * @return строка, представляющая изображение в формате Markdown
     */
    @Override
    public String toString() {
        return "![" + altText + "](" + url + ")";
    }
}
