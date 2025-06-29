package ru.nsu.lyskov;

public class Utils {
    public static <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        return values[new java.util.Random().nextInt(values.length)];
    }
}
