package ru.nsu.lyskov;

import java.io.PrintStream;
import java.util.Map;
import ru.nsu.lyskov.Exceptions.DivisionByZeroException;
import ru.nsu.lyskov.Exceptions.IncorrectAssignmentException;

public class Div extends Expression {
    private final Expression left, right;

    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print(PrintStream out) {
        out.print("(");
        left.print(out);
        out.print("/");
        right.print(out);
        out.print(")");
    }

    @Override
    public Expression derivative(String variable) {
        return new Div(
                new Sub(new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))),
                new Mul(right, right)
        );
    }

    @Override
    public double eval(Map<String, Double> variables) throws DivisionByZeroException, IncorrectAssignmentException {
        double rightEvaluated = right.eval(variables);
        if (rightEvaluated != 0) {
            return left.eval(variables) / right.eval(variables);
        } else {
            throw new DivisionByZeroException("Dividing by zero");
        }
    }
}
