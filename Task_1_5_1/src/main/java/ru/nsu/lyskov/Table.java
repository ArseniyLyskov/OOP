package ru.nsu.lyskov;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий таблицу в формате Markdown. Таблица состоит из строк и может иметь
 * выравнивание по левому, правому краю или центру.
 */
public class Table extends Element {
    public static final String ALIGN_LEFT = ":-";
    public static final String ALIGN_RIGHT = "-:";
    public static final String ALIGN_CENTER = ":-:";

    private final List<String[]> rows; // Строки таблицы
    private final String[] alignments; // Выравнивание для каждого столбца

    /**
     * Конструктор для создания таблицы.
     *
     * @param rows       строки таблицы
     * @param alignments выравнивание для столбцов
     * @param rowLimit   максимальное количество строк в таблице
     */
    private Table(List<String[]> rows, String[] alignments, int rowLimit) {
        this.rows = rows;
        this.alignments = alignments;
    }

    /**
     * Таблица будет отформатирована с учётом выравнивания столбцов и максимальной ширины ячеек.
     *
     * @return строку
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // Вычисление ширины столбцов
        int columnCount = rows.get(0).length;
        int[] columnWidths = new int[columnCount];
        for (String[] row : rows) {
            for (int i = 0; i < columnCount; i++) {
                if (i < row.length) {
                    columnWidths[i] = Math.max(columnWidths[i], row[i].length());
                }
            }
        }

        // Заголовок таблицы
        builder.append("|");
        for (int i = 0; i < columnCount; i++) {
            builder.append(" ").append(
                    padCell(rows.get(0)[i], columnWidths[i], ALIGN_LEFT)).append(" |");
        }
        builder.append("\n");

        // Выравнивание столбцов
        builder.append("|");
        for (int i = 0; i < columnCount; i++) {
            String alignment = i < alignments.length ? alignments[i] : "---";
            builder.append(" ").append(formatAlignment(alignment, columnWidths[i])).append(" |");
        }
        builder.append("\n");

        // Строки таблицы
        for (int i = 1; i < rows.size(); i++) {
            builder.append("|");
            for (int j = 0; j < columnCount; j++) {
                String alignment = j < alignments.length ? alignments[j] : ALIGN_LEFT;
                String cell = j < rows.get(i).length ? rows.get(i)[j] : "";
                builder.append(" ").append(padCell(cell, columnWidths[j], alignment)).append(" |");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    /**
     * Вложенный класс для построения таблицы. Позволяет добавлять строки, устанавливать
     * выравнивание и максимальное количество строк.
     */
    public static class Builder {
        private final List<String[]> rows = new ArrayList<>();
        private String[] alignments;
        private int rowLimit = Integer.MAX_VALUE;

        /**
         * Устанавливает выравнивание для столбцов таблицы.
         *
         * @param alignments выравнивание столбцов
         * @return текущий экземпляр Builder для цепочки вызовов
         */
        public Builder withAlignments(String... alignments) {
            this.alignments = alignments;
            return this;
        }

        /**
         * Устанавливает максимальное количество строк в таблице.
         *
         * @param limit максимальное количество строк
         * @return текущий экземпляр Builder для цепочки вызовов
         * @throws IllegalArgumentException если лимит строк меньше или равен 0
         */
        public Builder withRowLimit(int limit) {
            if (limit <= 0) {
                throw new IllegalArgumentException("Row limit must be greater than 0.");
            }
            this.rowLimit = limit;
            return this;
        }

        /**
         * Добавляет строку в таблицу.
         *
         * @param cells ячейки строки
         * @return текущий экземпляр Builder для цепочки вызовов
         * @throws IllegalStateException если количество строк превышает лимит
         */
        public Builder addRow(Object... cells) {
            if (rows.size() >= rowLimit) {
                throw new IllegalStateException("Cannot add more rows: row limit reached.");
            }
            String[] row = new String[cells.length];
            for (int i = 0; i < cells.length; i++) {
                row[i] = cells[i].toString();
            }
            rows.add(row);
            return this;
        }

        /**
         * Строит таблицу на основе добавленных строк и настроек.
         *
         * @return объект Table
         * @throws IllegalStateException если таблица не содержит ни одной строки
         */
        public Table build() {
            if (rows.isEmpty()) {
                throw new IllegalStateException("Table must have at least one row.");
            }
            return new Table(rows, alignments, rowLimit);
        }
    }

    /**
     * Форматирует строку выравнивания для столбца.
     *
     * @param alignment выравнивание (левое, правое, центральное)
     * @param width     ширина столбца
     * @return строка выравнивания для столбца в Markdown
     */
    private String formatAlignment(String alignment, int width) {
        switch (alignment) {
            case ALIGN_LEFT:
                return ":" + "-".repeat(width - 1);
            case ALIGN_RIGHT:
                return "-".repeat(width - 1) + ":";
            case ALIGN_CENTER:
                return ":" + "-".repeat(width - 2) + ":";
            default:
                return "-".repeat(width);
        }
    }

    /**
     * Дополняет ячейку до нужной ширины в соответствии с выравниванием.
     *
     * @param content   содержимое ячейки
     * @param width     ширина ячейки
     * @param alignment выравнивание ячейки
     * @return строка, представляющая ячейку с необходимым выравниванием и размером
     */
    private String padCell(String content, int width, String alignment) {
        if (content == null) {
            content = "";
        }
        switch (alignment) {
            case ALIGN_RIGHT:
                return " ".repeat(width - content.length()) + content;
            case ALIGN_CENTER:
                int padding = (width - content.length()) / 2;
                return " ".repeat(padding) + content + " ".repeat(
                        width - content.length() - padding);
            case ALIGN_LEFT:
            default:
                return content + " ".repeat(width - content.length());
        }
    }
}
