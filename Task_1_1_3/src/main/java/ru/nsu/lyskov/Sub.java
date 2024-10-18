package ru.nsu.lyskov;

import java.io.PrintStream;
import java.util.Map;
import ru.nsu.lyskov.Exceptions.DivisionByZeroException;
import ru.nsu.lyskov.Exceptions.IncorrectAssignmentException;

/**
 * Класс для представления операции вычитания одного выражения из другого.
 */
public class Sub extends Expression {
    private final Expression left, right;

    /**
     * Конструктор, принимающий два выражения: уменьшаемое и вычитаемое.
     *
     * @param left  левое выражение (уменьшаемое)
     * @param right правое выражение (вычитаемое)
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Печатает выражение вычитания в виде (left - right)
     *
     * @param out поток вывода, в который будет напечатано выражение
     */
    @Override
    public void print(PrintStream out) {
        out.print("(");
        left.print(out);
        out.print("-");
        right.print(out);
        out.print(")");
    }

    /**
     * Возвращает производную выражения вычитания по указанной переменной.
     *
     * @param variable переменная, по которой необходимо дифференцировать
     * @return новое выражение, представляющее производную (left' - right')
     */
    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Вычисляет значение выражения вычитания.
     *
     * @param variables карта, где ключи — имена переменных, а значения — их числовые значения
     * @return результат вычитания
     * @throws DivisionByZeroException      если в одном из выражений произошло деление на ноль.
     * @throws IncorrectAssignmentException если совершено некорректное присваивание переменны
     */
    @Override
    public double eval(Map<String, Double> variables) throws DivisionByZeroException, IncorrectAssignmentException {
        return left.eval(variables) - right.eval(variables);
    }
}
