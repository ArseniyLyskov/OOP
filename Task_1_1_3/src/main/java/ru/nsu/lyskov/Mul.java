package ru.nsu.lyskov;

import java.io.PrintStream;
import java.util.Map;
import java.util.Objects;
import ru.nsu.lyskov.exceptions.DivisionByZeroException;
import ru.nsu.lyskov.exceptions.IncorrectAssignmentException;

/**
 * Класс для представления операции умножения двух выражений.
 */
public class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор, принимающий два выражения, которые нужно перемножить.
     *
     * @param left  левое выражение
     * @param right правое выражение
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Печатает умножение выражения в виде (left * right).
     *
     * @param out поток вывода, в который будет напечатано выражение
     */
    @Override
    public void print(PrintStream out) {
        out.print("(");
        left.print(out);
        out.print("*");
        right.print(out);
        out.print(")");
    }

    /**
     * Возвращает производную выражения умножения по указанной переменной.
     *
     * @param variable переменная, по которой необходимо дифференцировать
     * @return новое выражение, представляющее производную
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(new Mul(left.derivative(variable), right),
                       new Mul(left, right.derivative(variable))
        );
    }

    /**
     * Упрощает текущее выражение, создавая новое (упрощённое) выражение. Для {@link Mul} -
     * произведение упрощённых подвыражений; 0, если умножение на 0; второй множитель, если
     * умножение на 1.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() throws DivisionByZeroException {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Number && ((Number) simplifiedLeft).getValue() == 0) {
            return new Number(0);
        }
        if (simplifiedRight instanceof Number && ((Number) simplifiedRight).getValue() == 0) {
            return new Number(0);
        }
        if (simplifiedLeft instanceof Number && ((Number) simplifiedLeft).getValue() == 1) {
            return simplifiedRight;
        }
        if (simplifiedRight instanceof Number && ((Number) simplifiedRight).getValue() == 1) {
            return simplifiedLeft;
        }

        // Если оба подвыражения — числа, вычисляем их произведение
        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            return new Number(
                    ((Number) simplifiedLeft).getValue() * ((Number) simplifiedRight).getValue());
        }

        return new Mul(simplifiedLeft, simplifiedRight);
    }

    /**
     * Вычисляет значение выражения умножения.
     *
     * @param variables карта, где ключи — имена переменных, а значения — их числовые значения
     * @return результат умножения
     * @throws DivisionByZeroException      если в одном из выражений произошло деление на ноль.
     * @throws IncorrectAssignmentException если совершено некорректное присваивание переменных
     */
    @Override
    public double eval(Map<String, Double> variables)
            throws DivisionByZeroException, IncorrectAssignmentException {
        return left.eval(variables) * right.eval(variables);
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
        Mul mul = (Mul) obj;
        return Objects.equals(left, mul.left) && Objects.equals(right, mul.right);
    }
}
