package ru.nsu.lyskov;

import java.util.Map;
import ru.nsu.lyskov.Exceptions.IncorrectAssignmentException;

public class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.print(name);
    }

    @Override
    public Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number(1);
        }
        return new Number(0);
    }

    @Override
    public double eval(Map<String, Double> variables) throws IncorrectAssignmentException {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        throw new IncorrectAssignmentException("Variable " + name + " is not defined");
    }
}
