package ru.nsu.lyskov;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Реализация параметризованной хеш-таблицы, которая хранит пары ключ-значение и поддерживает
 * базовые операции с элементами.
 *
 * @param <K> тип ключей.
 * @param <V> тип значений.
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {
    /**
     * Начальный размер таблицы по умолчанию.
     */
    private static final int DEFAULT_CAPACITY = 16;
    /**
     * Коэффициент загрузки, определяющий порог расширения таблицы.
     */
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * Массив корзин, где каждая корзина представляет собой список пар ключ-значение.
     */
    private List<Entry<K, V>>[] table;
    /**
     * Текущее количество элементов в таблице.
     */
    private int size;
    /**
     * Счетчик изменений для отслеживания модификаций таблицы.
     */
    private int modCount = 0;

    /**
     * Конструктор, создающий пустую хеш-таблицу с начальной емкостью по умолчанию.
     */
    public HashTable() {
        this.table = new LinkedList[DEFAULT_CAPACITY];
    }

    /**
     * Вложенный статический класс, представляющий пару ключ-значение.
     *
     * @param <K> тип ключа.
     * @param <V> тип значения.
     */
    public static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Добавляет новую пару ключ-значение или обновляет значение для существующего ключа.
     *
     * @param key   ключ для добавления.
     * @param value значение, связанное с ключом.
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        ensureCapacity();
        int index = getIndex(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
        modCount++;
    }

    /**
     * Удаляет пару ключ-значение из таблицы.
     *
     * @param key ключ пары, которую нужно удалить.
     */
    public void remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = getIndex(key);
        if (table[index] != null) {
            Iterator<Entry<K, V>> iterator = table[index].iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (entry.key.equals(key)) {
                    iterator.remove();
                    size--;
                    modCount++;
                    return;
                }
            }
        }
    }

    /**
     * Возвращает значение, связанное с указанным ключом.
     *
     * @param key ключ для поиска.
     * @return значение, связанное с ключом, или {@code null}, если ключ отсутствует.
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = getIndex(key);
        if (table[index] != null) {
            for (Entry<K, V> entry : table[index]) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
            }
        }
        return null;
    }

    /**
     * Обновляет значение для указанного ключа. Если ключ отсутствует, то добавляет новую пару.
     *
     * @param key   ключ.
     * @param value новое значение.
     */
    public void update(K key, V value) {
        put(key, value);
    }

    /**
     * Проверяет наличие ключа в таблице.
     *
     * @param key ключ для проверки.
     * @return {@code true}, если ключ присутствует в таблице, иначе {@code false}.
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = getIndex(key);
        if (table[index] != null) {
            for (Entry<K, V> entry : table[index]) {
                if (entry.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Возвращает итератор для обхода всех пар ключ-значение в таблице.
     *
     * @return итератор.
     * @throws ConcurrentModificationException если таблица была изменена во время итерации.
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private final int expectedModCount = modCount;
            private int bucketIndex = 0;
            private Iterator<Entry<K, V>> bucketIterator = null;

            private void advanceToNextBucket() {
                while ((bucketIterator == null || !bucketIterator.hasNext()) && bucketIndex < table.length) {
                    if (table[bucketIndex] != null) {
                        bucketIterator = table[bucketIndex].iterator();
                    }
                    bucketIndex++;
                }
            }

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                advanceToNextBucket();
                return bucketIterator != null && bucketIterator.hasNext();
            }

            @Override
            public Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return bucketIterator.next();
            }
        };
    }

    /**
     * Сравнивает текущую таблицу с другой таблицей на равенство.
     *
     * @param obj объект для сравнения.
     * @return {@code true}, если таблицы равны; {@code false} в противном случае.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HashTable)) {
            return false;
        }
        HashTable<K, V> other = (HashTable<K, V>) obj;

        if (this.size != other.size) {
            return false;
        }

        for (Entry<K, V> entry : this) {
            if (!other.containsKey(entry.key)
                    || !Objects.equals(other.get(entry.key), entry.value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Возвращает строковое представление таблицы в формате: {key1=value1, key2=value2}.
     *
     * @return строковое представление таблицы.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Entry<K, V> entry : this) {
            sb.append(entry.key).append("=").append(entry.value).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    private int getIndex(K key) {
        return (key.hashCode() & 0x7FFFFFFF) % table.length;
    }

    private void ensureCapacity() {
        if (size > table.length * LOAD_FACTOR) {
            resize();
        }
    }

    private void resize() {
        List<Entry<K, V>>[] oldTable = table;
        table = new LinkedList[oldTable.length * 2];
        size = 0;
        modCount++;
        for (List<Entry<K, V>> bucket : oldTable) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    int index = getIndex(entry.key);
                    if (table[index] == null) {
                        table[index] = new LinkedList<>();
                    }
                    table[index].add(entry);
                    size++;
                }
            }
        }
    }
}
