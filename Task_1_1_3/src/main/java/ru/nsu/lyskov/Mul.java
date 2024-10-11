package ru.nsu.lyskov;

import java.util.Map;
import ru.nsu.lyskov.Exceptions.DivisionByZeroException;
import ru.nsu.lyskov.Exceptions.IncorrectAssignmentException;

public class Mul extends Expression {
    private final Expression left, right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("*");
        right.print();
        System.out.print(")");
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable)));
    }

    @Override
    public double eval(Map<String, Double> variables) throws DivisionByZeroException, IncorrectAssignmentException {
        return left.eval(variables) * right.eval(variables);
    }
}
