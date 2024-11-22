package ru.nsu.lyskov;

import java.io.PrintStream;
import java.util.Map;
import java.util.Objects;
import ru.nsu.lyskov.exceptions.DivisionByZeroException;
import ru.nsu.lyskov.exceptions.IncorrectAssignmentException;

/**
 * Класс для представления операции сложения двух выражений.
 */
public class Add extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор, принимающий два выражения, которые нужно сложить.
     *
     * @param left  левое выражение
     * @param right правое выражение
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Печатает сложное выражение в виде (left + right).
     *
     * @param out поток вывода, в который будет напечатано выражение
     */
    @Override
    public void print(PrintStream out) {
        out.print("(");
        left.print(out);
        out.print("+");
        right.print(out);
        out.print(")");
    }

    /**
     * Возвращает производную выражения сложения по указанной переменной.
     *
     * @param variable переменная, по которой необходимо дифференцировать
     * @return новое выражение, представляющее производную (left' + right')
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Упрощает текущее выражение, создавая новое (упрощённое) выражение. Для {@link Add} - сумма
     * упрощённых подвыражений.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() throws DivisionByZeroException {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number(
                    ((Number) simplifiedLeft).getValue() + ((Number) simplifiedRight).getValue());
        }
        return new Add(simplifiedLeft, simplifiedRight);
    }

    /**
     * Вычисляет значение выражения сложения.
     *
     * @param variables карта, где ключи — имена переменных, а значения — их числовые значения
     * @return результат сложения
     * @throws DivisionByZeroException      если в одном из выражений произошло деление на ноль.
     * @throws IncorrectAssignmentException если совершено некорректное присваивание переменных
     */
    @Override
    public double eval(Map<String, Double> variables)
            throws DivisionByZeroException, IncorrectAssignmentException {
        return left.eval(variables) + right.eval(variables);
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
        Add add = (Add) obj;
        return Objects.equals(left, add.left) && Objects.equals(right, add.right);
    }
}
