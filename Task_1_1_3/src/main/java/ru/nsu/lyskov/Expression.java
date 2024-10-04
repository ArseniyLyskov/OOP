package ru.nsu.lyskov;

import java.util.HashMap;
import java.util.Map;

public abstract class Expression {
    public abstract void print();

    public abstract Expression derivative(String variable);

    abstract int eval(Map<String, Integer> variables);

    public int eval(String variablesStr) {
        Map<String, Integer> variables = parseVariables(variablesStr);
        return eval(variables);
    }

    private Map<String, Integer> parseVariables(String variablesStr) {
        Map<String, Integer> variables = new HashMap<>();
        String[] assignments = variablesStr.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            if (parts.length == 2) {
                String variable = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                variables.put(variable, value);
            } else {
                throw new IllegalArgumentException("Invalid format: " + assignment);
            }
        }
        return variables;
    }
}
