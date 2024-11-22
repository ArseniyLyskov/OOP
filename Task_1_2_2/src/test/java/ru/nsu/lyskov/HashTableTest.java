package ru.nsu.lyskov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки корректности работы хеш-таблицы (HashTable).
 */
public class HashTableTest {

    /**
     * Тестирует добавление и получение элементов.
     */
    @Test
    public void testPutAndGet() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("key1", 1);
        table.put("key2", 2);

        assertEquals(1, table.get("key1"));
        assertEquals(2, table.get("key2"));
    }

    /**
     * Тестирует обновление значения для существующего ключа.
     */
    @Test
    public void testUpdate() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("key1", 1);
        table.update("key1", 100);

        assertEquals(100, table.get("key1"));
    }

    /**
     * Тестирует удаление элемента.
     */
    @Test
    public void testRemove() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("key1", 1);
        table.put("key2", 2);
        table.remove("key1");

        assertNull(table.get("key1"));
        assertEquals(2, table.get("key2"));
    }

    /**
     * Тестирует проверку наличия ключа в таблице.
     */
    @Test
    public void testContainsKey() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("key1", 1);

        assertTrue(table.containsKey("key1"));
        assertFalse(table.containsKey("key2"));
    }

    /**
     * Тестирует метод toString, чтобы убедиться в правильности форматирования строки.
     */
    @Test
    public void testToString() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("key1", 1);
        table.put("key2", 2);

        String result = table.toString();
        assertTrue(result.contains("key1=1"));
        assertTrue(result.contains("key2=2"));
    }

    /**
     * Тестирует итератор по хеш-таблице.
     */
    @Test
    public void testIterator() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("key1", 1);
        table.put("key2", 2);

        int sum = 0;
        int count = 0;
        for (HashTable.Entry<String, Integer> entry : table) {
            sum += entry.value;
            count++;
        }
        assertEquals(3, sum);
        assertEquals(2, count);
    }

    /**
     * Тестирует равенство двух таблиц с одинаковыми элементами, но расположенными в разном порядке
     * внутри корзин.
     */
    @Test
    public void testEqualsWithCollisions() {
        HashTable<KeyWithFixedHash, Integer> table1 = new HashTable<>();
        HashTable<KeyWithFixedHash, Integer> table2 = new HashTable<>();

        KeyWithFixedHash key1 = new KeyWithFixedHash("key1", 1);
        KeyWithFixedHash key2 = new KeyWithFixedHash("key2", 1);

        table1.put(key1, 100);
        table1.put(key2, 200);

        table2.put(key2, 200);
        table2.put(key1, 100);

        assertEquals(table1, table2);
    }

    /**
     * Тестирует неравенство двух таблиц, если хотя бы одно значение отличается.
     */
    @Test
    public void testNotEqualsWithCollisions() {
        HashTable<KeyWithFixedHash, Integer> table1 = new HashTable<>();
        HashTable<KeyWithFixedHash, Integer> table2 = new HashTable<>();

        KeyWithFixedHash key1 = new KeyWithFixedHash("key1", 1);
        KeyWithFixedHash key2 = new KeyWithFixedHash("key2", 1);

        table1.put(key1, 100);
        table1.put(key2, 200);

        table2.put(key1, 100);
        table2.put(key2, 300);

        assertNotEquals(table1, table2);
    }

    /**
     * Тестирует масштабирование таблицы (resize), добавляя большое количество элементов.
     */
    @Test
    public void testResize() {
        HashTable<Integer, String> table = new HashTable<>();

        int elementsToAdd = 1000;

        for (int i = 0; i < elementsToAdd; i++) {
            table.put(i, "Value" + i);
        }

        for (int i = 0; i < elementsToAdd; i++) {
            assertEquals("Value" + i, table.get(i));
        }

        assertFalse(table.toString().isEmpty());
        assertEquals(elementsToAdd, table.toString().split(",").length);
    }

    /**
     * Тестирует ошибку при изменении таблицы в процессе итерации.
     */
    @Test
    public void testConcurrentModificationException() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("key1", 1);
        table.put("key2", 2);

        assertThrows(ConcurrentModificationException.class, () -> {
            for (HashTable.Entry<String, Integer> entry : table) {
                table.put("key3", 3);
            }
        });
    }
}
