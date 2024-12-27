package ru.nsu.lyskov;

/**
 * Класс, представляющий простой текст в формате Markdown. Текст можно форматировать различными
 * способами, такими как жирный, курсив, зачёркнутый или код.
 */
public class Text extends Element {
    private final String content; // Содержимое текста

    /**
     * Конструктор для создания текста с заданным содержимым.
     *
     * @param content текст
     */
    public Text(String content) {
        this.content = content;
    }

    /**
     * Преобразует текст в строку.
     *
     * @return строку
     */
    @Override
    public String toString() {
        return content;
    }

    /**
     * Класс для жирного текста. Текст будет обрамлён двойными звёздочками "**".
     */
    public static class Bold extends Text {
        public Bold(String content) {
            super(content);
        }

        /**
         * Преобразует жирный текст в строку Markdown.
         *
         * @return строку
         */
        @Override
        public String toString() {
            return "**" + super.toString() + "**";
        }
    }

    /**
     * Класс для курсива. Текст будет обрамлён одной звёздочкой "*".
     */
    public static class Italic extends Text {
        public Italic(String content) {
            super(content);
        }

        /**
         * Преобразует текст в курсив в строку Markdown.
         *
         * @return строку
         */
        @Override
        public String toString() {
            return "*" + super.toString() + "*";
        }
    }

    /**
     * Класс для зачеркнутого текста. Текст будет обрамлён двумя тильдами "~~".
     */
    public static class StrikeThrough extends Text {
        public StrikeThrough(String content) {
            super(content);
        }

        /**
         * Преобразует зачеркнутый текст в строку Markdown.
         *
         * @return строку
         */
        @Override
        public String toString() {
            return "~~" + super.toString() + "~~";
        }
    }

    /**
     * Класс для текста, представляющего код. Текст будет обрамлён обратными кавычками "`".
     */
    public static class Code extends Text {
        public Code(String content) {
            super(content);
        }

        /**
         * Преобразует текст в код в строку Markdown.
         *
         * @return строку
         */
        @Override
        public String toString() {
            return "`" + super.toString() + "`";
        }
    }
}
