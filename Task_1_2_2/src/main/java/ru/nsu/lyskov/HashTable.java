package ru.nsu.lyskov;

import java.util.*;

public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private List<Entry<K, V>>[] table;
    private int size;
    private int modCount = 0;

    public HashTable() {
        this.table = new LinkedList[DEFAULT_CAPACITY];
    }

    public static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) {
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

    public void remove(K key) {
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

    public V get(K key) {
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

    public void update(K key, V value) {
        put(key, value);
    }

    public boolean containsKey(K key) {
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

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            private int expectedModCount = modCount;
            private int bucketIndex = 0;
            private Iterator<Entry<K, V>> bucketIterator = getNextBucketIterator();

            private Iterator<Entry<K, V>> getNextBucketIterator() {
                while (bucketIndex < table.length) {
                    if (table[bucketIndex] != null) {
                        return table[bucketIndex++].iterator();
                    }
                    bucketIndex++;
                }
                return null;
            }

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return bucketIterator != null && bucketIterator.hasNext();
            }

            @Override
            public Entry<K, V> next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (bucketIterator != null && bucketIterator.hasNext()) {
                    return bucketIterator.next();
                } else {
                    bucketIterator = getNextBucketIterator();
                    return bucketIterator != null ? bucketIterator.next() : null;
                }
            }
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof HashTable)) return false;
        HashTable<K, V> other = (HashTable<K, V>) obj;
        if (this.size != other.size) return false;
        for (Entry<K, V> entry : this) {
            if (!other.containsKey(entry.key) || !Objects.equals(other.get(entry.key), entry.value)) {
                return false;
            }
        }
        return true;
    }

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
                    put(entry.key, entry.value);
                }
            }
        }
    }

    public static void main(String[] args) {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.update("one", 1.0);
        System.out.println(hashTable.get("one"));  // Должно вывести 1.0

        for (Entry<String, Number> entry : hashTable) {
            System.out.println(entry.key + " = " + entry.value);
        }
    }
}
