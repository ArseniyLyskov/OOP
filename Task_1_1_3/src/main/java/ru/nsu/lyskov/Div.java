package ru.nsu.lyskov;

import java.io.PrintStream;
import java.util.Map;
import java.util.Objects;
import ru.nsu.lyskov.exceptions.DivisionByZeroException;
import ru.nsu.lyskov.exceptions.IncorrectAssignmentException;

/**
 * Класс для представления операции деления одного выражения на другое.
 */
public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор, принимающий два выражения: делимое и делитель.
     *
     * @param left  левое выражение (делимое)
     * @param right правое выражение (делитель)
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Печатает выражение деления в виде (left / right).
     *
     * @param out поток вывода, в который будет напечатано выражение
     */
    @Override
    public void print(PrintStream out) {
        out.print("(");
        left.print(out);
        out.print("/");
        right.print(out);
        out.print(")");
    }

    /**
     * Возвращает производную выражения деления по указанной переменной.
     *
     * @param variable переменная, по которой необходимо дифференцировать
     * @return новое выражение, представляющее производную
     */
    @Override
    public Expression derivative(String variable) {
        return new Div(
                new Sub(new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))
                ),
                new Mul(right, right)
        );
    }

    /**
     * Упрощает текущее выражение, создавая новое (упрощённое) выражение. Для {@link Div} - частное
     * упрощённых подвыражений.
     *
     * @return упрощённое выражение
     */
    @Override
    public Expression simplify() throws DivisionByZeroException {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            if (((Number) simplifiedRight).getValue() == 0) {
                throw new DivisionByZeroException("Dividing by zero");
            }
            return new Number(
                    ((Number) simplifiedLeft).getValue() / ((Number) simplifiedRight).getValue());
        }
        return new Div(simplifiedLeft, simplifiedRight);
    }

    /**
     * Вычисляет значение выражения деления.
     *
     * @param variables карта, где ключи — имена переменных, а значения — их числовые значения
     * @return результат умножения
     * @throws DivisionByZeroException      если делитель равен нулю.
     * @throws IncorrectAssignmentException если совершено некорректное присваивание переменных
     */
    @Override
    public double eval(Map<String, Double> variables)
            throws DivisionByZeroException, IncorrectAssignmentException {
        double rightEvaluated = right.eval(variables);
        if (rightEvaluated != 0) {
            return left.eval(variables) / right.eval(variables);
        } else {
            throw new DivisionByZeroException("Dividing by zero");
        }
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
        Div div = (Div) obj;
        return Objects.equals(left, div.left) && Objects.equals(right, div.right);
    }
}
