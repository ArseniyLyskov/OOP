package ru.nsu.lyskov;

import java.util.Objects;

/**
 * Класс KeyWithFixedHash представляет ключ с фиксированным хеш-кодом. Предназначен для
 * тестирования коллизий в хеш-таблицах.
 */
class KeyWithFixedHash {
    /**
     * Строковое значение ключа.
     */
    private final String key;

    /**
     * Фиксированный хеш-код ключа.
     */
    private final int hash;

    /**
     * Создает экземпляр ключа с заданным строковым значением и хеш-кодом.
     *
     * @param key  строковое значение ключа.
     * @param hash фиксированный хеш-код, который будет возвращен методом {@link #hashCode()}.
     */
    public KeyWithFixedHash(String key, int hash) {
        this.key = key;
        this.hash = hash;
    }

    /**
     * Возвращает фиксированный хеш-код, указанный при создании объекта.
     *
     * @return фиксированный хеш-код ключа.
     */
    @Override
    public int hashCode() {
        return hash;
    }

    /**
     * Проверяет равенство текущего ключа с другим объектом. Два ключа считаются равными, если они
     * принадлежат одному классу и их строковые значения совпадают.
     *
     * @param obj объект для сравнения с текущим ключом.
     * @return {@code true}, если объекты равны; {@code false} в противном случае.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyWithFixedHash)) {
            return false;
        }
        KeyWithFixedHash other = (KeyWithFixedHash) obj;
        return Objects.equals(this.key, other.key);
    }

    /**
     * Возвращает строковое представление ключа, равное его строковому значению.
     *
     * @return строковое представление ключа.
     */
    @Override
    public String toString() {
        return key;
    }
}
