package ru.nsu.lyskov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void testTable() {
        Table.Builder tableBuilder = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("Index", "Random");
        for (int i = 1; i <= 5; i++) {
            if (i == 1 || i == 4) {
                tableBuilder.addRow(i, new Text.Bold(String.valueOf(6-i)));
            } else {
                tableBuilder.addRow(i, 6-i);
            }
        }
        assertEquals(
                """
                        | Index | Random |
                        | ----: | :----- |
                        |     1 | **5**  |
                        |     2 | 4      |
                        |     3 | 3      |
                        |     4 | **2**  |
                        |     5 | 1      |
                        """,
                tableBuilder.build().toString()
        );
    }
}
