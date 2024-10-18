package ru.nsu.lyskov;

import java.io.PrintStream;
import java.util.Map;
import ru.nsu.lyskov.Exceptions.DivisionByZeroException;
import ru.nsu.lyskov.Exceptions.IncorrectAssignmentException;

/**
 * Класс для представления операции умножения двух выражений.
 */
public class Mul extends Expression {
    private final Expression left, right;

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
     * Печатает умножение выражения в виде (left * right)
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
     * Возвращает производную выражения умножения по указанной переменной
     *
     * @param variable переменная, по которой необходимо дифференцировать
     * @return новое выражение, представляющее производную
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable)));
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
    public double eval(Map<String, Double> variables) throws DivisionByZeroException, IncorrectAssignmentException {
        return left.eval(variables) * right.eval(variables);
    }
}
