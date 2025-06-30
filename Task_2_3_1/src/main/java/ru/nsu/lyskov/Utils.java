package ru.nsu.lyskov;

/**
 * Утилитарный класс, предоставляющий вспомогательные методы.
 */
public class Utils {
    /**
     * Возвращает случайное значение из enum заданного класса.
     *
     * @param <T>       тип перечисления
     * @param enumClass класс перечисления, из которого нужно выбрать случайное значение
     * @return случайное значение из указанного перечисления
     * @throws IllegalArgumentException если переданный класс не является перечислением или если
     *                                  перечисление не содержит значений
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        return values[new java.util.Random().nextInt(values.length)];
    }
}