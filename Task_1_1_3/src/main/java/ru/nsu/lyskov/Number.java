package ru.nsu.lyskov;

import java.io.PrintStream;
import java.util.Map;
import java.util.Objects;

/**
 * Класс для представления численного значения в математическом выражении.
 */
public class Number extends Expression {
    private final double value;

    /**
     * Конструктор, который создаёт числовое выражение с указанным значением.
     *
     * @param value числовое значение
     */
    public Number(double value) {
        this.value = value;
    }

    /**
     * Печатает числовое значение. Если значение целое, оно выводится без дробной части.
     *
     * @param out поток вывода, в который будет напечатано выражение
     */
    @Override
    public void print(PrintStream out) {
        if (value % 1 == 0) {
            out.print((long) value);
        } else {
            out.print(value);
        }
    }

    /**
     * Возвращает производную числового выражения, которая всегда равна 0.
     *
     * @param variable переменная, по которой необходимо дифференцировать
     * @return новое выражение, представляющее производную (всегда 0)
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * Упрощает текущее выражение, создавая новое (упрощённое) выражение. Для {@link Number} -
     * упрощение не нужно.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * Возвращает числовое значение выражения.
     *
     * @param variables карта, где ключи — имена переменных, а значения — их числовые значения
     *                  (игнорируется для чисел)
     * @return числовое значение
     */
    @Override
    public double eval(Map<String, Double> variables) {
        return value;
    }

    /**
     * Геттер для значения.
     *
     * @return значение
     */
    public double getValue() {
        return value;
    }

    /**
     * Сравнивает два объекта на равенство.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны, иначе false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Number number = (Number) obj;
        return Objects.equals(value, number.value);
    }
}
